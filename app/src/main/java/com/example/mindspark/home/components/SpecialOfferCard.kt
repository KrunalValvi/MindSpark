package com.example.mindspark.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindspark.ui.theme.customTypography
import com.example.mindspark.courses.model.SpecialOfferModel

@Composable
fun SpecialOfferCard(card: SpecialOfferModel) {
    Box(
        modifier = Modifier
            .size(width = 280.dp, height = 180.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = card.backgroundResId),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(16.dp))
        )

        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = card.title,
                    style = MaterialTheme.customTypography.mulish.extraBold,
                    fontSize = 15.sp,
                    color = Color.White,
                    textAlign = TextAlign.Start,
                )
                Text(
                    text = card.subtitle,
                    style = MaterialTheme.customTypography.mulish.extraBold,
                    fontSize = 22.sp,
                    color = Color.White,
                    textAlign = TextAlign.Start,
                )
                Spacer(modifier = Modifier.padding(top = 10.dp))
                Text(
                    text = card.description,
                    style = MaterialTheme.customTypography.mulish.extraBold.copy(
                        lineHeight = 16.sp
                    ),
                    fontSize = 13.sp,
                    color = Color.White,
                    textAlign = TextAlign.Start,
                )
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                for (i in 0..4) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .background(
                                if (i == 2) Color(0xFFFAC840) else Color(0xFF1A6EFC),
                                CircleShape
                            )
                    )
                    if (i < 4) Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    }
}
