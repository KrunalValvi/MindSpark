package com.example.mindspark.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindspark.home.model.SearchModel
import com.example.mindspark.ui.theme.customTypography

@Composable
fun SearchRecent(searchModel: SearchModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = searchModel.name,
            color = Color.Gray,
            fontSize = 15.sp,
            style = MaterialTheme.customTypography.mulish.bold
        )

        Text(
            text = if (searchModel.isSelected) "X" else "",
            style = MaterialTheme.customTypography.mulish.bold
        )
    }
}
