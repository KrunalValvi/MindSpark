package com.example.mindspark.myCourses.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mindspark.R
import com.example.mindspark.courses.model.CourseModel
import com.example.mindspark.myCourses.ui.MyCourseCompleted
import com.example.mindspark.ui.theme.customTypography

@Composable
fun MyCompletedCourseCardHorizontal(
    course: CourseModel,
    onCourseClick: (CourseModel) -> Unit,
) {
    // Track bookmark state
    var isChecked by remember { mutableStateOf(false) }

    // Bookmark icons
    val checkedImage = painterResource(R.drawable.ic_checked_bookmark)
    val uncheckedImage = painterResource(R.drawable.ic_unchecked_bookmark)

    Card(
        modifier = Modifier
            .padding(8.dp)
//            .fillMaxWidth()
//            .wrapContentHeight()
            .size(width = 360.dp, height = 134.dp)
            .clickable { onCourseClick(course) },
        shape = RoundedCornerShape(16.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            // --- LEFT SIDE: Image or Placeholder ---
            Image(
                painter = painterResource(id = course.imageRes),
                contentDescription = course.title,
                modifier = Modifier
                    .width(120.dp) // Adjust width for your design
//                    .height(120.dp)
                    .clip(RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp)),
                contentScale = ContentScale.Crop
            )

            // Add spacing between the image and text column
            Spacer(modifier = Modifier.width(8.dp))

            // --- RIGHT SIDE: Text content ---
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 10.dp)
                    .padding(vertical = 8.dp, horizontal = 8.dp)
            ) {
                // Top row: Category (orange text) + Bookmark icon on the right
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween
                ) {
                    Text(
                        text = course.category,
                        color = Color(0xFFFF5722),
                        style = MaterialTheme.customTypography.mulish.bold,
                        fontSize = 12.sp
                    )
                    Image(
                        painter = if (isChecked) checkedImage else uncheckedImage,
                        contentDescription = "Bookmark",
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .clickable { isChecked = !isChecked }
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Course title
                Text(
                    text = course.title,
                    style = MaterialTheme.customTypography.jost.semiBold,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 16.sp
                )

//                Spacer(modifier = Modifier.height(4.dp))
//
//                // Price, rating, and student count
//
//                // Price
//                Text(
//                    text = course.price, // e.g. "7058/-"
//                    style = MaterialTheme.customTypography.mulish.extraBold,
//                    fontSize = 17.sp,
//                    color = Color(0xFF007BFF)
//                )

                // Spacing
                Spacer(modifier = Modifier.height(10.dp))

                Row {
                    // Rating with star icon
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.size(11.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = course.rating, // e.g. "4.2"
                        style = MaterialTheme.customTypography.mulish.extraBold,
                        fontSize = 11.sp,
                        color = Color.Black
                    )

                    // Optional separator
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "|",
                        style = MaterialTheme.customTypography.mulish.bold,
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    // Student count
                    Text(
                        text = course.students, // e.g. "7830 Std"
                        style = MaterialTheme.customTypography.mulish.extraBold,
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp),
                    horizontalArrangement = Arrangement.End
                ){
                    Text(
                        modifier = Modifier.padding(end = 8.dp),
                        text = "Download Certificate",
                        style = MaterialTheme.customTypography.mulish.extraBold,
                        fontSize = 13.sp,
                        color = Color(0xFF167F71),
                        textDecoration = TextDecoration.Underline
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CoursesListScreenPreview() {
    MyCourseCompleted(navController = NavController(LocalContext.current))
}

@Composable
fun MyOngoingCourseCardHorizontal(
    course: CourseModel,
    onCourseClick: (CourseModel) -> Unit,
) {
    // Track bookmark state
    var isChecked by remember { mutableStateOf(false) }

    // Bookmark icons
    val checkedImage = painterResource(R.drawable.ic_checked_bookmark)
    val uncheckedImage = painterResource(R.drawable.ic_unchecked_bookmark)

    val progressValue by remember { mutableStateOf(0.23f) }
    val totalUnits = 100
    val completedUnits = (progressValue * totalUnits).toInt()

    Card(
        modifier = Modifier
            .padding(8.dp)
            .size(width = 360.dp, height = 134.dp)
            .clickable { onCourseClick(course) },
        shape = RoundedCornerShape(16.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Image(
                painter = painterResource(id = course.imageRes),
                contentDescription = course.title,
                modifier = Modifier
                    .width(120.dp) // Adjust width for your design
                    .clip(RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp)),
                contentScale = ContentScale.Crop
            )

            // Add spacing between the image and text column
            Spacer(modifier = Modifier.width(8.dp))

            // --- RIGHT SIDE: Text content ---
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 10.dp)
                    .padding(vertical = 8.dp, horizontal = 8.dp)
            ) {
                // Top row: Category (orange text) + Bookmark icon on the right
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween
                ) {
                    Text(
                        text = course.category,
                        color = Color(0xFFFF5722),
                        style = MaterialTheme.customTypography.mulish.bold,
                        fontSize = 12.sp
                    )
                    Image(
                        painter = if (isChecked) checkedImage else uncheckedImage,
                        contentDescription = "Bookmark",
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .clickable { isChecked = !isChecked }
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Course title
                Text(
                    text = course.title,
                    style = MaterialTheme.customTypography.jost.semiBold,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 16.sp
                )

                // Spacing
                Spacer(modifier = Modifier.height(10.dp))

                Row {
                    // Rating with star icon
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.size(11.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = course.rating, // e.g. "4.2"
                        style = MaterialTheme.customTypography.mulish.extraBold,
                        fontSize = 11.sp,
                        color = Color.Black
                    )

                    // Optional separator
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "|",
                        style = MaterialTheme.customTypography.mulish.bold,
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    // Student count
                    Text(
                        text = course.students, // e.g. "7830 Std"
                        style = MaterialTheme.customTypography.mulish.extraBold,
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Progress bar with text showing "completed/total"
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LinearProgressIndicator(
                        progress = { progressValue },
                        modifier = Modifier
                            .weight(1f)
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = Color(0xFF167F71),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "$completedUnits/$totalUnits",
                        style = MaterialTheme.customTypography.mulish.extraBold,
                        fontSize = 12.sp,
                        color = Color.Black
                    )
                }

            }
        }
    }
}