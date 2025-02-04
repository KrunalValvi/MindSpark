package com.example.mindspark.courses.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mindspark.courses.model.FilterCategory
import com.example.mindspark.courses.model.FilterItem
import com.example.mindspark.courses.ui.CoursesFilterScreen
import com.example.mindspark.ui.theme.customTypography


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    CustomCheckboxItem(filterItem = FilterItem("Option 1", false), onCheckedChange = {})
}

@Composable
fun CustomCheckboxItem(
    filterItem: FilterItem,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                onCheckedChange(!filterItem.isSelected)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(if (filterItem.isSelected) Color(0xFF00796B) else Color(0xFFE0E0E0))
                .clickable {
                    onCheckedChange(!filterItem.isSelected)
                }
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

        Text(
            text = filterItem.name,
            style = MaterialTheme.customTypography.mulish.bold,
            fontSize = 14.sp,
            color = Color(0xFF202244)
        )
    }
}


@Composable
fun FilterCategoryItem(category: FilterCategory) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = category.title,
            style = MaterialTheme.customTypography.jost.semiBold,
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        category.options.forEach { item ->
            val isSelected = remember { mutableStateOf(item.isSelected) }

            CustomCheckboxItem(
                filterItem = item.copy(isSelected = isSelected.value),
                onCheckedChange = { checked ->
                    isSelected.value = checked
                    item.isSelected = checked
                }
            )
        }
    }
}