package com.example.mindspark.profile.ui.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mindspark.auth.components.AuthTopBar
import com.example.mindspark.ui.theme.LightBlueBackground
import com.example.mindspark.ui.theme.customTypography

@Composable
fun HelpCenterItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick)
            .border(
                width = 1.dp,
                color = Color(0xFFBDBDBD),
                shape = RoundedCornerShape(12.dp)
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(LightBlueBackground),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color(0xFF1565C0),
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.customTypography.jost.semiBold,
                    fontSize = 16.sp,
                    color = Color(0xFF202244)
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.customTypography.mulish.semiBold,
                    fontSize = 14.sp,
                    color = Color(0xFF6E7191)
                )
            }

            Icon(
                imageVector = Icons.Default.ArrowForwardIos,
                contentDescription = "Forward",
                tint = Color(0xFF6E7191),
                modifier = Modifier.size(16.dp)
            )
        }
    }
}


@Composable
fun HelpCenterScreen(navController: NavController) {
    Scaffold(
        modifier = Modifier.background(LightBlueBackground),
        containerColor = LightBlueBackground,
        topBar = {
            AuthTopBar(
                title = "Help Center",  // Changed from "Security" to "Help Center"
                onBackClick = { navController.navigateUp() }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(LightBlueBackground)
                .padding(padding)
                .padding(horizontal = 18.dp, vertical = 8.dp)
        ) {
            // Welcome Message
            item {
                Text(
                    text = "How can we help you?",
                    style = MaterialTheme.customTypography.jost.semiBold,
                    fontSize = 24.sp,
                    color = Color(0xFF202244),
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }

            // Search Bar (Optional - uncomment if needed)
            /*item {
                SearchBar(
                    query = searchQuery,
                    onQueryChange = { searchQuery = it },
                    onSearch = { },
                    active = false,
                    onActiveChange = { },
                    placeholder = { Text("Search help articles") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
            }*/

            // FAQ Section
            item {
                HelpCenterItem(
                    icon = Icons.Outlined.QuestionAnswer,
                    title = "FAQ",
                    subtitle = "Frequently asked questions",
                    onClick = { /* Navigate to FAQ */ }
                )
            }

            // Contact Support
            item {
                HelpCenterItem(
                    icon = Icons.Outlined.Support,
                    title = "Contact Support",
                    subtitle = "Get help from our team",
                    onClick = { /* Navigate to Contact Support */ }
                )
            }

            // Account & Billing
            item {
                HelpCenterItem(
                    icon = Icons.Outlined.AccountBalance,
                    title = "Account & Billing",
                    subtitle = "Manage your account and payments",
                    onClick = { /* Navigate to Account & Billing Help */ }
                )
            }

            // Getting Started Guide
            item {
                HelpCenterItem(
                    icon = Icons.Outlined.Lightbulb,
                    title = "Getting Started",
                    subtitle = "Learn the basics of MindSpark",
                    onClick = { /* Navigate to Getting Started Guide */ }
                )
            }

            // Report an Issue
            item {
                HelpCenterItem(
                    icon = Icons.Outlined.BugReport,
                    title = "Report an Issue",
                    subtitle = "Let us know if something's not working",
                    onClick = { /* Navigate to Issue Reporting */ }
                )
            }

            // Quick Contact Options
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Quick Contact",
                    style = MaterialTheme.customTypography.jost.semiBold,
                    fontSize = 18.sp,
                    color = Color(0xFF202244),
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            // Email Support
            item {
                OutlinedButton(
                    onClick = { /* Handle email support */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFF1565C0)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Email,
                        contentDescription = "Email Support",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Email Support",
                        style = MaterialTheme.customTypography.mulish.semiBold
                    )
                }
            }

            // Live Chat
            item {
                OutlinedButton(
                    onClick = { /* Handle live chat */ },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFF1565C0)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Chat,
                        contentDescription = "Live Chat",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Live Chat",
                        style = MaterialTheme.customTypography.mulish.semiBold
                    )
                }
            }

            // Support Hours Note
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Support available 24/7",
                    style = MaterialTheme.customTypography.mulish.regular,
                    fontSize = 14.sp,
                    color = Color(0xFF6E7191),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HelpCenterScreenPreview() {
    HelpCenterScreen(navController = NavController(LocalContext.current))
}