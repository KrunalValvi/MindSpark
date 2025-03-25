package com.example.mindspark.courses.components

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
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.mindspark.R
import com.example.mindspark.courses.model.CourseModel
import com.example.mindspark.ui.theme.customTypography
import androidx.compose.foundation.Image as ComposeImage

@Composable
fun PopularCourseCardVertical(course: CourseModel, onCourseClick: (CourseModel) -> Unit) {
    var isChecked by remember { mutableStateOf(false) }
    // Use local bookmark resources for checked/unchecked states
    val checkedImage = painterResource(R.drawable.ic_checked_bookmark)
    val uncheckedImage = painterResource(R.drawable.ic_unchecked_bookmark)

    Card(
        modifier = Modifier
            .padding(8.dp)
            .size(width = 280.dp, height = 240.dp)
            .clickable { onCourseClick(course) },
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            // Course Image loaded with Coil (AsyncImage) from Firebase backend.
            AsyncImage(
                model = course.imageRes,
                contentDescription = "Course Image",
                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(160.dp)
                    .size(width = 280.dp, height = 134.dp)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(10.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
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
                        modifier = Modifier.padding(end = 10.dp).clickable { isChecked = !isChecked }
                    )
                }

                Spacer(modifier = Modifier.height(7.dp))

                Text(
                    text = course.title,
                    style = MaterialTheme.customTypography.jost.semiBold,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = course.price,
                        style = MaterialTheme.customTypography.mulish.extraBold,
                        fontSize = 15.sp,
                        color = Color(0xFF007BFF)
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
                            tint = Color(0xFFFFC107),
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
                }
            }
        }
    }
}

@Composable
fun PopularCourseCardHorizontal(course: CourseModel, onCourseClick: (CourseModel) -> Unit) {
    val isCheckedState = remember { mutableStateOf(false) }
    val checkedImage = painterResource(R.drawable.ic_checked_bookmark)
    val uncheckedImage = painterResource(R.drawable.ic_unchecked_bookmark)

    Card(
        modifier = Modifier
            .padding(8.dp)
            .size(width = 360.dp, height = 130.dp)
            .clickable { onCourseClick(course) },
        shape = RoundedCornerShape(16.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            AsyncImage(
                model = course.imageRes,
                contentDescription = "Course Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .background(Color.LightGray),
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 10.dp)
                    .padding(vertical = 8.dp, horizontal = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = course.category,
                        color = Color(0xFFFF5722),
                        style = MaterialTheme.customTypography.mulish.bold,
                        fontSize = 12.sp
                    )
                    ComposeImage(
                        painter = if (isCheckedState.value) checkedImage else uncheckedImage,
                        contentDescription = "Bookmark",
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .clickable { isCheckedState.value = !isCheckedState.value }
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = course.title,
                    style = MaterialTheme.customTypography.jost.semiBold,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = course.price,
                    style = MaterialTheme.customTypography.mulish.extraBold,
                    fontSize = 17.sp,
                    color = Color(0xFF007BFF)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.size(11.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = course.rating,
                        style = MaterialTheme.customTypography.mulish.extraBold,
                        fontSize = 11.sp,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "|",
                        style = MaterialTheme.customTypography.mulish.bold,
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = course.students,
                        style = MaterialTheme.customTypography.mulish.extraBold,
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}
