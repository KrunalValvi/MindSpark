package com.example.mindspark.home.ui

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mindspark.R
import com.example.mindspark.auth.components.CustomTextField
import com.example.mindspark.home.components.*
import com.example.mindspark.courses.data.CardData
import com.example.mindspark.courses.data.CourseData
import com.example.mindspark.courses.data.MentorData

@Composable
fun HomeScreen(navController: NavController) {

    var search by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }
    val cards = CardData.getCardDetails()

    Box(
        modifier = Modifier
            .background(Color(0xFFF5F9FF))
            .fillMaxSize() // Add this
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(
                    start = 10.dp,
                    end = 10.dp,
                    top = 10.dp,
//                    bottom = 80.dp // Add fixed bottom padding for navbar
                )
        ) {
            // Header Section
            HomeHeader(navController = navController)

            // Search Section
            CustomTextField(
                value = search,
                onValueChange = { search = it },
                placeholder = "Search",
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "Search Icon",
                        modifier = Modifier
                            .size(24.dp)
                            .padding(start = 4.dp)
                            .clickable{ navController.navigate("SearchScreen")}
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            Spacer(modifier = Modifier.padding(top = 20.dp))

            // Special Offer Card
            SpecialOfferCard(cards = cards)

            // Popular Courses Section
            SectionHeader(
                title = "Popular Courses",
                onSeeAllClick = {
                    navController.navigate("PopularCoursesList"){
                        popUpTo(navController.graph.startDestinationId) {saveState = true}
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )

            // Categories List
            CategoriesListShow(
                categories = CourseData.getAllCategories(),
                selectedCategory = selectedCategory,
                onCategorySelected = { selectedCategory = it },
                onAllSelected = {
                    navController.navigate("CoursesListScreen") // Navigate to the "All" page
                }
            )

            // Popular Courses List
            PopularCoursesListHorizontal(
                courses = CourseData.getPopularCourses(),
                onCourseClick = { course ->
                    navController.navigate("CourseDetailScreen/${course.id}")
                }
            )

            // Top Mentors Section
            SectionHeader(
                title = "Top Mentor",
                onSeeAllClick = { navController.navigate("TopMentorScreen") }
            )

            // Mentors List
            TopMentorsListHorizontal(
                mentors = MentorData.getTopMentors(),
                onMentorClick = { mentor ->
                    // Navigate to SingleMentorDetails with the mentor's ID
                    navController.navigate("SingleMentorDetails/${mentor.id}")
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = NavController(context = LocalContext.current))
}

//package com.example.mindspark.home.ui
//
//import androidx.compose.animation.animateContentSize
//import androidx.compose.foundation.*
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyRow
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.*
//import androidx.compose.material.icons.outlined.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.style.TextOverflow
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import com.example.mindspark.R
//import com.example.mindspark.ui.theme.customTypography
//
//@Composable
//fun HomeScreen(navController: NavController) {
//    var search by remember { mutableStateOf("") }
//    var selectedCategory by remember { mutableStateOf("All") }
//
//    Box(
//        modifier = Modifier
//            .background(Color(0xFFF5F9FF))
//            .fillMaxSize()
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .verticalScroll(rememberScrollState())
//        ) {
//            // Top App Bar with Profile
//            HomeTopBar(navController)
//
//            // Main Content
//            Column(
//                modifier = Modifier
//                    .padding(horizontal = 16.dp)
//            ) {
//                // Welcome Message
//                WelcomeSection()
//
//                // Search Bar
//                SearchBar(
//                    query = search,
//                    onQueryChange = { search = it },
//                    onSearch = { navController.navigate("SearchScreen") }
//                )
//
//                // Quick Actions
//                QuickActionsSection(navController)
//
//                // Featured Course
//                FeaturedCourseCard(navController)
//
//                // Categories
//                CategoriesSection(
//                    selectedCategory = selectedCategory,
//                    onCategorySelected = { selectedCategory = it }
//                )
//
//                // Popular Courses
//                PopularCoursesSection(navController)
//
//                // Top Mentors
//                TopMentorsSection(navController)
//
//                // Learning Progress
//                LearningProgressSection()
//
//                Spacer(modifier = Modifier.height(80.dp))
//            }
//        }
//    }
//}
//
//@Composable
//fun PopularCoursesSection(navController: NavController) {
//    Column(modifier = Modifier.padding(vertical = 16.dp)) {
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(
//                text = "Popular Courses",
//                style = MaterialTheme.customTypography.jost.semiBold,
//                fontSize = 18.sp,
//                color = Color(0xFF202244)
//            )
//            TextButton(onClick = { navController.navigate("courses") }) {
//                Text(
//                    text = "See All",
//                    style = MaterialTheme.customTypography.mulish.semiBold,
//                    color = Color(0xFF1565C0)
//                )
//            }
//        }
//
//        LazyRow(
//            horizontalArrangement = Arrangement.spacedBy(16.dp),
//            contentPadding = PaddingValues(vertical = 8.dp)
//        ) {
//            items(3) { index ->
//                CourseCard(
//                    courseTitle = "Course ${index + 1}",
//                    instructor = "Instructor Name",
//                    rating = 4.5f,
//                    price = "$49.99",
//                    onClick = { navController.navigate("courseDetail/$index") }
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun CourseCard(
//    courseTitle: String,
//    instructor: String,
//    rating: Float,
//    price: String,
//    onClick: () -> Unit
//) {
//    Card(
//        modifier = Modifier
//            .width(280.dp)
//            .clickable(onClick = onClick),
//        shape = RoundedCornerShape(12.dp),
//        colors = CardDefaults.cardColors(containerColor = Color.White)
//    ) {
//        Column(
//            modifier = Modifier.padding(12.dp)
//        ) {
//            // Course Thumbnail
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(140.dp)
//                    .clip(RoundedCornerShape(8.dp))
//                    .background(Color(0xFFE3F2FD))
//            ) {
//                // Replace with actual course thumbnail
//                Icon(
//                    imageVector = Icons.Outlined.School,
//                    contentDescription = null,
//                    tint = Color(0xFF1565C0),
//                    modifier = Modifier
//                        .size(48.dp)
//                        .align(Alignment.Center)
//                )
//            }
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            // Course Title
//            Text(
//                text = courseTitle,
//                style = MaterialTheme.customTypography.mulish.bold,
//                fontSize = 16.sp,
//                color = Color(0xFF202244),
//                maxLines = 2,
//                overflow = TextOverflow.Ellipsis
//            )
//
//            Spacer(modifier = Modifier.height(4.dp))
//
//            // Instructor Name
//            Text(
//                text = "By $instructor",
//                style = MaterialTheme.customTypography.mulish.regular,
//                fontSize = 14.sp,
//                color = Color(0xFF6E7191)
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            // Rating and Price Row
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Icon(
//                        imageVector = Icons.Filled.Star,
//                        contentDescription = null,
//                        tint = Color(0xFFFFC107),
//                        modifier = Modifier.size(16.dp)
//                    )
//                    Spacer(modifier = Modifier.width(4.dp))
//                    Text(
//                        text = rating.toString(),
//                        style = MaterialTheme.customTypography.mulish.semiBold,
//                        fontSize = 14.sp,
//                        color = Color(0xFF202244)
//                    )
//                }
//                Text(
//                    text = price,
//                    style = MaterialTheme.customTypography.mulish.bold,
//                    fontSize = 16.sp,
//                    color = Color(0xFF1565C0)
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun TopMentorsSection(navController: NavController) {
//    Column(modifier = Modifier.padding(vertical = 16.dp)) {
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(
//                text = "Top Mentors",
//                style = MaterialTheme.customTypography.jost.semiBold,
//                fontSize = 18.sp,
//                color = Color(0xFF202244)
//            )
//            TextButton(onClick = { navController.navigate("mentors") }) {
//                Text(
//                    text = "See All",
//                    style = MaterialTheme.customTypography.mulish.semiBold,
//                    color = Color(0xFF1565C0)
//                )
//            }
//        }
//
//        LazyRow(
//            horizontalArrangement = Arrangement.spacedBy(16.dp),
//            contentPadding = PaddingValues(vertical = 8.dp)
//        ) {
//            items(4) { index ->
//                MentorCard(
//                    name = "Mentor Name",
//                    specialty = "Mobile Development",
//                    rating = 4.8f,
//                    onClick = { navController.navigate("mentorDetail/$index") }
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun MentorCard(
//    name: String,
//    specialty: String,
//    rating: Float,
//    onClick: () -> Unit
//) {
//    Card(
//        modifier = Modifier
//            .width(160.dp)
//            .clickable(onClick = onClick),
//        shape = RoundedCornerShape(12.dp),
//        colors = CardDefaults.cardColors(containerColor = Color.White)
//    ) {
//        Column(
//            modifier = Modifier.padding(12.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            // Mentor Avatar
//            Box(
//                modifier = Modifier
//                    .size(80.dp)
//                    .clip(CircleShape)
//                    .background(Color(0xFFE3F2FD)),
//                contentAlignment = Alignment.Center
//            ) {
//                Icon(
//                    imageVector = Icons.Outlined.Person,
//                    contentDescription = null,
//                    tint = Color(0xFF1565C0),
//                    modifier = Modifier.size(40.dp)
//                )
//            }
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            Text(
//                text = name,
//                style = MaterialTheme.customTypography.mulish.bold,
//                fontSize = 16.sp,
//                color = Color(0xFF202244),
//                maxLines = 1,
//                overflow = TextOverflow.Ellipsis
//            )
//
//            Text(
//                text = specialty,
//                style = MaterialTheme.customTypography.mulish.regular,
//                fontSize = 14.sp,
//                color = Color(0xFF6E7191),
//                maxLines = 1,
//                overflow = TextOverflow.Ellipsis
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                Icon(
//                    imageVector = Icons.Filled.Star,
//                    contentDescription = null,
//                    tint = Color(0xFFFFC107),
//                    modifier = Modifier.size(16.dp)
//                )
//                Spacer(modifier = Modifier.width(4.dp))
//                Text(
//                    text = rating.toString(),
//                    style = MaterialTheme.customTypography.mulish.semiBold,
//                    fontSize = 14.sp,
//                    color = Color(0xFF202244)
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun LearningProgressSection() {
//    Column(modifier = Modifier.padding(vertical = 16.dp)) {
//        Text(
//            text = "Continue Learning",
//            style = MaterialTheme.customTypography.jost.semiBold,
//            fontSize = 18.sp,
//            color = Color(0xFF202244)
//        )
//
//        Spacer(modifier = Modifier.height(12.dp))
//
//        Card(
//            modifier = Modifier.fillMaxWidth(),
//            shape = RoundedCornerShape(12.dp),
//            colors = CardDefaults.cardColors(containerColor = Color.White)
//        ) {
//            Column(modifier = Modifier.padding(16.dp)) {
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Box(
//                        modifier = Modifier
//                            .size(60.dp)
//                            .clip(RoundedCornerShape(8.dp))
//                            .background(Color(0xFFE3F2FD)),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Icon(
//                            imageVector = Icons.Outlined.Code,
//                            contentDescription = null,
//                            tint = Color(0xFF1565C0)
//                        )
//                    }
//
//                    Spacer(modifier = Modifier.width(12.dp))
//
//                    Column(modifier = Modifier.weight(1f)) {
//                        Text(
//                            text = "Flutter Development",
//                            style = MaterialTheme.customTypography.mulish.bold,
//                            fontSize = 16.sp,
//                            color = Color(0xFF202244)
//                        )
//                        Text(
//                            text = "4 of 12 lessons completed",
//                            style = MaterialTheme.customTypography.mulish.regular,
//                            fontSize = 14.sp,
//                            color = Color(0xFF6E7191)
//                        )
//                    }
//                }
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                LinearProgressIndicator(
//                    progress = 0.33f,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(8.dp)
//                        .clip(RoundedCornerShape(4.dp)),
//                    color = Color(0xFF1565C0),
//                    trackColor = Color(0xFFE3F2FD)
//                )
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                Button(
//                    onClick = { /* Continue course */ },
//                    modifier = Modifier.fillMaxWidth(),
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = Color(0xFF1565C0)
//                    ),
//                    shape = RoundedCornerShape(8.dp)
//                ) {
//                    Text(
//                        text = "Continue Learning",
//                        style = MaterialTheme.customTypography.mulish.semiBold,
//                        color = Color.White
//                    )
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun HomeTopBar(navController: NavController) {
//    Surface(
//        modifier = Modifier.fillMaxWidth(),
//        color = Color.White,
//        shadowElevation = 4.dp
//    ) {
//        Row(
//            modifier = Modifier
//                .padding(horizontal = 16.dp, vertical = 12.dp)
//                .fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                Image(
//                    painter = painterResource(id = R.drawable.ic_launcher_background), // Replace with your logo
//                    contentDescription = "Logo",
//                    modifier = Modifier
//                        .size(40.dp)
//                        .clip(CircleShape)
//                )
//                Spacer(modifier = Modifier.width(12.dp))
//                Text(
//                    text = "MindSpark",
//                    style = MaterialTheme.customTypography.jost.bold,
//                    fontSize = 20.sp,
//                    color = Color(0xFF1565C0)
//                )
//            }
//
//            IconButton(
//                onClick = { navController.navigate("profile") }
//            ) {
//                Icon(
//                    imageVector = Icons.Default.AccountCircle,
//                    contentDescription = "Profile",
//                    tint = Color(0xFF1565C0),
//                    modifier = Modifier.size(32.dp)
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun WelcomeSection() {
//    Column(
//        modifier = Modifier.padding(vertical = 16.dp)
//    ) {
//        Text(
//            text = "Hello, Student! 👋",
//            style = MaterialTheme.customTypography.jost.bold,
//            fontSize = 24.sp,
//            color = Color(0xFF202244)
//        )
//        Text(
//            text = "What would you like to learn today?",
//            style = MaterialTheme.customTypography.mulish.regular,
//            fontSize = 16.sp,
//            color = Color(0xFF6E7191)
//        )
//    }
//}
//
//@Composable
//fun SearchBar(
//    query: String,
//    onQueryChange: (String) -> Unit,
//    onSearch: () -> Unit
//) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .clickable(onClick = onSearch),
//        shape = RoundedCornerShape(12.dp),
//        colors = CardDefaults.cardColors(containerColor = Color.White)
//    ) {
//        Row(
//            modifier = Modifier
//                .padding(16.dp)
//                .fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Icon(
//                imageVector = Icons.Default.Search,
//                contentDescription = "Search",
//                tint = Color(0xFF1565C0)
//            )
//            Spacer(modifier = Modifier.width(12.dp))
//            Text(
//                text = "Search courses, mentors...",
//                style = MaterialTheme.customTypography.mulish.regular,
//                color = Color(0xFF6E7191)
//            )
//        }
//    }
//}
//
//@Composable
//fun QuickActionsSection(navController: NavController) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 24.dp),
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        QuickActionItem(
//            icon = Icons.Outlined.Book,
//            text = "Courses",
//            onClick = { navController.navigate("courses") }
//        )
//        QuickActionItem(
//            icon = Icons.Outlined.Assignment,
//            text = "My Learning",
//            onClick = { navController.navigate("myLearning") }
//        )
//        QuickActionItem(
//            icon = Icons.Outlined.People,
//            text = "Mentors",
//            onClick = { navController.navigate("mentors") }
//        )
//        QuickActionItem(
//            icon = Icons.Outlined.Bookmark,
//            text = "Saved",
//            onClick = { navController.navigate("saved") }
//        )
//    }
//}
//
//@Composable
//fun QuickActionItem(
//    icon: ImageVector,
//    text: String,
//    onClick: () -> Unit
//) {
//    Column(
//        modifier = Modifier
//            .clickable(onClick = onClick)
//            .padding(8.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Box(
//            modifier = Modifier
//                .size(48.dp)
//                .clip(RoundedCornerShape(12.dp))
//                .background(Color(0xFFE3F2FD)),
//            contentAlignment = Alignment.Center
//        ) {
//            Icon(
//                imageVector = icon,
//                contentDescription = null,
//                tint = Color(0xFF1565C0)
//            )
//        }
//        Spacer(modifier = Modifier.height(8.dp))
//        Text(
//            text = text,
//            style = MaterialTheme.customTypography.mulish.regular,
//            fontSize = 12.sp,
//            color = Color(0xFF202244)
//        )
//    }
//}
//
//@Composable
//fun FeaturedCourseCard(navController: NavController) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .clickable { navController.navigate("courseDetail/featured") }
//            .padding(vertical = 8.dp),
//        shape = RoundedCornerShape(16.dp),
//        colors = CardDefaults.cardColors(
//            containerColor = Color(0xFF1565C0)
//        )
//    ) {
//        Column(
//            modifier = Modifier.padding(20.dp)
//        ) {
//            Text(
//                text = "Featured Course",
//                style = MaterialTheme.customTypography.mulish.bold,
//                fontSize = 14.sp,
//                color = Color.White.copy(alpha = 0.8f)
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(
//                text = "Master Mobile App Development",
//                style = MaterialTheme.customTypography.jost.bold,
//                fontSize = 20.sp,
//                color = Color.White
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//            Button(
//                onClick = { navController.navigate("courseDetail/featured") },
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = Color.White
//                ),
//                shape = RoundedCornerShape(8.dp)
//            ) {
//                Text(
//                    text = "Start Learning",
//                    color = Color(0xFF1565C0),
//                    style = MaterialTheme.customTypography.mulish.semiBold
//                )
//            }
//        }
//    }
//}
//
//// ... Continue with CategoriesSection, PopularCoursesSection,
//// TopMentorsSection, and LearningProgressSection implementations
//
//@Composable
//fun CategoriesSection(
//    selectedCategory: String,
//    onCategorySelected: (String) -> Unit
//) {
//    Column(modifier = Modifier.padding(vertical = 16.dp)) {
//        Text(
//            text = "Categories",
//            style = MaterialTheme.customTypography.jost.semiBold,
//            fontSize = 18.sp,
//            color = Color(0xFF202244)
//        )
//        Spacer(modifier = Modifier.height(12.dp))
//        LazyRow(
//            horizontalArrangement = Arrangement.spacedBy(8.dp),
//            contentPadding = PaddingValues(horizontal = 4.dp)
//        ) {
//            items(
//                listOf("All", "Development", "Design", "Business", "Marketing")
//            ) { category ->
//                CategoryChip(
//                    category = category,
//                    isSelected = category == selectedCategory,
//                    onSelected = { onCategorySelected(category) }
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun CategoryChip(
//    category: String,
//    isSelected: Boolean,
//    onSelected: () -> Unit
//) {
//    Surface(
//        modifier = Modifier
//            .clickable(onClick = onSelected)
//            .border(
//                width = 1.dp,
//                color = if (isSelected) Color(0xFF1565C0) else Color(0xFFE0E0E0),
//                shape = RoundedCornerShape(20.dp)
//            ),
//        shape = RoundedCornerShape(20.dp),
//        color = if (isSelected) Color(0xFF1565C0) else Color.White
//    ) {
//        Text(
//            text = category,
//            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
//            style = MaterialTheme.customTypography.mulish.regular,
//            color = if (isSelected) Color.White else Color(0xFF6E7191)
//        )
//    }
//}