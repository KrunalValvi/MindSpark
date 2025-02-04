package com.example.mindspark.profile.ui.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mindspark.auth.components.AuthButton
import com.example.mindspark.auth.components.AuthTopBar
import com.example.mindspark.notifications.ui.NotificationsScreen
import com.example.mindspark.profile.components.NotificationItem
import com.example.mindspark.ui.theme.LightBlueBackground

@Composable
fun ProfileNotificationsScreen(navController: NavController) {
    // State for each toggle button
    var specialOffers by remember { mutableStateOf(true) }
    var sound by remember { mutableStateOf(true) }
    var vibrate by remember { mutableStateOf(false) }
    var generalNotification by remember { mutableStateOf(true) }
    var promoDiscount by remember { mutableStateOf(false) }
    var paymentOptions by remember { mutableStateOf(false) }
    var appUpdate by remember { mutableStateOf(true) }
    var newService by remember { mutableStateOf(false) }
    var newTips by remember { mutableStateOf(true) }

    Scaffold(
        modifier = Modifier.background(LightBlueBackground),
        containerColor = LightBlueBackground,
        topBar = {
            AuthTopBar(
                title = "Notification",
                onBackClick = { navController.navigateUp() }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LightBlueBackground)
                .padding(padding)
                .padding(horizontal = 18.dp, vertical = 8.dp)
        ) {

            // Notification items
            NotificationItem(
                title = "Special Offers",
                checked = specialOffers,
                onCheckedChange = { specialOffers = it }
            )

            NotificationItem(
                title = "Sound",
                checked = sound,
                onCheckedChange = { sound = it }
            )

            NotificationItem(
                title = "Vibrate",
                checked = vibrate,
                onCheckedChange = { vibrate = it }
            )

            NotificationItem(
                title = "General Notification",
                checked = generalNotification,
                onCheckedChange = { generalNotification = it }
            )

            NotificationItem(
                title = "Promo & Discount",
                checked = promoDiscount,
                onCheckedChange = { promoDiscount = it }
            )

            NotificationItem(
                title = "Payment Options",
                checked = paymentOptions,
                onCheckedChange = { paymentOptions = it }
            )

            NotificationItem(
                title = "App Update",
                checked = appUpdate,
                onCheckedChange = { appUpdate = it }
            )

            NotificationItem(
                title = "New Service Available",
                checked = newService,
                onCheckedChange = { newService = it }
            )

            NotificationItem(
                title = "New Tips Available",
                checked = newTips,
                onCheckedChange = { newTips = it }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotificationsScreenPreview() {
    ProfileNotificationsScreen(navController = NavController(LocalContext.current))
}