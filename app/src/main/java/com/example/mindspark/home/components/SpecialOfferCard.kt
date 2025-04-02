package com.example.mindspark.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindspark.ui.theme.customTypography
import com.example.mindspark.courses.model.SpecialOfferModel
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@Composable
fun SpecialOfferCarousel(offers: List<SpecialOfferModel>) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    
    LazyRow(
        state = listState,
        contentPadding = PaddingValues(horizontal = 40.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(offers) { card ->
            val itemIndex = offers.indexOf(card)
            val scrollOffset = listState.layoutInfo.visibleItemsInfo
                .firstOrNull { it.index == itemIndex }?.offset ?: 0
            val cardWidth = 280.dp
            val percentVisible = 1f - (scrollOffset.absoluteValue / cardWidth.value).coerceIn(0f, 1f)
            
            Box(
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = 0.8f + (0.2f * percentVisible)
                        scaleY = 0.8f + (0.2f * percentVisible)
                        alpha = 0.5f + (0.5f * percentVisible)
                    }
                    .clickable {
                        coroutineScope.launch {
                            listState.animateScrollToItem(itemIndex)
                        }
                    }
            ) {
                SpecialOfferCard(card)
            }
        }
    }
}

@Composable
fun SpecialOfferCard(card: SpecialOfferModel) {
    Box(
        modifier = Modifier
            .size(width = 310.dp, height = 180.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        // Card background with gradient overlay
        Box(
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(16.dp))
                .background(card.cardColor)
        ) {
            // Background image with semi-transparent overlay
            Image(
                painter = painterResource(id = card.backgroundResId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .matchParentSize()
                    .clip(RoundedCornerShape(16.dp))
                    .alpha(0.7f)
            )
        }

        // Featured badge if applicable
        if (card.isFeatured) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFFAC840))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "FEATURED",
                    style = MaterialTheme.customTypography.mulish.extraBold,
                    fontSize = 10.sp,
                    color = Color(0xFF202244),
                )
            }
        }

        // Card content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top section with title and subtitle
            Column {
                Text(
                    text = card.title,
                    style = MaterialTheme.customTypography.mulish.extraBold,
                    fontSize = 16.sp,
                    color = card.textColor,
                    textAlign = TextAlign.Start,
                )
                Text(
                    text = card.subtitle,
                    style = MaterialTheme.customTypography.mulish.extraBold,
                    fontSize = 22.sp,
                    color = card.textColor,
                    textAlign = TextAlign.Start,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = card.description,
                    style = MaterialTheme.customTypography.mulish.extraBold.copy(
                        lineHeight = 16.sp
                    ),
                    fontSize = 13.sp,
                    color = card.textColor,
                    textAlign = TextAlign.Start,
                )
            }
            
            // Bottom section with expiry date and action button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                // Expiry date if available
                if (card.expiryDate.isNotEmpty()) {
                    Text(
                        text = card.expiryDate,
                        style = MaterialTheme.customTypography.mulish.bold,
                        fontSize = 12.sp,
                        color = card.textColor.copy(alpha = 0.8f),
                    )
                }
                
                // Action button
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (card.textColor == Color.White) Color.White.copy(alpha = 0.2f) else card.cardColor.copy(alpha = 0.2f))
                        .clickable { /* Handle button click */ }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = card.buttonText,
                        style = MaterialTheme.customTypography.mulish.extraBold,
                        fontSize = 12.sp,
                        color = card.textColor,
                    )
                }
            }
        }
    }
}
