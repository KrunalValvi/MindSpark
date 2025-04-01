package com.example.mindspark.home.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mindspark.R
import com.example.mindspark.courses.components.PopularCourseCardHorizontal
import com.example.mindspark.courses.components.PopularCourseCardVertical
import com.example.mindspark.courses.components.TopMentorCardHorizontal
import com.example.mindspark.courses.components.TopMentorCardVertical
import com.example.mindspark.courses.model.CourseCategory
import com.example.mindspark.courses.model.CourseModel
import com.example.mindspark.courses.model.MentorModel
import com.example.mindspark.courses.model.SpecialOfferModel
import com.example.mindspark.ui.theme.customTypography
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun HomeHeader(navController: NavController) {
    val context = LocalContext.current
    var fullName by remember { mutableStateOf("Loading...") }
    val currentUser = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser

    LaunchedEffect(currentUser) {
        if (currentUser != null) {
            val db = FirebaseFirestore.getInstance()
            db.collection("users").document(currentUser.uid)
                .get()
                .addOnSuccessListener { document ->
                    fullName = document.getString("fullName")
                        ?: currentUser.displayName ?: "User"
                }
                .addOnFailureListener {
                    fullName = currentUser.displayName ?: "User"
                }
        } else {
            fullName = "User"
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "Hi, $fullName",
                style = MaterialTheme.customTypography.jost.semiBold,
                fontSize = 24.sp
            )
            Text(
                text = "What Would you like to learn Today?\nSearch Below.",
                style = MaterialTheme.customTypography.mulish.bold.copy(
                    lineHeight = 16.sp
                ),
                fontSize = 13.sp,
                modifier = Modifier.padding(top = 3.dp)
            )
        }
        Image(
            painter = painterResource(R.drawable.ic_notification),
            contentDescription = "Notifications",
            modifier = Modifier
                .size(40.dp)
                .clickable { navController.navigate("NotificationsScreen") }
        )
    }
}

@Composable
fun SectionHeader(
    title: String,
    onSeeAllClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = title,
            style = MaterialTheme.customTypography.jost.semiBold,
            fontSize = 15.sp,
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            modifier = Modifier.clickable(onClick = onSeeAllClick),
            text = "SEE ALL >",
            color = Color(0xFF0961F5),
            style = MaterialTheme.customTypography.mulish.extraBold,
            fontSize = 12.sp
        )
    }
}

@Composable
fun CategoriesListShow(
    categories: List<CourseCategory>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit,
    onAllSelected: () -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(categories.size) { index ->
            val isSelected = selectedCategory == categories[index].value
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(if (isSelected) Color(0xFF167F71) else Color(0xFFE8F1FF))
                    .clickable {
                        val category = categories[index].value
                        if (category == CourseCategory.All.value) {
                            onAllSelected()
                        } else {
                            onCategorySelected(category)
                        }
                    }
                    .padding(horizontal = 16.dp, vertical = 5.dp)
            ) {
                Text(
                    text = categories[index].value,
                    style = MaterialTheme.customTypography.mulish.bold,
                    color = if (isSelected) Color.White else Color(0xFF202244),
                    fontSize = 13.sp
                )
            }
        }
    }
}

@Composable
fun CategoriesList(
    categories: List<CourseCategory>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(categories.size) { index ->
            val isSelected = selectedCategory == categories[index].value
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(if (isSelected) Color(0xFF167F71) else Color(0xFFE8F1FF))
                    .clickable { onCategorySelected(categories[index].value) }
                    .padding(horizontal = 16.dp, vertical = 5.dp)
            ) {
                Text(
                    text = categories[index].value,
                    style = MaterialTheme.customTypography.mulish.bold,
                    color = if (isSelected) Color.White else Color(0xFF202244),
                    fontSize = 13.sp
                )
            }
        }
    }
}

@Composable
fun SpecialOfferCard(cards: List<SpecialOfferModel>) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(cards) { card ->
            SpecialOfferCard(card)
        }
    }
}

@Composable
fun PopularCoursesListVertical(courses: List<CourseModel>, onCourseClick: (CourseModel) -> Unit) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(courses.size) { index ->
            val course = courses[index]
            PopularCourseCardVertical(course, onCourseClick)
        }
    }
}

@Composable
fun PopularCoursesListHorizontal(courses: List<CourseModel>, onCourseClick: (CourseModel) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(courses.size) { index ->
            val course = courses[index]
            PopularCourseCardHorizontal(course, onCourseClick)
        }
    }
}

// Modified to properly handle mentor images
@Composable
fun TopMentorsListHorizontal(mentors: List<MentorModel>, onMentorClick: (MentorModel) -> Unit) {
    // State to hold the mentors from Firebase
    var firebaseMentors by remember { mutableStateOf<List<MentorModel>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    // Effect to fetch mentors from Firebase when the component is first composed
    LaunchedEffect(Unit) {
        Log.d("TopMentorsListHorizontal", "Fetching mentors from Firebase")
        fetchMentorsFromFirebase { mentorsList ->
            Log.d("TopMentorsListHorizontal", "Loaded ${mentorsList.size} mentors from Firebase")
            // Log each mentor's data for debugging
            mentorsList.forEach { mentor ->
                Log.d("TopMentorsListHorizontal", "Mentor: ${mentor.name}, ProfileImageUrl: ${mentor.profileImageUrl}, UserId: ${mentor.userId}")
            }
            firebaseMentors = mentorsList
            isLoading = false
        }
    }

    // Display the list of mentors
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Use Firebase mentors if available, otherwise fall back to provided mentors
        val displayedMentors = if (firebaseMentors.isNotEmpty()) firebaseMentors else mentors
        Log.d("TopMentorsListHorizontal", "Displaying ${displayedMentors.size} mentors")

        items(displayedMentors.size) { index ->
            val currentMentor = displayedMentors[index]
            Log.d("TopMentorsListHorizontal", "Rendering mentor at index $index: ${currentMentor.name}")
            TopMentorCardHorizontal(currentMentor, onClick = onMentorClick)
        }
    }
}

// Improved function to fetch mentors from Firebase with better profile image handling
private fun fetchMentorsFromFirebase(onComplete: (List<MentorModel>) -> Unit) {
    Log.d("FetchMentors", "Starting to fetch mentors from Firebase")
    val db = FirebaseFirestore.getInstance()
    val mentorsList = mutableListOf<MentorModel>()

    // Query users collection for accounts with type "Mentor"
    db.collection("users")
        .whereEqualTo("accountType", "Mentor")
        .get()
        .addOnSuccessListener { documents ->
            Log.d("FetchMentors", "Successfully queried Firebase, found ${documents.size()} documents")
            var idCounter = 1
            for (document in documents) {
                val userData = document.data
                Log.d("FetchMentors", "Processing document ID: ${document.id}, data: $userData")

                // Check if user is a mentor
                if (userData["accountType"] == "Mentor") {
                    // Get profile image URL with proper handling
                    val profileImageUrl = userData["profileImageUrl"] as? String ?: ""

                    // Log what we found
                    Log.d("FetchMentors", "Found mentor: ${userData["fullName"]}, profile image: $profileImageUrl")

                    // Create a MentorModel from the document
                    val mentor = MentorModel(
                        id = idCounter++, // Assign sequential IDs
                        userId = document.id,
                        name = userData["fullName"] as? String ?: "Unknown",
                        profession = userData["profession"] as? String ?: "Mentor",
                        courses = (userData["courses"] as? Long)?.toInt() ?: 0,
                        students = (userData["students"] as? Long)?.toInt() ?: 0,
                        ratings = (userData["ratings"] as? Long)?.toInt() ?: 0,
                        profileImageUrl = profileImageUrl,
                        imageRes = R.drawable.ic_profile_placeholder // Default image resource
                    )
                    mentorsList.add(mentor)
                }
            }
            Log.d("FetchMentors", "Completed processing ${mentorsList.size} mentors")
            onComplete(mentorsList)
        }
        .addOnFailureListener { exception ->
            // Log the error and return an empty list
            Log.e("FetchMentors", "Error getting mentors: ${exception.message}", exception)
            onComplete(emptyList())
        }
}

// Modified to properly handle mentor images
@Composable
fun TopMentorsListVertical(mentors: List<MentorModel>, onMentorClick: (MentorModel) -> Unit) {
    // State to hold the mentors from Firebase
    var firebaseMentors by remember { mutableStateOf<List<MentorModel>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    // Effect to fetch mentors from Firebase when the component is first composed
    LaunchedEffect(Unit) {
        Log.d("TopMentorsListVertical", "Fetching mentors from Firebase")
        fetchMentorsFromFirebase { mentorsList ->
            // Log the loaded mentors for debugging
            Log.d("TopMentorsListVertical", "Loaded ${mentorsList.size} mentors from Firebase")
            mentorsList.forEach { mentor ->
                Log.d("TopMentorsListVertical", "Mentor: ${mentor.name}, ProfileImageUrl: ${mentor.profileImageUrl}, UserId: ${mentor.userId}")
            }

            firebaseMentors = mentorsList
            isLoading = false
        }
    }

    // Display the list of mentors
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Use Firebase mentors if available, otherwise fall back to provided mentors
        val displayedMentors = if (firebaseMentors.isNotEmpty()) firebaseMentors else mentors

        // Log which mentors are being displayed
        Log.d("TopMentorsListVertical", "Displaying ${displayedMentors.size} mentors")

        items(displayedMentors.size) { index ->
            val currentMentor = displayedMentors[index]
            Log.d("TopMentorsListVertical", "Rendering mentor at index $index: ${currentMentor.name}, ProfileImageUrl: ${currentMentor.profileImageUrl}")
            TopMentorCardVertical(currentMentor, onClick = onMentorClick)
        }
    }
}