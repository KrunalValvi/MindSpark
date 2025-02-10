package com.example.mindspark.profile.model

import androidx.compose.ui.graphics.vector.ImageVector

data class ProfileField(
    val label: String,
    val value: String,
    val onValueChange: (String) -> Unit,
    val icon: ImageVector,
    val isEditing: Boolean,
    val isDropdown: Boolean = false,
    val dropdownOptions: List<String>? = null
)