package com.example.mindspark.admin.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage


@Composable
fun AdminCourseCard(
    category: String,
    title: String,
    description: String,
    imageUrl: String?,
    rating: String?,
    students: String?,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            // Show image if available
            if (!imageUrl.isNullOrEmpty()) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Course Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .background(Color.LightGray),
                    alignment = Alignment.Center
                )
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = category,
                    fontSize = 14.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "⭐ ${rating ?: "N/A"}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFFFF9800) // Orange color for rating
                    )
                    Text(
                        text = "👥 ${students ?: "0"} students",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF4CAF50) // Green color for students count
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = description,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}


@Composable
fun AdminMentorCard(name: String ,profession: String, bio: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = profession,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = bio,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun AdminUsersCard(fullName: String ,email: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
//            .padding(vertical = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = fullName,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = email,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
//            Spacer(modifier = Modifier.height(4.dp))
//            Text(
//                text = ,
//                fontSize = 14.sp,
//                color = Color.Gray
//            )
        }
    }
}