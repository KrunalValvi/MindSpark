package com.example.mindspark.notifications.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mindspark.auth.components.AuthTopBar
import com.example.mindspark.ui.theme.customTypography
import com.example.mindspark.notifications.data.olderNotifications
import com.example.mindspark.notifications.data.todayNotifications
import com.example.mindspark.notifications.data.yesterdayNotifications
import com.example.mindspark.notifications.model.Notification
import kotlinx.coroutines.delay

private val LightBlueBackground = Color(0xFFF5F9FF)
private val PrimaryBlue = Color(0xFF4E74F9)
private val SectionTitleColor = Color(0xFF202244)
private val CardGradientStart = Color(0xFFEDF3FF)
private val CardGradientEnd = Color(0xFFE8F1FF)
private val IconBackgroundColor = Color(0xFFE9F0FF)
private val NotificationTitleColor = Color(0xFF202244)
private val NotificationMessageColor = Color(0xFF545454)
private val DividerColor = Color(0xFFEEF2F9)

@Composable
fun SectionTitle(title: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 12.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.customTypography.jost.bold,
            fontSize = 17.sp,
            color = SectionTitleColor,
        )

        Spacer(modifier = Modifier.width(8.dp))

        Divider(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp),
            color = DividerColor,
            thickness = 1.dp
        )
    }
}

@Composable
fun NotificationCard(notification: Notification, animationDelay: Int = 0) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        delay(animationDelay.toLong())
        visible = true
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(300)) +
                slideInVertically(initialOffsetY = { it / 5 }, animationSpec = tween(400))
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(16.dp),
                    spotColor = Color(0x1A4E74F9)
                ),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(16.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(CardGradientStart, CardGradientEnd)
                        )
                    )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        modifier = Modifier.size(48.dp),
                        shape = CircleShape,
                        color = IconBackgroundColor
                    ) {
                        Icon(
                            imageVector = notification.icon,
                            contentDescription = null,
                            modifier = Modifier.padding(12.dp),
                            tint = PrimaryBlue
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(
                            text = notification.title,
                            style = MaterialTheme.customTypography.jost.semiBold,
                            fontSize = 18.sp,
                            color = NotificationTitleColor
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = notification.message,
                            style = MaterialTheme.customTypography.mulish.bold,
                            fontSize = 14.sp,
                            color = NotificationMessageColor,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NotificationsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            AuthTopBar(
                title = "Notifications",
                onBackClick = { navController.navigateUp() }
            )
        },
        containerColor = LightBlueBackground
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(LightBlueBackground)
                .padding(padding)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }

                item {
                    SectionTitle("Today")
                }

                itemsIndexed(todayNotifications) { index, notification ->
                    NotificationCard(notification, animationDelay = index * 100)
                }

                item {
                    SectionTitle("Yesterday")
                }

                itemsIndexed(yesterdayNotifications) { index, notification ->
                    NotificationCard(notification, animationDelay = (index + todayNotifications.size) * 100)
                }

                item {
                    SectionTitle("Nov 20, 2022")
                }

                itemsIndexed(olderNotifications) { index, notification ->
                    NotificationCard(notification, animationDelay = (index + todayNotifications.size +
                            yesterdayNotifications.size) * 100)
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotificationsScreenPreview() {
    NotificationsScreen(navController = NavController(LocalContext.current))
}