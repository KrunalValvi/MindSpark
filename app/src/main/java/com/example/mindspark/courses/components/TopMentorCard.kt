package com.example.mindspark.courses.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindspark.ui.theme.customTypography
import com.example.mindspark.courses.model.MentorModel

@Composable
fun TopMentorCardHorizontal(mentor: MentorModel, onClick: (MentorModel) -> Unit) {
    Column(
        modifier = Modifier
            .padding(top = 8.dp)
            .width(80.dp)
            .clickable { onClick(mentor) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Image(
                painter = painterResource(id = mentor.imageRes),
                contentDescription = "Profile picture of ${mentor.name}",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = mentor.name,
            color = Color.Black,
            style = MaterialTheme.customTypography.jost.semiBold,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun TopMentorCardVertical(mentor: MentorModel, onClick: (MentorModel) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .clickable { onClick(mentor) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Image(
                painter = painterResource(id = mentor.imageRes),
                contentDescription = "Profile picture of ${mentor.name}",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column {
            Text(
                text = mentor.name,
                color = Color.Black,
                style = MaterialTheme.customTypography.jost.semiBold,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = mentor.profession,
                color = Color.Black,
                style = MaterialTheme.customTypography.mulish.bold,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}