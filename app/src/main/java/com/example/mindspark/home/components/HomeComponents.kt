package com.example.mindspark.home.components

import androidx.compose.foundation.lazy.LazyRow
import com.example.mindspark.courses.model.CourseCategory
import com.example.mindspark.courses.model.CourseModel
import com.example.mindspark.courses.model.MentorModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mindspark.R
import com.example.mindspark.courses.components.PopularCourseCardHorizontal
import com.example.mindspark.courses.components.PopularCourseCardVertical
import com.example.mindspark.courses.components.TopMentorCardHorizontal
import com.example.mindspark.courses.components.TopMentorCardVertical
import com.example.mindspark.ui.theme.customTypography
import com.example.mindspark.courses.model.SpecialOfferModel

@Composable
fun HomeHeader(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "Hi, Alex",
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
            modifier = Modifier.size(40.dp).clickable{ navController.navigate("NotificationsScreen")}
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
            fontSize = 18.sp,
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
    onAllSelected: () -> Unit // Callback for "All" navigation
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
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
                            onAllSelected() // Trigger navigation to the new page
                        } else {
                            onCategorySelected(category) // Handle regular category selection
                        }
                    }
                    .padding(horizontal = 16.dp, vertical = 5.dp)
            ) {
                Text(
                    text = categories[index].value,
                    style = MaterialTheme.customTypography.mulish.bold,
                    color = if (isSelected) Color.White else Color(0xFF202244), // Text color
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
        modifier = Modifier
            .fillMaxWidth(),
//            .padding(5.dp),
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
                        onCategorySelected(categories[index].value)
                    }
                    .padding(horizontal = 16.dp, vertical = 5.dp)
            ) {
                Text(
                    text = categories[index].value,
                    style = MaterialTheme.customTypography.mulish.bold,
                    color = if (isSelected) Color.White else Color(0xFF202244), // Text color
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
            com.example.mindspark.courses.components.SpecialOfferCard(card)
        }
    }
}

@Composable
fun PopularCoursesListHorizontal(courses: List<CourseModel>) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(courses.size) { index ->
            val course = courses[index]
            PopularCourseCardHorizontal(course)
        }
    }
}

@Composable
fun PopularCoursesListVertical(courses: List<CourseModel>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp) // Add spacing between items
    ) {
        items(courses.size) { index ->
            val course = courses[index]
            PopularCourseCardVertical(course)
        }
    }
}

@Composable
fun TopMentorsListHorizontal(mentors: List<MentorModel>) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(mentors.size) { index ->
            TopMentorCardHorizontal(mentors[index])
        }
    }
}

@Composable
fun TopMentorsListVertical(mentors: List<MentorModel>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp) // Add spacing between items
    ) {
        items(mentors.size) { index ->
            TopMentorCardVertical(mentors[index])
        }
    }
}