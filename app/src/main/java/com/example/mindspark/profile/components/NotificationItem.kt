package com.example.mindspark.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindspark.ui.theme.customTypography

@Composable
fun NotificationItem(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.customTypography.mulish.bold,
            fontSize = 16.sp,
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(

                // Customize switch colors as needed
                checkedThumbColor = Color(0xFF0961F5),
                checkedTrackColor = Color(0xFFE8F1FF),

                uncheckedThumbColor = Color(0xFFFFFFFF),
                uncheckedTrackColor = Color(0xFFE8F1FF),

                checkedBorderColor = Color(0xFFBDBDBD),
                uncheckedBorderColor = Color(0xFFBDBDBD)
            )
        )
    }
}
