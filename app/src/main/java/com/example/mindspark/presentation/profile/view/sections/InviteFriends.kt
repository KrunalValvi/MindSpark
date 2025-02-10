package com.example.mindspark.profile.ui.sections

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mindspark.R
import com.example.mindspark.auth.components.AuthTopBar
import com.example.mindspark.ui.theme.LightBlueBackground
import com.example.mindspark.ui.theme.customTypography
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SocialShareButton(
    icon: ImageVector,
    text: String,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            style = MaterialTheme.customTypography.mulish.semiBold,
            color = Color.White
        )
    }
}

@Composable
fun InviteFriendsScreen(navController: NavController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var showCopiedMessage by remember { mutableStateOf(false) }
    val referralCode = "MINDSPARK2024" // Example referral code

    Scaffold(
        modifier = Modifier.background(LightBlueBackground),
        containerColor = LightBlueBackground,
        topBar = {
            AuthTopBar(
                title = "Invite Friends",  // Fixed the title
                onBackClick = { navController.navigateUp() }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(LightBlueBackground)
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//            // Illustration
//            item {
//                Image(
//                    painter = painterResource(id = R.drawable.ic_launcher_background), // Replace with your invite illustration
//                    contentDescription = "Invite Friends",
//                    modifier = Modifier
//                        .size(200.dp)
//                        .padding(vertical = 24.dp),
//                    contentScale = ContentScale.Fit
//                )
//            }

            // Title and Description
//            item {
//                Text(
//                    text = "Invite Your Friends",
//                    style = MaterialTheme.customTypography.jost.semiBold,
//                    fontSize = 24.sp,
//                    color = Color(0xFF202244),
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier.padding(bottom = 8.dp)
//                )

//                Text(
//                    text = "Share MindSpark with friends and enjoy learning together!",
//                    style = MaterialTheme.customTypography.mulish.bold,
//                    fontSize = 16.sp,
//                    color = Color(0xFF6E7191),
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier.padding(bottom = 24.dp)
//                )
//            }

            // Referral Code Section
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                        .border(
                            width = 1.dp,
                            color = Color(0xFFBDBDBD),
                            shape = RoundedCornerShape(12.dp)
                        ),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Your Referral Code",
                            style = MaterialTheme.customTypography.jost.semiBold,
                            fontSize = 14.sp,
                            color = Color(0xFF6E7191)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(LightBlueBackground)
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = referralCode,
                                style = MaterialTheme.customTypography.mulish.semiBold,
                                fontSize = 18.sp,
                                color = Color(0xFF1565C0)
                            )

                            IconButton(
                                onClick = {
                                    // Copy to clipboard functionality
                                    scope.launch {
                                        showCopiedMessage = true
                                        delay(2000)
                                        showCopiedMessage = false
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ContentCopy,
                                    contentDescription = "Copy Code",
                                    tint = Color(0xFF1565C0)
                                )
                            }
                        }

                        if (showCopiedMessage) {
                            Text(
                                text = "Code copied!",
                                color = Color(0xFF1565C0),
                                style = MaterialTheme.customTypography.mulish.regular,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }
            }

            // Share Options
            item {
                Text(
                    text = "Share via",
                    style = MaterialTheme.customTypography.mulish.semiBold,
                    fontSize = 16.sp,
                    color = Color(0xFF202244),
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            // WhatsApp
            item {
                SocialShareButton(
                    icon = Icons.Outlined.Share,
                    text = "Share on WhatsApp",
                    backgroundColor = Color(0xFF25D366),
                    onClick = { /* Handle WhatsApp share */ }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Message
            item {
                SocialShareButton(
                    icon = Icons.Outlined.Message,
                    text = "Share via Message",
                    backgroundColor = Color(0xFF1565C0),
                    onClick = { /* Handle Message share */ }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Email
            item {
                SocialShareButton(
                    icon = Icons.Outlined.Email,
                    text = "Share via Email",
                    backgroundColor = Color(0xFF6E7191),
                    onClick = { /* Handle Email share */ }
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            // Rewards Info
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFE3F2FD)
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.CardGiftcard,
                            contentDescription = "Rewards",
                            tint = Color(0xFF1565C0),
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Earn rewards when friends join using your code!",
                            style = MaterialTheme.customTypography.mulish.regular,
                            fontSize = 14.sp,
                            color = Color(0xFF1565C0)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreviewewew() {
    InviteFriendsScreen(navController = NavController(LocalContext.current))
}