package com.example.mindspark.navigation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.mindspark.auth.ui.login.LoginScreen


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    FloatingBottomBar(currentIcon = 0, icons = listOf(), onTap = {})
}

@Composable
fun FloatingBottomBar(
    currentIcon: Int,
    icons: List<IconModel>,
    onTap: (Int) -> Unit,

) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize()
            .zIndex(1f), // Ensures the navigation bar stays on top
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp) // Adjust this value to control how high above the bottom
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
                .height(64.dp)
                .clip(RoundedCornerShape(32.dp))
                .background(Color.White)
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(32.dp),
                    spotColor = Color.Gray.copy(alpha = 0.3f)
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                icons.forEach { icon ->
                    NavBarIcon(
                        icon = icon,
                        isSelected = currentIcon == icon.id,
                        onTap = { onTap(icon.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun NavBarIcon(
    icon: IconModel,
    isSelected: Boolean,
    onTap: () -> Unit
) {
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.2f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    val color by animateColorAsState(
        targetValue = if (isSelected) Color(0xFF008060) else Color(0xFF1E1E1E),
        animationSpec = tween(durationMillis = 300)
    )

    Box(
        modifier = Modifier
            .size(48.dp)
            .clickable(onClick = onTap),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = icon.icon),
            contentDescription = null,
            tint = color,
            modifier = Modifier
                .size(24.dp)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                }
        )
    }
}