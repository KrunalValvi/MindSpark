package com.example.mindspark.bookmark.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindspark.bookmark.model.BookMarkModel
import com.example.mindspark.ui.theme.customTypography
import com.example.mindspark.R
import com.example.mindspark.courses.model.CourseModel

@Composable
fun BookMarkCard(course: CourseModel, bookmark: BookMarkModel, onCourseClick: (BookMarkModel) -> Unit) {
    var isChecked by remember { mutableStateOf(true) }
    val checkedImage = painterResource(id = R.drawable.ic_checked_bookmark)
    val uncheckedImage = painterResource(id = R.drawable.ic_unchecked_bookmark)

    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(250.dp)
            .wrapContentHeight()
            .clickable { onCourseClick(bookmark) },
        shape = RoundedCornerShape(12.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            // Course Image
            Image(
                painter = painterResource(id = course.imageRes),
                contentDescription = bookmark.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Row {
                    Text(
                        text = bookmark.course,
                        color = Color(0xFFFF5722),
                        style = MaterialTheme.customTypography.mulish.bold,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = if (isChecked) checkedImage else uncheckedImage,
                        contentDescription = "Bookmark",
                        modifier = Modifier.clickable { isChecked = !isChecked }
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = bookmark.title,
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
                    Text(
                        text = bookmark.status,
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
                            text = "4.5", // Placeholder for rating
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
                        text = "1200", // Placeholder for students
                        style = MaterialTheme.customTypography.mulish.extraBold,
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}