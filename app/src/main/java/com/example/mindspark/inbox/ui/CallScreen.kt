package com.example.mindspark.inbox.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeOff
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.CallEnd
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mindspark.inbox.components.CallControlButton

@Composable
fun ActiveCallScreen(
    navController: NavController,
) {
    var isMuted by remember { mutableStateOf(false) }
    var isSpeakerOn by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Caller Info
        Column(
            modifier = Modifier.padding(top = 200.dp).align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                modifier = Modifier
                    .size(width = 170.dp, height = 170.dp)
                    .clip(CircleShape),
                color = Color.Gray
            ) { }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Calling...",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )
        }

        // Call Controls with improved spacing
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 74.dp) // Increased bottom padding
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 60.dp), // Added horizontal padding
                horizontalArrangement = Arrangement.SpaceBetween // This ensures equal spacing
            ) {
                // Mute Button
                CallControlButton(
                    icon = if (isMuted) Icons.Default.MicOff else Icons.Default.Mic,
                    onClick = { isMuted = !isMuted },
                    backgroundColor = if (isMuted) Color.White.copy(alpha = 0.3f) else Color.White.copy(alpha = 0.2f),
//                    size = 62.dp // Increased button size
                )

                // End Call Button
                CallControlButton(
                    icon = Icons.Default.CallEnd,
                    backgroundColor = Color.Red,
                    onClick = { navController.navigateUp() },
//                    size = 62.dp // Slightly larger than other buttons
                )

                // Speaker Button
                CallControlButton(
                    icon = if (isSpeakerOn) Icons.AutoMirrored.Filled.VolumeUp else Icons.AutoMirrored.Filled.VolumeOff,
                    onClick = { isSpeakerOn = !isSpeakerOn },
                    backgroundColor = if (isSpeakerOn) Color.White.copy(alpha = 0.3f) else Color.White.copy(alpha = 0.2f),
//                    size = 62.dp // Increased button size
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ActiveCallScreenPreview() {
    ActiveCallScreen(navController = NavController(LocalContext.current))
}