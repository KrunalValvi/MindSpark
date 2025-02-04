package com.example.mindspark.profile.components

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import androidx.navigation.compose.rememberNavController
import com.example.mindspark.R
import com.example.mindspark.auth.ui.login.LoginScreen
import com.example.mindspark.ui.theme.customTypography

@Composable
fun ProfileHeaderCard(navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                contentAlignment = Alignment.BottomEnd
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_profile_placeholder),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color(0xFF1565C0), CircleShape),
                    contentScale = ContentScale.Crop
                )
                FloatingActionButton(
                    onClick = { /* Handle photo change */ },
                    modifier = Modifier
                        .size(40.dp)
                        .offset(x = (-8).dp, y = (-8).dp),
                    containerColor = Color(0xFF1565C0)
                ) {
                    Icon(
                        imageVector = Icons.Default.Camera,
                        contentDescription = "Change Photo",
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Alex Hernandez",
                style = MaterialTheme.customTypography.jost.bold,
                fontSize = 24.sp,
                color = Color(0xFF202244)
            )

            Text(
                text = "Student",
                style = MaterialTheme.customTypography.mulish.regular,
                fontSize = 16.sp,
                color = Color(0xFF6E7191)
            )

            Text(
                text = "hernandex.redial@gmail.ac.in",
                style = MaterialTheme.customTypography.mulish.regular,
                fontSize = 14.sp,
                color = Color(0xFF6E7191)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = { navController.navigate("EditProfileScreen") },
                modifier = Modifier.fillMaxWidth(),
                border = BorderStroke(1.dp, Color(0xFF1565C0)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null,
                    tint = Color(0xFF1565C0)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Edit Profile",
                    color = Color(0xFF1565C0)
                )
            }
        }
    }
}

@Composable
fun QuickStatsCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatItem(
                icon = Icons.Default.School,
                value = "12",
                label = "Courses",
                onClick = { /* Navigate to courses */ }
            )
            VerticalDivider()
            StatItem(
                icon = Icons.Default.Assignment,
                value = "8",
                label = "Pending Tasks",
                onClick = { /* Navigate to tasks */ }
            )
            VerticalDivider()
            StatItem(
                icon = Icons.Default.EmojiEvents,
                value = "15",
                label = "Certificates",
                onClick = { /* Navigate to certificates */ }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    QuickStatsCard()
}

@Composable
fun StatItem(
    icon: ImageVector,
    value: String,
    label: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF1565C0),
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = value,
            style = MaterialTheme.customTypography.jost.bold,
            fontSize = 20.sp,
            color = Color(0xFF202244)
        )
        Text(
            text = label,
            style = MaterialTheme.customTypography.mulish.regular,
            fontSize = 14.sp,
            color = Color(0xFF6E7191)
        )
    }
}

@Composable
fun VerticalDivider() {
    Divider(
        modifier = Modifier
            .height(40.dp)
            .width(1.dp),
        color = Color(0xFFE0E0E0)
    )
}

@Composable
fun SettingsGroupList(navController: NavController) {
    val settingsGroups = listOf(
        SettingsGroup(
            title = "Account",
            settings = listOf(
                Setting(
                    icon = Icons.Default.Security,
                    title = "Security",
                    route = "SecurityScreen"
                ),
                Setting(
                    icon = Icons.Default.Notifications,
                    title = "Notifications",
                    route = "ProfileNotificationsScreen"
                ),
                Setting(
                    icon = Icons.Default.Payment,
                    title = "Payment Methods",
                    route = "PaymentOptionScreen"
                )
            )
        ),
        SettingsGroup(
            title = "Preferences",
            settings = listOf(
                Setting(
                    icon = Icons.Default.Language,
                    title = "Language",
                    route = "LanguageScreen"
                ),
                Setting(
                    icon = Icons.Default.DarkMode,
                    title = "Dark Mode",
                    route = ""
                )
            )
        ),
        SettingsGroup(
            title = "Support",
            settings = listOf(
                Setting(
                    icon = Icons.Default.Help,
                    title = "Help Center",
                    route = "HelpCenterScreen"
                ),
                Setting(
                    icon = Icons.Default.Description,
                    title = "Terms & Conditions",
                    route = "TermsScreen"
                ),
                Setting(
                    icon = Icons.Default.Share,
                    title = "Invite Friends",
                    route = "InviteFriendsScreen"
                )
            )
        )
    )

    Column(
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        settingsGroups.forEach { group ->
            SettingsGroup(
                group = group,
                onSettingClick = { route ->
                    navController.navigate(route)
                }
            )
        }
    }
}

@Composable
fun SettingsGroup(
    group: SettingsGroup,
    onSettingClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = group.title,
                style = MaterialTheme.customTypography.jost.semiBold,
                fontSize = 18.sp,
                color = Color(0xFF202244),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            group.settings.forEach { setting ->
                SettingItem(
                    setting = setting,
                    onClick = { onSettingClick(setting.route) }
                )
                if (group.settings.indexOf(setting) < group.settings.lastIndex) {
                    Divider(color = Color(0xFFE0E0E0))
                }
            }
        }
    }
}

@Composable
fun SettingItem(
    setting: Setting,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = setting.icon,
                contentDescription = null,
                tint = Color(0xFF1565C0),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = setting.title,
                style = MaterialTheme.customTypography.mulish.semiBold,
                fontSize = 16.sp,
                color = Color(0xFF202244)
            )
        }
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = Color(0xFF6E7191)
        )
    }
}

data class SettingsGroup(
    val title: String,
    val settings: List<Setting>
)

data class Setting(
    val icon: ImageVector,
    val title: String,
    val route: String
)