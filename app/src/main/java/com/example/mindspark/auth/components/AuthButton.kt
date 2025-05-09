package com.example.mindspark.auth.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindspark.ui.theme.customTypography


@Preview(showBackground = true)
@Composable
fun AuthButtonPreview() {
    AuthButton(text = "Button", onClick = {})
}

@Composable
fun AuthButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth(0.9f)
            .height(56.dp)
            .clip(MaterialTheme.shapes.extraLarge)
            .background(Color(0xFF1565C0))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center, // Centers content
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = text,
                textAlign = TextAlign.Center,
                color = Color.White,
                style = MaterialTheme.customTypography.jost.semiBold,
                fontSize = 16.sp,
                modifier = Modifier.weight(1f) // Centers text properly
            )

            Box(
                modifier = Modifier
                    .padding(end = 6.dp)
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .align(Alignment.CenterVertically), // Ensures icon stays aligned
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Arrow Icon",
                    tint = Color(0xFF1565C0),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
