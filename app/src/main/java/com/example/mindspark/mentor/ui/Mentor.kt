package com.example.mindspark.mentor.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Grade
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.MonetizationOn
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.PlayCircleOutline
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material.icons.outlined.Title
import androidx.compose.material.icons.outlined.VerifiedUser
import androidx.compose.material.icons.outlined.VideoLibrary
import androidx.compose.material.icons.outlined.Videocam
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mindspark.auth.components.AuthTopBar
import com.example.mindspark.courses.model.CourseCategory
import com.example.mindspark.courses.model.CourseModel
import com.example.mindspark.courses.model.fetchYoutubePlaylistData
import com.example.mindspark.mentor.components.MentorCoursesList
import com.example.mindspark.ui.theme.LightBlueBackground
import com.example.mindspark.ui.theme.customTypography
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

// Color palette
private val PrimaryBlue = Color(0xFF1565C0)
private val LightBlue = Color(0xFFE8F1FF)
private val SecondaryColor = Color(0xFF03A9F4)
private val AccentColor = Color(0xFF009688)
private val TextPrimary = Color(0xFF212121)
private val TextSecondary = Color(0xFF757575)
private val DividerColor = Color(0xFFEEEEEE)
private val ErrorColor = Color(0xFFE53935)
private val SuccessColor = Color(0xFF43A047)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MentorScreen(navController: NavController) {
    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser
    val mentorId = remember { mutableStateOf(0) } // Default value
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
            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = Color.White,
                contentColor = PrimaryBlue,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        modifier = Modifier
                            .tabIndicatorOffset(tabPositions[selectedTabIndex])
                            .padding(horizontal = 24.dp),
                        height = 3.dp,
                        color = PrimaryBlue
                    )
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        text = {
                            Text(
                                text = title,
                                style = MaterialTheme.customTypography.mulish.semiBold,
                                fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        icon = {
                            Icon(
                                imageVector = if (index == 0) Icons.Filled.LibraryBooks else Icons.Filled.Add,
                                contentDescription = title
                            )
                        }
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
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = course.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = TextPrimary
                )

                Badge(
                    containerColor = PrimaryBlue,
                    contentColor = Color.White
                ) {
                    Text(
                        text = course.category,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Divider(color = DividerColor)

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CourseStatItem(
                    icon = Icons.Outlined.VideoLibrary,
                    label = "Videos",
                    value = course.videos.toString()
                )

                CourseStatItem(
                    icon = Icons.Outlined.AccessTime,
                    label = "Duration",
                    value = "${course.hours} hours"
                )

                if (course.certification) {
                    CourseStatItem(
                        icon = Icons.Outlined.VerifiedUser,
                        label = "Certificate",
                        value = "Yes"
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                OutlinedButton(
                    onClick = { showDeleteConfirmation = true },
                    modifier = Modifier.padding(end = 8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = ErrorColor
                    ),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = androidx.compose.ui.graphics.SolidColor(ErrorColor)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = "Delete",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Delete")
                }

                Button(
                    onClick = { onEditClick(docId) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryBlue
                    )
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = "Edit",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Edit")
                }
            }

            if (showDeleteConfirmation) {
                AlertDialog(
                    onDismissRequest = { showDeleteConfirmation = false },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Warning,
                            contentDescription = "Warning",
                            tint = ErrorColor
                        )
                    },
                    title = {
                        Text(
                            text = "Confirm Deletion",
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    },
                    text = {
                        Text(
                            "Are you sure you want to delete this course? This action cannot be undone.",
                            textAlign = TextAlign.Center
                        )
                    },
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
                            colors = ButtonDefaults.buttonColors(containerColor = ErrorColor)
                        ) {
                            Text("Delete")
                        }
                    },
                    dismissButton = {
                        OutlinedButton(onClick = { showDeleteConfirmation = false }) {
                            Text("Cancel")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun CourseStatItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = PrimaryBlue,
            modifier = Modifier.size(24.dp)
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary
        )

        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = TextSecondary
        )
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
    val languageOptions = listOf("English", "Hindi", "Gujarati", "Other")

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
//        // Header with styling
//        Card(
//            modifier = Modifier.fillMaxWidth(),
//            colors = CardDefaults.cardColors(
//                containerColor = PrimaryBlue
//            ),
//            shape = RoundedCornerShape(12.dp)
//        ) {
//            Row(
//                modifier = Modifier.padding(16.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Icon(
//                    imageVector = if (courseId != null) Icons.Filled.Edit else Icons.Filled.Add,
//                    contentDescription = "Course action",
//                    tint = Color.White,
//                    modifier = Modifier.size(32.dp)
//                )
//
//                Spacer(modifier = Modifier.width(16.dp))
//
//                Text(
//                    text = if (courseId != null) "Edit Course" else "Create New Course",
//                    fontSize = 24.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color.White
//                )
//            }
//        }

        // Course Details Section
        FormSection(
            title = "Course Details",
            icon = Icons.Outlined.Info
        ) {
            // Title field
            CustomTextField(
                value = title,
                onValueChange = { title = it },
                label = "Course Title",
                icon = Icons.Outlined.Title
            )

            // Category Dropdown
            CustomDropdownField(
                value = category,
                label = "Category",
                expanded = expandedCategory,
                onExpandedChange = { expandedCategory = it },
                options = categoryOptions,
                onOptionSelected = {
                    category = it
                    expandedCategory = false
                },
                icon = Icons.Outlined.Category
            )

            // Price field
            CustomTextField(
                value = price,
                onValueChange = { price = it },
                label = "Price",
                icon = Icons.Outlined.MonetizationOn,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }

        // Course Content Section
        FormSection(
            title = "Course Content",
            icon = Icons.Outlined.VideoLibrary
        ) {
            // Videos field
            CustomTextField(
                value = videos,
                onValueChange = { videos = it },
                label = "Number of Videos",
                icon = Icons.Outlined.Videocam,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            // Hours field
            CustomTextField(
                value = hours,
                onValueChange = { hours = it },
                label = "Duration (Hours)",
                icon = Icons.Outlined.Timer,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            // YouTube playlist link
            CustomTextField(
                value = youtubePlaylistLink,
                onValueChange = { youtubePlaylistLink = it },
                label = "YouTube Playlist Link",
                icon = Icons.Outlined.PlayCircleOutline
            )
        }

        // Additional Information Section
        FormSection(
            title = "Additional Information",
            icon = Icons.Outlined.Description
        ) {
            // About course
            CustomTextField(
                value = about,
                onValueChange = { about = it },
                label = "About Course",
                icon = Icons.Outlined.Info,
                singleLine = false,
                minLines = 3
            )

            CustomTextField(
                value = rating,
                onValueChange = { rating = it },
                label = "Courses Rating",
                icon = Icons.Outlined.Star,
                singleLine = true,
                minLines = 1
            )

            CustomTextField(
                value = students,
                onValueChange = { students = it },
                label = "Students Enrolled",
                icon = Icons.Outlined.PersonOutline,
                singleLine = true,
                minLines = 1
            )

            // Difficulty level dropdown
            CustomDropdownField(
                value = difficultyLevel,
                label = "Difficulty Level",
                expanded = expandedDifficulty,
                onExpandedChange = { expandedDifficulty = it },
                options = difficultyOptions,
                onOptionSelected = {
                    difficultyLevel = it
                    expandedDifficulty = false
                },
                icon = Icons.Outlined.Grade
            )

            // Language dropdown
            CustomDropdownField(
                value = language,
                label = "Language",
                expanded = expandedLanguage,
                onExpandedChange = { expandedLanguage = it },
                options = languageOptions,
                onOptionSelected = {
                    language = it
                    expandedLanguage = false
                },
                icon = Icons.Outlined.Language
            )

            // Certification switch with better styling
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.VerifiedUser,
                        contentDescription = "Certification",
                        tint = PrimaryBlue,
                        modifier = Modifier.size(24.dp)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = "Certification Available",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextPrimary
                    )
                }

                Switch(
                    checked = certification,
                    onCheckedChange = { certification = it },
                    thumbContent = {
                        Icon(
                            imageVector = if (certification) Icons.Filled.Check else Icons.Filled.Close,
                            contentDescription = "Certification Status",
                            modifier = Modifier.size(16.dp)
                        )
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = PrimaryBlue,
                        checkedIconColor = PrimaryBlue,
                        uncheckedThumbColor = Color.White,
                        uncheckedTrackColor = TextSecondary,
                        uncheckedIconColor = TextSecondary
                    )
                )
            }

            // Features field
            CustomTextField(
                value = features,
                onValueChange = { features = it },
                label = "Features (comma-separated)",
                icon = Icons.Outlined.List,
                singleLine = false,
                minLines = 2
            )

            // Image URL field
            CustomTextField(
                value = imageRes,
                onValueChange = { imageRes = it },
                label = "Image URL",
                icon = Icons.Outlined.Image
            )
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
                        playlistVideos = playlistVideos,
                        creatorId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
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
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
        ) {
            Icon(
                imageVector = if (courseId != null) Icons.Filled.Save else Icons.Filled.Add,
                contentDescription = "Save",
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = if (courseId != null) "Update Course" else "Create Course",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun FormSection(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = PrimaryBlue,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryBlue
                )
            }

            Divider(color = DividerColor)

            content()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    singleLine: Boolean = true,
    minLines: Int = 1
) {
    // Define gray color for the border
    val grayBorderColor = Color(0xFFAAAAAA)
    // Define black color for the icon
    val blackIconColor = Color(0xFF000000)

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = grayBorderColor,
            unfocusedBorderColor = grayBorderColor,
            focusedLabelColor = PrimaryBlue,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        ),
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = blackIconColor
            )
        },
        keyboardOptions = keyboardOptions,
        singleLine = singleLine,
        minLines = minLines,
        shape = RoundedCornerShape(8.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropdownField(
    value: String,
    label: String,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    options: List<String>,
    onOptionSelected: (String) -> Unit,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier
) {
    // Define gray color for the border
    val grayBorderColor = Color(0xFFAAAAAA)
    // Define black color for the icon
    val blackIconColor = Color(0xFF000000)

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = grayBorderColor,
                unfocusedBorderColor = grayBorderColor,
                focusedLabelColor = PrimaryBlue,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            leadingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = blackIconColor
                )
            },
            shape = RoundedCornerShape(8.dp)
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) },
            modifier = Modifier.background(Color.White)
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = { onOptionSelected(option) },
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MentorScreenPreview() {
    MentorScreen(navController = NavController(LocalContext.current))
}