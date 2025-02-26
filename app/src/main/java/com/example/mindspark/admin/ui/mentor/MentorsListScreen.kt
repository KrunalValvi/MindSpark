package com.example.mindspark.admin.ui.mentor

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mindspark.admin.model.AdminMentorModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.mindspark.admin.components.AdminMentorCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MentorsListScreen(
    onBack: () -> Unit,
    onMentorClick: (String?) -> Unit // mentorId or null for new mentor
) {
    var mentors by remember { mutableStateOf<List<Pair<String, AdminMentorModel>>>(emptyList()) }

    LaunchedEffect(Unit) {
        val db = FirebaseFirestore.getInstance()
        val snapshot = db.collection("mentors").get().await()
        mentors = snapshot.documents.mapNotNull { doc ->
            doc.toObject(AdminMentorModel::class.java)?.let { mentor ->
                doc.id to mentor
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            TopAppBar(
                title = { Text("Mentors (${mentors.size})") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(mentors) { (id, mentor) ->
                    AdminMentorCard(
                        name = mentor.name,
                        profession = mentor.profession,
                        bio = mentor.bio ?: "",
                        onClick = { onMentorClick(id) }
                    )
                }
            }
        }
        FloatingActionButton(
            onClick = { onMentorClick(null) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Add Mentor")
        }
    }
}
