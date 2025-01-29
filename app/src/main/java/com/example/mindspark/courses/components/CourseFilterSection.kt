package com.example.mindspark.courses.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindspark.courses.model.FilterCategory
import com.example.mindspark.courses.model.FilterItem
import com.example.mindspark.ui.theme.customTypography

@Composable
fun CustomCheckboxItem(filterItem: FilterItem, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Custom Checkbox UI
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(if (filterItem.isSelected) Color(0xFF00796B) else Color(0xFFE0E0E0))
                .clickable { onCheckedChange(!filterItem.isSelected) }
        ) {
            if (filterItem.isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Checked",
                    tint = Color.White,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Filter Name
        Text(
            text = filterItem.name,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF1A1A2E) // Dark blue text
        )
    }
}

@Composable
fun FilterCategoryItem(category: FilterCategory) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        // Category Title
        Text(
            text = category.title,
            style = MaterialTheme.customTypography.jost.semiBold,
            fontSize = 18.sp,
            modifier = Modifier.padding(5.dp)
        )

        // Loop through filter options
        category.options.forEach { item ->
            CustomCheckboxItem(filterItem = item) { isChecked ->
                item.isSelected = isChecked
            }
        }
    }
}
