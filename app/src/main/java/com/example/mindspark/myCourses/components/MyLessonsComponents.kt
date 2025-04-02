package com.example.mindspark.myCourses.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mindspark.R
import com.example.mindspark.myCourses.model.LessonItem
import com.example.mindspark.myCourses.model.Section
import com.example.mindspark.ui.theme.customTypography

/**
 * Reusable UI components: SectionCard and LessonRow
 * that can be used in any screen.
 */

/**
 * A Card representing a single section.
 * Shows "Section 01 - Introduction 25 Mins" at the top,
 * followed by a list of lessons separated by dividers.
 */


//@Preview(showBackground = true)
//@Composable
//fun MyLessonsPreview() {
//    MyLessons(
//        navController = NavController(LocalContext.current).apply {
//            createGraph(startDestination = "MyLessons") {}
//        }
//    )
//}

@Composable
fun SectionCard(
    section: Section,
    sectionIndex: Int
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.fillMaxWidth().clickable{ }

        ) {
        Column(modifier = Modifier.padding(14.dp)) {

            // Header: "Section 01 - Introduction" and "25 Mins"
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Section $sectionIndex - ${section.title}",
                    style = MaterialTheme.customTypography.jost.semiBold,
                    fontSize = 15.sp
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = section.totalTime,
                    style = MaterialTheme.customTypography.mulish.extraBold,
                    fontSize = 12.sp,
                    color = Color.Blue
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            Divider(thickness = 0.5.dp, color = Color.LightGray)
            Spacer(modifier = Modifier.height(12.dp))

            // For each lesson, show a row. Divider between rows.
            section.lessons.forEachIndexed { index, lesson ->
                LessonRow(lesson, navController = NavController(LocalContext.current))
                if (index < section.lessons.lastIndex) {
                    Divider(
                        modifier = Modifier
                            .padding(vertical = 12.dp)
                            .fillMaxWidth(),
                        thickness = 0.5.dp,
                        color = Color.LightGray
                    )
                }
            }
        }
    }
}


@Composable
fun LessonRow(
    lesson: LessonItem,
    navController: NavController
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Lesson number
        Box(
            modifier = Modifier.width(30.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = lesson.number,
                style = MaterialTheme.customTypography.jost.semiBold,
                fontSize = 14.sp
            )
        }
        Spacer(modifier = Modifier.width(8.dp))

        // Title + duration
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = lesson.lessonTitle,
                style = MaterialTheme.customTypography.jost.semiBold,
                fontSize = 16.sp,
                maxLines = 1
            )
            Text(
                text = lesson.duration,
                style = MaterialTheme.customTypography.mulish.bold,
                color = Color.Gray,
                fontSize = 13.sp
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Play icon on the right

        IconButton(
            onClick = { }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_play_circle), // Replace with your play icon
                contentDescription = "Play Lesson",
                tint = Color(0xFF007BFF)
            )
        }
    }
}




