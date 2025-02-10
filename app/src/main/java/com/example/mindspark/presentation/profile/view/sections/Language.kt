package com.example.mindspark.profile.ui.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mindspark.auth.components.AuthTopBar
import com.example.mindspark.ui.theme.LightBlueBackground
import com.example.mindspark.ui.theme.customTypography

data class LanguageOption(
    val code: String,
    val name: String,
    val nativeName: String
)

@Composable
fun LanguageItem(
    language: LanguageOption,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick)
            .border(
                width = 1.dp,
                color = if (isSelected) Color(0xFFBDBDBD) else Color(0xFFE0E0E0),
                shape = RoundedCornerShape(12.dp)
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFFF5F9FF) else Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = language.name,
                    style = MaterialTheme.customTypography.jost.semiBold,
                    fontSize = 16.sp,
                    color = Color(0xFF202244)
                )
                Text(
                    text = language.nativeName,
                    style = MaterialTheme.customTypography.mulish.semiBold,
                    fontSize = 14.sp,
                    color = Color(0xFF6E7191)
                )
            }

            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Selected",
                    tint = Color(0xFF1565C0),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun LanguageScreen(navController: NavController) {
    var selectedLanguage by remember { mutableStateOf("en") }

    val languages = remember {
        listOf(
            LanguageOption("en", "English", "English"),
            LanguageOption("hi", "Hindi", "हिंदी"),
            LanguageOption("gu", "Gujarati", "ગુજરાતી"),
            LanguageOption("mr", "Marathi", "मराठी"),
            LanguageOption("bn", "Bengali", "বাংলা"),
            LanguageOption("ta", "Tamil", "தமிழ்"),
            LanguageOption("te", "Telugu", "తెలుగు"),
            LanguageOption("kn", "Kannada", "ಕನ್ನಡ"),
            LanguageOption("ml", "Malayalam", "മലയാളം")
        )
    }

    Scaffold(
        modifier = Modifier.background(LightBlueBackground),
        containerColor = LightBlueBackground,
        topBar = {
            AuthTopBar(
                title = "Language",
                onBackClick = { navController.navigateUp() }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LightBlueBackground)
                .padding(padding)
                .padding(16.dp)
        ) {
            // Language Icon and Description
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Language,
                            contentDescription = "Language",
                            tint = Color(0xFF1565C0),
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Select Your Preferred Language",
                        style = MaterialTheme.customTypography.jost.semiBold,
                        fontSize = 20.sp,
                        color = Color(0xFF202244),
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "You can change this at any time in settings",
                        style = MaterialTheme.customTypography.mulish.semiBold,
                        fontSize = 14.sp,
                        color = Color(0xFF6E7191),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            // Language List
            LazyColumn {
                items(languages) { language ->
                    LanguageItem(
                        language = language,
                        isSelected = selectedLanguage == language.code,
                        onClick = {
                            selectedLanguage = language.code
                            // TODO: Implement language change functionality
                        }
                    )
                }
            }

            // Apply Button
            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    // TODO: Save language preference and navigate back
                    navController.navigateUp()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1565C0)
                )
            ) {
                Text(
                    text = "Apply Changes",
                    style = MaterialTheme.customTypography.mulish.semiBold,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LanguageScreenPreview() {
    LanguageScreen(navController = NavController(LocalContext.current))
}