package com.example.mindspark.courses.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindspark.R
import com.example.mindspark.ui.theme.customTypography
import com.example.mindspark.courses.model.CourseModel

@Composable
fun PopularCourseCardHorizontal(course: CourseModel, onCourseClick: (CourseModel) -> Unit) {

    var isChecked by remember { mutableStateOf(false) }
    val checkedImage = painterResource(R.drawable.ic_checked_bookmark)
    val uncheckedImage = painterResource(R.drawable.ic_unchecked_bookmark)

    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(250.dp)
            .wrapContentHeight()
            .clickable { onCourseClick(course) },
        shape = RoundedCornerShape(12.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            // Placeholder for image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(Color.Black), // Replace with Image later
                contentAlignment = Alignment.Center
            ) {
                Text("Image Placeholder", color = Color.White)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Row {
                    // Category Text
                    Text(
                        text = course.category,
                        color = Color(0xFFFF5722), // Orange color
                        style = MaterialTheme.customTypography.mulish.bold,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = if (isChecked) checkedImage else uncheckedImage,
                        contentDescription = "Bookmark",
                        modifier = Modifier
                            .clickable { isChecked = !isChecked } // Toggle the state on click
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Title
                Text(
                    text = course.title,
                    style = MaterialTheme.customTypography.jost.semiBold,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Price
                    Text(
                        text = course.price,
                        style = MaterialTheme.customTypography.mulish.extraBold,
                        fontSize = 15.sp,
                        color = Color(0xFF007BFF) // Blue color
                    )
                    Text(
                        text = "|",
                        style = MaterialTheme.customTypography.mulish.bold,
                        fontSize = 14.sp
                    )
                    Row {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Rating",
                            tint = Color(0xFFFFC107), // Yellow color
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = course.rating,
                            style = MaterialTheme.customTypography.mulish.extraBold,
                            fontSize = 14.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                    Text(
                        text = "|",
                        style = MaterialTheme.customTypography.mulish.bold,
                        fontSize = 14.sp
                    )
                    Text(
                        text = course.students,
                        style = MaterialTheme.customTypography.mulish.extraBold,
                        fontSize = 11.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.width(4.dp))
                }
            }
        }
    }
}

@Composable
fun PopularCourseCardVertical(course: CourseModel, onCourseClick: (CourseModel) -> Unit) {

    var isChecked by remember { mutableStateOf(false) }
    val checkedImage = painterResource(R.drawable.ic_checked_bookmark)
    val uncheckedImage = painterResource(R.drawable.ic_unchecked_bookmark)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onCourseClick(course) },
        shape = RoundedCornerShape(12.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            // Placeholder for image
            Box(
                modifier = Modifier
                    .height(120.dp)
                    .width(110.dp)
                    .background(Color.Black), // Replace with Image later
                contentAlignment = Alignment.Center
            ) {
                Text("Image ", color = Color.White)
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Category Text
                    Text(
                        text = course.category,
                        color = Color(0xFFFF5722), // Orange color
                        style = MaterialTheme.customTypography.mulish.bold,
                        fontSize = 12.sp
                    )
                    Image(
                        painter = if (isChecked) checkedImage else uncheckedImage,
                        contentDescription = "Bookmark",
                        modifier = Modifier
                            .clickable { isChecked = !isChecked } // Toggle the state on click
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Title
                Text(
                    text = course.title,
                    style = MaterialTheme.customTypography.jost.semiBold,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Price
                Text(
                    text = course.price,
                    style = MaterialTheme.customTypography.mulish.extraBold,
                    fontSize = 17.sp,
                    color = Color(0xFF007BFF) // Blue color
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(2.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Rating",
                            tint = Color(0xFFFFC107), // Yellow color
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = course.rating,
                            style = MaterialTheme.customTypography.mulish.extraBold,
                            fontSize = 14.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(15.dp))

                    Text(
                        text = "|",
                        style = MaterialTheme.customTypography.mulish.bold,
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = course.students,
                        style = MaterialTheme.customTypography.mulish.extraBold,
                        fontSize = 11.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.width(4.dp))
                }
            }
        }
    }
}