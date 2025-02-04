package com.example.mindspark.profile.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindspark.profile.model.ProfileField
import com.example.mindspark.ui.theme.customTypography

@Composable
fun ProfileSection(
    title: String,
    fields: List<ProfileField>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.customTypography.jost.semiBold,
                fontSize = 18.sp,
                color = Color(0xFF202244)
            )

            Spacer(modifier = Modifier.height(16.dp))

            fields.forEach { field ->
                ProfileFieldItem(field)
                if (fields.indexOf(field) < fields.lastIndex) {
                    Divider(
                        modifier = Modifier.padding(vertical = 12.dp),
                        color = Color(0xFFE0E0E0)
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileFieldItem(field: ProfileField) {
    var showDropdown by remember { mutableStateOf(false) }

    Column {
        Text(
            text = field.label,
            style = MaterialTheme.customTypography.mulish.regular,
            fontSize = 14.sp,
            color = Color(0xFF6E7191)
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (field.isDropdown && field.isEditing) {
            Box {
                OutlinedButton(
                    onClick = { showDropdown = true },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, Color(0xFFE0E0E0))
                ) {
                    Icon(
                        imageVector = field.icon,
                        contentDescription = null,
                        tint = Color(0xFF1565C0),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = field.value,
                        style = MaterialTheme.customTypography.mulish.regular,
                        color = Color(0xFF202244)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null
                    )
                }

                DropdownMenu(
                    expanded = showDropdown,
                    onDismissRequest = { showDropdown = false }
                ) {
                    field.dropdownOptions?.forEach { option ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = option,
                                    style = MaterialTheme.customTypography.mulish.regular
                                )
                            },
                            onClick = {
                                field.onValueChange(option)
                                showDropdown = false
                            }
                        )
                    }
                }
            }
        } else {
            OutlinedTextField(
                value = field.value,
                onValueChange = field.onValueChange,
                enabled = field.isEditing,
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = field.icon,
                        contentDescription = null,
                        tint = Color(0xFF1565C0)
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = Color(0xFF202244),
                    disabledBorderColor = Color(0xFFE0E0E0),
                    disabledLeadingIconColor = Color(0xFF1565C0)
                ),
                shape = RoundedCornerShape(8.dp)
            )
        }
    }
}

