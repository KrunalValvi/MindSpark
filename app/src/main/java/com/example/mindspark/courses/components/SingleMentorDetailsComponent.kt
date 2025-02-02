package com.example.mindspark.courses.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindspark.R
import com.example.mindspark.courses.model.MentorCourseModel
import com.example.mindspark.courses.model.ReviewModel
import com.example.mindspark.ui.theme.customTypography

@Composable
fun MentorCourseItem(course: MentorCourseModel) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF4FD)),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                text = course.title,
                style = MaterialTheme.customTypography.jost.semiBold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Color(0xFFFF9C07)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = course.rating,
                        style = MaterialTheme.customTypography.mulish.bold,
                        fontSize = 12.sp
                    )
                }
                Text(
                    text = course.price,
                    style = MaterialTheme.customTypography.jost.semiBold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun FollowButton(modifier: Modifier = Modifier) {
    val isFollowing = remember { mutableStateOf(false) }

    Button(
        onClick = { isFollowing.value = !isFollowing.value },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFE8F1FF)
        ),
        modifier = modifier.height(60.dp),
        shape = RoundedCornerShape(40.dp)
    ) {
        Text(
            text = if (isFollowing.value) "Unfollow" else "Follow",
            style = MaterialTheme.customTypography.jost.semiBold,
            fontSize = 18.sp
        )
    }
}


@Composable
fun ReviewItem(review: ReviewModel) {
    var isLiked by remember { mutableStateOf(false) }
    var likeCount by remember { mutableStateOf(0) }

    Row(modifier = Modifier.padding(vertical = 8.dp)) {
        Image(
            painter = painterResource(id = R.drawable.ic_profile_placeholder),
            contentDescription = "Reviewer",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )
        Spacer(Modifier.width(8.dp))
        Column {
            Text(
                text = review.reviewerName,
                style = MaterialTheme.customTypography.jost.semiBold,
                fontSize = 17.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = review.reviewText,
                style = MaterialTheme.customTypography.mulish.bold,
                fontSize = 13.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Like",
                    tint = if (isLiked) Color.Red else Color.Gray,
                    modifier = Modifier
                        .size(16.dp)
                        .clickable {
                            isLiked = !isLiked
                            likeCount += if (isLiked) 1 else -1
                        }
                )
                Spacer(Modifier.width(5.dp))
                Text(
                    text = likeCount.toString(),
                    style = MaterialTheme.customTypography.mulish.extraBold,
                    fontSize = 12.sp
                )
                Spacer(Modifier.width(25.dp))
                Text(
                    text = review.date,
                    style = MaterialTheme.customTypography.mulish.extraBold,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}