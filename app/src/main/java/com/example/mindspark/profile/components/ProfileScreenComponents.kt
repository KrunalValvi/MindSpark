package com.example.mindspark.profile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindspark.R
import com.example.mindspark.ui.theme.customTypography

@Composable
fun ProfileHeader() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        Box(contentAlignment = Alignment.BottomEnd) {
            Image(
                painter = painterResource(id = R.drawable.ic_profile_placeholder), // Replace with actual profile image
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .height(110.dp)
                    .width(110.dp)
                    .clip(CircleShape)
                    .background(Color.Gray),
                contentScale = ContentScale.Crop
            )
            IconButton(
                onClick = { /* Open image picker */ },
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(Color.White)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_edit_photo), // Replace with actual edit icon
                    contentDescription = "Edit Profile",
                    tint = Color.Black
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text("Alex", style = MaterialTheme.customTypography.jost.semiBold, fontSize = 24.sp)
        Text("hernandex.redial@gmail.ac.in", style = MaterialTheme.customTypography.mulish.bold, fontSize = 13.sp, color = Color.Gray)
    }
}


@Composable
fun SettingsList() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(18.dp)
            .background(Color.White, shape = RoundedCornerShape(16.dp))
            .padding(vertical = 16.dp)
    ) {
        SettingItem(icon = R.drawable.ic_edit, title = "Edit Profile")
        SettingItem(icon = R.drawable.ic_payment, title = "Payment Option")
        SettingItem(icon = R.drawable.ic_notifications, title = "Notifications")
        SettingItem(icon = R.drawable.ic_security, title = "Security")
        SettingItem(icon = R.drawable.ic_language, title = "Language")
        SettingItem(icon = R.drawable.ic_dark_mode, title = "Dark Mode")
        SettingItem(icon = R.drawable.ic_terms, title = "Terms & Conditions")
        SettingItem(icon = R.drawable.ic_help, title = "Help Center")
        SettingItem(icon = R.drawable.ic_invite, title = "Invite Friends")
    }
}

@Composable
fun SettingItem(icon: Int, title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painter = painterResource(id = icon), contentDescription = title, tint = Color.Black)
        Spacer(modifier = Modifier.width(16.dp))
        Text(title, style = MaterialTheme.customTypography.mulish.bold, fontSize = 15.sp, modifier = Modifier.weight(1f))
//        if (value != null) {
//            Text(value, fontSize = 14.sp, color = Color(0xFF202244))
//        }
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_right),
            contentDescription = "Navigate",
            tint = Color.Black
        )
    }
}
