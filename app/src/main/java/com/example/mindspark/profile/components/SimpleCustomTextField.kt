package com.example.mindspark.profile.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindspark.ui.theme.customTypography

@Composable
fun SimpleCustomTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        // External label text
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = label,
            style = MaterialTheme.customTypography.mulish.extraBold,
            fontSize = 14.sp,
        )
        // The editable TextField
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = label,
                    style = MaterialTheme.customTypography.mulish.bold,
                    fontSize = 14.sp,
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(15.dp),
                    spotColor = Color(0x1A000000),
                    ambientColor = Color(0x1A000000)
                ),
            shape = RoundedCornerShape(15.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = Color(0xFF1565C0)
            ),
            textStyle = MaterialTheme.customTypography.mulish.bold.copy(
                color = Color.Black
            )
        )
    }
}