package com.example.mindspark.mentor.ui

import android.R
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mindspark.auth.components.AuthTopBar
import com.example.mindspark.courses.model.CourseCategory
import com.example.mindspark.courses.model.CourseModel
import com.example.mindspark.courses.model.fetchYoutubePlaylistData
import com.example.mindspark.mentor.components.MentorCoursesList
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

private val LightBlueBackground = Color(0xFFF5F9FF)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MentorScreen(navController: NavController) {
    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser
    val mentorId = remember { mutableStateOf(0) } // Default value
    val showCreateForm = remember { mutableStateOf(false) }
    val selectedCourseId = remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()
    var refreshTrigger = remember { mutableStateOf(false) }

    // Fetch mentor ID when screen is loaded
    LaunchedEffect(currentUser) {
        currentUser?.uid?.let { uid ->
            try {
                val mentorDoc = db.collection("mentors").document(uid).get().await()
                mentorId.value = mentorDoc.getLong("mentorId")?.toInt() ?: 0
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("My Courses", "Create Course")

    Scaffold(
        modifier = Modifier.background(LightBlueBackground),
        containerColor = LightBlueBackground,
        topBar = {
            AuthTopBar(
                title = "Mentor Dashboard",
                onBackClick = { navController.navigateUp() }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LightBlueBackground)
                .padding(padding)
        ) {
            TabRow(selectedTabIndex = selectedTabIndex) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index }
                    )
                }
            }

            when (selectedTabIndex) {
                0 -> MentorCoursesList(
                    mentorId = mentorId.value,
                    refreshTrigger = refreshTrigger.value,
                    onEditCourse = { courseId ->
                        selectedCourseId.value = courseId
                        selectedTabIndex = 1
                    },
                    onRefresh = {
                        // This allows the MentorCoursesList to trigger a refresh
                        refreshTrigger.value = !refreshTrigger.value
                    }
                )
                1 -> MentorCourseForm(
                    courseId = selectedCourseId.value,
                    mentorId = mentorId.value,
                    onComplete = {
                        selectedCourseId.value = null
                        // Trigger refresh when returning to list
                        refreshTrigger.value = !refreshTrigger.value
                        selectedTabIndex = 0
                    }
                )
            }
        }
    }
}

@Composable
fun CourseItem(
    course: CourseModel,
    docId: String,
    onEditClick: (String) -> Unit,
    onDelete: () -> Unit = {}
) {
    val db = FirebaseFirestore.getInstance()
    val scope = rememberCoroutineScope()
    var showDeleteConfirmation by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = course.title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(text = "Category: ${course.category}")
            Text(text = "Videos: ${course.getVideosAsString()}")
            Text(text = "Duration: ${course.hours} hours")

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = { showDeleteConfirmation = true },
                    modifier = Modifier.padding(end = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Delete")
                }

                Button(
                    onClick = { onEditClick(docId) }
                ) {
                    Text("Edit")
                }
            }

            if (showDeleteConfirmation) {
                AlertDialog(
                    onDismissRequest = { showDeleteConfirmation = false },
                    title = { Text("Confirm Deletion") },
                    text = { Text("Are you sure you want to delete this course? This action cannot be undone.") },
                    confirmButton = {
                        Button(
                            onClick = {
                                scope.launch {
                                    try {
                                        db.collection("courses").document(docId).delete().await()
                                        showDeleteConfirmation = false
                                        onDelete() // Call the onDelete callback to refresh the list
                                    } catch (e: Exception) {
                                        println("Error deleting course: ${e.message}")
                                        e.printStackTrace()
                                    }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                        ) {
                            Text("Delete")
                        }
                    },
                    dismissButton = {
                        Button(onClick = { showDeleteConfirmation = false }) {
                            Text("Cancel")
                        }
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MentorCourseForm(
    courseId: String?,
    mentorId: Int,
    onComplete: () -> Unit
) {
    val db = FirebaseFirestore.getInstance()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    // Mutable state variables for course details
    var category by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var rating by remember { mutableStateOf("") }
    var students by remember { mutableStateOf("") }
    var videos by remember { mutableStateOf("") }
    var hours by remember { mutableStateOf("") }
    var about by remember { mutableStateOf("") }
    var difficultyLevel by remember { mutableStateOf("") }
    var certification by remember { mutableStateOf(false) }
    var language by remember { mutableStateOf("") }
    var features by remember { mutableStateOf("") }
    var imageRes by remember { mutableStateOf("") }
    var youtubePlaylistLink by remember { mutableStateOf("") }

    // For dropdown menus
    var expandedCategory by remember { mutableStateOf(false) }
    var expandedDifficulty by remember { mutableStateOf(false) }
    var expandedLanguage by remember { mutableStateOf(false) }

    // Predefined options for dropdowns
    val categoryOptions = CourseCategory.values().map { it.value }
    val difficultyOptions = listOf("Beginner", "Intermediate", "Advanced", "All Levels")
    val languageOptions = listOf("English", "Hindi", "Spanish", "French", "German", "Other")

    var refreshTrigger = remember { mutableStateOf(false) }

    // Store the original mentor IDs to prevent overwriting
    var existingMentorIds by remember { mutableStateOf<List<Int>>(emptyList()) }

    // Fetch course details if editing an existing course
    LaunchedEffect(courseId) {
        if (courseId != null) {
            try {
                val doc = db.collection("courses").document(courseId).get().await()
                val course = doc.toObject(CourseModel::class.java)
                course?.let {
                    category = it.category
                    title = it.title
                    price = it.price
                    rating = it.rating
                    students = it.students
                    imageRes = it.imageRes
                    videos = it.getVideosAsString()
                    hours = it.hours
                    about = it.about
                    difficultyLevel = it.difficultyLevel
                    certification = it.certification
                    language = it.language
                    features = it.features.joinToString(", ")
                    existingMentorIds = it.mentorIds
                    // Set YouTube playlist link if available
                    if (it.playlistVideos.isNotEmpty()) {
                        val firstVideo = it.playlistVideos.firstOrNull()
                        youtubePlaylistLink = firstVideo?.videoUrl?.takeIf { url ->
                            url.contains("watch?v=")
                        }?.let { url ->
                            val videoId = url.substringAfter("watch?v=").substringBefore("&")
                            // Reconstruct playlist URL if possible
                            if (videoId.isNotEmpty()) {
                                "https://www.youtube.com/playlist?list=PL..." // Placeholder
                            } else ""
                        } ?: ""
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header with styling
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF167F71)
            )
        ) {
            Text(
                text = if (courseId != null) "Edit Course" else "Create New Course",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Course Details Section
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Course Details",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF167F71)
                )

                // Title field
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Course Title") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF167F71),
                        focusedLabelColor = Color(0xFF167F71)
                    ),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_delete), // Replace with appropriate icon
                            contentDescription = "Title",
                            tint = Color(0xFF167F71)
                        )
                    }
                )

                // Category Dropdown
                ExposedDropdownMenuBox(
                    expanded = expandedCategory,
                    onExpandedChange = { expandedCategory = it },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = category,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Category") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCategory)
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF167F71),
                            focusedLabelColor = Color(0xFF167F71)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_delete), // Replace with appropriate icon
                                contentDescription = "Category",
                                tint = Color(0xFF167F71)
                            )
                        }
                    )

                    ExposedDropdownMenu(
                        expanded = expandedCategory,
                        onDismissRequest = { expandedCategory = false }
                    ) {
                        categoryOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    category = option
                                    expandedCategory = false
                                }
                            )
                        }
                    }
                }

                // Price field
                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text("Price") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF167F71),
                        focusedLabelColor = Color(0xFF167F71)
                    ),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_delete), // Replace with appropriate icon
                            contentDescription = "Price",
                            tint = Color(0xFF167F71)
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        }

        // Course Content Section
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Course Content",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF167F71)
                )

                // Videos field
                OutlinedTextField(
                    value = videos,
                    onValueChange = { videos = it },
                    label = { Text("Number of Videos") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF167F71),
                        focusedLabelColor = Color(0xFF167F71)
                    ),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_delete), // Replace with appropriate icon
                            contentDescription = "Videos",
                            tint = Color(0xFF167F71)
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                // Hours field
                OutlinedTextField(
                    value = hours,
                    onValueChange = { hours = it },
                    label = { Text("Duration (Hours)") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF167F71),
                        focusedLabelColor = Color(0xFF167F71)
                    ),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_delete), // Replace with appropriate icon
                            contentDescription = "Hours",
                            tint = Color(0xFF167F71)
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                // YouTube playlist link
                OutlinedTextField(
                    value = youtubePlaylistLink,
                    onValueChange = { youtubePlaylistLink = it },
                    label = { Text("YouTube Playlist Link") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF167F71),
                        focusedLabelColor = Color(0xFF167F71)
                    ),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_delete), // Replace with appropriate icon
                            contentDescription = "YouTube Link",
                            tint = Color(0xFF167F71)
                        )
                    }
                )
            }
        }

        // Course Details Section
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Additional Information",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF167F71)
                )

                // About course
                OutlinedTextField(
                    value = about,
                    onValueChange = { about = it },
                    label = { Text("About Course") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF167F71),
                        focusedLabelColor = Color(0xFF167F71)
                    ),
                    minLines = 3,
                    maxLines = 5
                )

                // Difficulty level dropdown
                ExposedDropdownMenuBox(
                    expanded = expandedDifficulty,
                    onExpandedChange = { expandedDifficulty = it },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = difficultyLevel,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Difficulty Level") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDifficulty)
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF167F71),
                            focusedLabelColor = Color(0xFF167F71)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = expandedDifficulty,
                        onDismissRequest = { expandedDifficulty = false }
                    ) {
                        difficultyOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    difficultyLevel = option
                                    expandedDifficulty = false
                                }
                            )
                        }
                    }
                }

                // Language dropdown
                ExposedDropdownMenuBox(
                    expanded = expandedLanguage,
                    onExpandedChange = { expandedLanguage = it },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = language,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Language") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedLanguage)
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF167F71),
                            focusedLabelColor = Color(0xFF167F71)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = expandedLanguage,
                        onDismissRequest = { expandedLanguage = false }
                    ) {
                        languageOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    language = option
                                    expandedLanguage = false
                                }
                            )
                        }
                    }
                }

                // Certification switch with better styling
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Certification Available",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Switch(
                        checked = certification,
                        onCheckedChange = { certification = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color(0xFF167F71),
                            checkedTrackColor = Color(0xFFE8F1FF)
                        )
                    )
                }

                // Features field
                OutlinedTextField(
                    value = features,
                    onValueChange = { features = it },
                    label = { Text("Features (comma-separated)") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF167F71),
                        focusedLabelColor = Color(0xFF167F71)
                    ),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_delete), // Replace with appropriate icon
                            contentDescription = "Features",
                            tint = Color(0xFF167F71)
                        )
                    }
                )

                // Image URL field
                OutlinedTextField(
                    value = imageRes,
                    onValueChange = { imageRes = it },
                    label = { Text("Image URL") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF167F71),
                        focusedLabelColor = Color(0xFF167F71)
                    ),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_delete), // Replace with appropriate icon
                            contentDescription = "Image URL",
                            tint = Color(0xFF167F71)
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Save button with improved styling
        Button(
            onClick = {
                if (title.isBlank() || category.isBlank()) {
                    Toast.makeText(context, "Title and Category are required", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                scope.launch {
                    // Ensure mentorId is included in the mentorIds list
                    val updatedMentorIds = if (existingMentorIds.contains(mentorId)) {
                        existingMentorIds
                    } else {
                        existingMentorIds + mentorId
                    }

                    // Fetch the playlist videos on a background thread
                    val playlistVideos = if (youtubePlaylistLink.isNotEmpty()) {
                        fetchYoutubePlaylistData(youtubePlaylistLink)
                    } else {
                        emptyList()
                    }

                    // Create a new CourseModel including the fetched playlistVideos
                    val newCourse = CourseModel(
                        category = category,
                        title = title,
                        price = price,
                        rating = rating,
                        students = students,
                        imageRes = imageRes,
                        videos = videos.toIntOrNull() ?: 0,
                        hours = hours,
                        about = about,
                        difficultyLevel = difficultyLevel,
                        certification = certification,
                        language = language,
                        features = features.split(",").map { it.trim() },
                        mentorIds = updatedMentorIds,
                        playlistVideos = playlistVideos
                    )

                    try {
                        if (courseId == null) {
                            db.collection("courses").add(newCourse).await()
                            Toast.makeText(context, "Course created successfully", Toast.LENGTH_SHORT).show()
                        } else {
                            db.collection("courses").document(courseId).set(newCourse).await()
                            Toast.makeText(context, "Course updated successfully", Toast.LENGTH_SHORT).show()
                        }
                        // Force a refresh by changing a state value
                        refreshTrigger.value = !refreshTrigger.value
                        onComplete()
                    } catch (e: Exception) {
                        Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                        e.printStackTrace()
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF167F71))
        ) {
            Text(
                text = if (courseId != null) "Update Course" else "Create Course",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun MentorScreenPreview() {
    MentorScreen(navController = NavController(LocalContext.current))
}