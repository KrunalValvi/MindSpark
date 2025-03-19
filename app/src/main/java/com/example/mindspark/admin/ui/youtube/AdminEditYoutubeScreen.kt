package com.example.mindspark.admin.ui.youtube

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mindspark.admin.model.AdminYoutubeModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminEditYoutubeScreen(
    navController: NavController,
    initialModel: AdminYoutubeModel,
    playlistViews: String,  // Extracted from YouTube API
    playlistVideos: Int,    // Extracted from YouTube API
    playlistDescription: String, // Extracted from YouTube API
    estimatedHours: Int,  // Estimated completion hours
    onSave: (AdminYoutubeModel) -> Unit
) {
    val categories = listOf("Development", "Design", "Marketing", "Finance", "Personal Development")
    val difficultyLevels = listOf("Beginner", "Intermediate", "Advanced")
    val languages = listOf("Hindi", "English", "Gujarati")
    val predefinedFeatures = listOf(
        "Comprehensive Tutorials",
        "Expert Instructors",
        "Lifetime Access",
        "Downloadable Resources",
        "Certificate of Completion",
        "Community Support",
        "Regular Updates",
        "Quizzes and Assignments"
    )

    var selectedCategory by remember { mutableStateOf(initialModel.category.ifBlank { categories[0] }) }
    var categoryExpanded by remember { mutableStateOf(false) }

    var title by remember { mutableStateOf(initialModel.title.split("[")[0].trim()) } // Remove extra text
    var price by remember { mutableStateOf(initialModel.price) }
    var rating by remember { mutableStateOf(initialModel.rating) }
    var views by remember { mutableStateOf(playlistViews) } // From YouTube API
    var videos by remember { mutableStateOf(playlistVideos.toString()) } // From YouTube API
    var hours by remember { mutableStateOf(estimatedHours.toString()) } // Auto-filled
    var selectedDifficulty by remember { mutableStateOf(initialModel.difficultyLevel.ifBlank { difficultyLevels[0] }) }
    var difficultyExpanded by remember { mutableStateOf(false) }
    var selectedLanguage by remember { mutableStateOf(initialModel.language.ifBlank { languages[0] }) }
    var languageExpanded by remember { mutableStateOf(false) }
    var certification by remember { mutableStateOf(false) }
    var description by remember { mutableStateOf(playlistDescription) } // **Now showing Playlist Description**
    var imageRes by remember { mutableStateOf(initialModel.imageRes) }
    var selectedFeatures by remember {
        mutableStateOf(
            initialModel.features.split(",").filter { it.isNotBlank() })
    }
    val context = LocalContext.current // Get context for showing Toast


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // **Category Dropdown (Now Works)**
        DropdownMenuField(
            label = "Category",
            selectedValue = selectedCategory,
            options = categories
        ) { selectedCategory = it }

        // **Title**
        OutlinedTextField(
            value = title,
            onValueChange = { title = it.split("[")[0].trim() },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        // **Price (Only numbers, default "Free" if empty)**
        NumericTextField("Price", price) { price = it }

        // **Rating (Only numbers)**
        NumericTextField("Rating", rating) { rating = it }

        // **Views (Read-Only)**
//        OutlinedTextField(value = views, onValueChange = {}, label = { Text("Views") }, modifier = Modifier.fillMaxWidth(), readOnly = true)

        // **Videos Count (Read-Only)**
        OutlinedTextField(
            value = videos,
            onValueChange = {},
            label = { Text("Total Videos") },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true
        )

        // **Hours (Auto-filled, but editable)**
        NumericTextField("Hours to Complete", hours) { hours = it }

        // **Difficulty Level Dropdown (Now Works)**
        DropdownMenuField(
            label = "Difficulty Level",
            selectedValue = selectedDifficulty,
            options = difficultyLevels
        ) { selectedDifficulty = it }

        // **Language Dropdown (Now Works)**
        DropdownMenuField(
            label = "Language",
            selectedValue = selectedLanguage,
            options = languages
        ) { selectedLanguage = it }

        // **Certification Toggle**
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Certification")
            Switch(checked = certification, onCheckedChange = { certification = it })
        }

        // **Description (Now Shows Playlist Description)**
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )

        // **Features (Checkbox Selection)**
        Text("Features")
        predefinedFeatures.forEach { feature ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(feature)
                Checkbox(
                    checked = selectedFeatures.contains(feature),
                    onCheckedChange = { isChecked ->
                        selectedFeatures =
                            if (isChecked) selectedFeatures + feature else selectedFeatures - feature
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val updatedModel = AdminYoutubeModel(
                    category = selectedCategory,
                    title = title,
                    price = if (price.isBlank()) "Free" else price,
                    rating = rating,
                    students = views,
                    videos = videos,
                    hours = hours,
                    difficultyLevel = selectedDifficulty,
                    language = selectedLanguage,
                    certification = certification.toString(),
                    about = description,
                    mentorIds = "",
                    imageRes = imageRes,
                    features = selectedFeatures.joinToString(", ")
                )
                onSave(updatedModel)

                // Show success message
                Toast.makeText(context, "Course saved successfully!", Toast.LENGTH_SHORT).show()

                // Navigate to AdminScreen and clear backstack to prevent double navigation
                navController.navigate("AdminScreen") {
                    popUpTo("AdminScreen") { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save")
        }
    }
}

@Composable
fun DropdownMenuField(
    label: String,
    selectedValue: String,
    options: List<String>,
    onValueChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Column {
        Text(label)
        Box {
            OutlinedTextField(
                value = selectedValue,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Filled.ArrowDropDown, contentDescription = "Dropdown Arrow")
                    }
                }
            )
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            onValueChange(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun NumericTextField(label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = { if (it.all { char -> char.isDigit() }) onValueChange(it) },
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
    )
}
