package com.example.mindspark.courses.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindspark.ui.theme.customTypography

@Composable
fun ToggleSelectionRowCourses(
    options: List<String>, // List of values (e.g., "Courses", "Mentors")
    selectedOption: String, // Currently selected option
    onOptionSelected: (String) -> Unit, // Callback when an option is clicked
    modifier: Modifier = Modifier // Optional modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
//            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween // Even spacing
    ) {
        options.forEach { option ->
            Box(
                modifier = Modifier
                    .weight(1f) // Equal width for both boxes
                    .padding(horizontal = 8.dp)
                    .clip(RoundedCornerShape(30.dp)) // Rounded corners
                    .background(
                        if (option == selectedOption) Color(0xFF167F71) // Selected color
                        else Color(0xFFE8F1FF) // Unselected color
                    )
                    .clickable { onOptionSelected(option) } // Handle clicks
                    .padding(vertical = 12.dp), // Padding inside the box
                contentAlignment = Alignment.Center // Center the text
            ) {
                Text(
                    text = option,
                    color = if (option == selectedOption) Color(0xFFFFFFFF) else Color(0xFF202244), // Text color
                    style = MaterialTheme.customTypography.mulish.extraBold,
                    fontSize = 15.sp
                )
            }
        }
    }
}

@Composable
fun ToggleSelectionRowMentor(
    options: List<String>, // List of values (e.g., "Courses", "Mentors")
    selectedOption: String, // Currently selected option
    onOptionSelected: (String) -> Unit, // Callback when an option is clicked
    modifier: Modifier = Modifier // Optional modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
//            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween // Even spacing
    ) {
        options.forEach { option ->
            Box(
                modifier = Modifier
                    .weight(1f) // Equal width for both boxes
                    .padding(horizontal = 8.dp)
                    .clip(RoundedCornerShape(30.dp)) // Rounded corners
                    .background(
                        if (option == selectedOption) Color(0xFFE8F1FF) // Selected color
                        else Color(0xFF167F71) // Unselected color
                    )
                    .clickable { onOptionSelected(option) } // Handle clicks
                    .padding(vertical = 12.dp), // Padding inside the box
                contentAlignment = Alignment.Center // Center the text
            ) {
                Text(
                    text = option,
                    color = if (option == selectedOption) Color(0xFF202244) else Color(0xFFFFFFFF), // Text color
                    style = MaterialTheme.customTypography.mulish.extraBold,
                    fontSize = 15.sp
                )
            }
        }
    }
}

