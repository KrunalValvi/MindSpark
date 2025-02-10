package com.example.mindspark.profile.ui.sections

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mindspark.ui.theme.customTypography
import com.example.mindspark.profile.components.NotificationMasterSwitch
import com.example.mindspark.profile.components.NotificationSection
import com.example.mindspark.profile.components.NotificationSetting

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileNotificationsScreen(navController: NavController) {
    var masterNotificationSwitch by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Notifications",
                        style = MaterialTheme.customTypography.jost.semiBold,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color(0xFF202244)
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            NotificationMasterSwitch(
                enabled = masterNotificationSwitch,
                onEnabledChange = { masterNotificationSwitch = it }
            )

            AnimatedVisibility(
                visible = masterNotificationSwitch,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column {
                    // General Notifications Section
                    NotificationSection(
                        title = "General",
                        icon = Icons.Default.Notifications,
                        items = listOf(
                            NotificationSetting(
                                "Push Notifications",
                                "Receive push notifications",
                                Icons.Default.NotificationsActive
                            ),
                            NotificationSetting(
                                "Sound",
                                "Play sound for notifications",
                                Icons.Default.VolumeUp
                            ),
                            NotificationSetting(
                                "Vibrate",
                                "Vibrate for notifications",
                                Icons.Default.Vibration
                            )
                        )
                    )

                    // Course Updates Section
                    NotificationSection(
                        title = "Course Updates",
                        icon = Icons.Default.School,
                        items = listOf(
                            NotificationSetting(
                                "New Lessons",
                                "When new lessons are available",
                                Icons.Default.Book
                            ),
                            NotificationSetting(
                                "Assignment Deadlines",
                                "Upcoming assignment due dates",
                                Icons.Default.Assignment
                            ),
                            NotificationSetting(
                                "Course Progress",
                                "Weekly progress updates",
                                Icons.Default.Timeline
                            )
                        )
                    )

                    // Promotional Section
                    NotificationSection(
                        title = "Promotional",
                        icon = Icons.Default.Campaign,
                        items = listOf(
                            NotificationSetting(
                                "Special Offers",
                                "Exclusive deals and discounts",
                                Icons.Default.LocalOffer
                            ),
                            NotificationSetting(
                                "New Features",
                                "Updates about new app features",
                                Icons.Default.NewReleases
                            ),
                            NotificationSetting(
                                "Events",
                                "Upcoming events and webinars",
                                Icons.Default.Event
                            )
                        )
                    )
                }
            }
        }
    }
}