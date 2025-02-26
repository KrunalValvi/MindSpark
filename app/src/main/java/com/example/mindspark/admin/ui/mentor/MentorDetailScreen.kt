package com.example.mindspark.admin.ui.mentor

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mindspark.admin.model.AdminMentorModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MentorDetailScreen(
    mentorId: String?,
    onBack: () -> Unit
) {
    val db = FirebaseFirestore.getInstance()
    val scope = rememberCoroutineScope()

    var name by remember { mutableStateOf("") }
    var profession by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }
    var profileImage by remember { mutableStateOf("") }
    var socialLinks by remember { mutableStateOf("") }

    LaunchedEffect(mentorId) {
        if (mentorId != null) {
            val doc = db.collection("mentors").document(mentorId).get().await()
            val mentor = doc.toObject(AdminMentorModel::class.java)
            mentor?.let {
                name = it.name
                profession = it.profession
                bio = it.bio ?: ""
                profileImage = it.profile_image ?: ""
                socialLinks = it.social_links ?: ""
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(if (mentorId != null) "Edit Mentor" else "New Mentor") },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        )
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = profession,
                onValueChange = { profession = it },
                label = { Text("Profession") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = bio,
                onValueChange = { bio = it },
                label = { Text("Bio") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = profileImage,
                onValueChange = { profileImage = it },
                label = { Text("Profile Image URL") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = socialLinks,
                onValueChange = { socialLinks = it },
                label = { Text("Social Links (JSON)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    scope.launch {
                        val newMentor = AdminMentorModel(
                            name = name,
                            profession = profession,
                            bio = bio,
                            profile_image = profileImage,
                            social_links = socialLinks
                        )
                        try {
                            if (mentorId == null) {
                                db.collection("mentors").add(newMentor).await()
                            } else {
                                db.collection("mentors").document(mentorId).set(newMentor).await()
                            }
                            onBack()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save")
            }
        }
    }
}
