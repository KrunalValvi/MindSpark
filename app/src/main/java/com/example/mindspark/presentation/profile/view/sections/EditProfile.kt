package com.example.mindspark.profile.ui.sections

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mindspark.R
import com.example.mindspark.profile.components.ProfileSection
import com.example.mindspark.profile.model.ProfileField
import com.example.mindspark.ui.theme.LightBlueBackground
import com.example.mindspark.ui.theme.customTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(navController: NavController) {
    var fullName by remember { mutableStateOf("John Doe") }
    var nickname by remember { mutableStateOf("Johnny") }
    var dateOfBirth by remember { mutableStateOf("1995-01-01") }
    var email by remember { mutableStateOf("john.doe@example.com") }
    var phoneNumber by remember { mutableStateOf("+1 234 567 8900") }
    var gender by remember { mutableStateOf("Male") }
    var isEditing by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Edit Profile",
                        style = MaterialTheme.customTypography.jost.semiBold,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    TextButton(
                        onClick = { isEditing = !isEditing }
                    ) {
                        Text(
                            text = if (isEditing) "Save" else "Edit",
                            color = Color(0xFF1565C0),
                            style = MaterialTheme.customTypography.mulish.semiBold
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color(0xFF202244)
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LightBlueBackground)
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            // Profile Picture Section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(contentAlignment = Alignment.BottomEnd) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_profile_placeholder),
                            contentDescription = "Profile Picture",
                            modifier = Modifier
                                .size(120.dp)
                                .clip(CircleShape)
                                .border(2.dp, Color(0xFF1565C0), CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        if (isEditing) {
                            FloatingActionButton(
                                onClick = { /* Handle photo change */ },
                                modifier = Modifier
                                    .size(40.dp)
                                    .offset(x = (-8).dp, y = (-8).dp),
                                containerColor = Color(0xFF1565C0)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Camera,
                                    contentDescription = "Change Photo",
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = fullName,
                        style = MaterialTheme.customTypography.jost.bold,
                        fontSize = 24.sp,
                        color = Color(0xFF202244)
                    )

                    Text(
                        text = "Student",
                        style = MaterialTheme.customTypography.mulish.regular,
                        fontSize = 16.sp,
                        color = Color(0xFF6E7191)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Profile Fields
            ProfileSection(
                title = "Personal Information",
                fields = listOf(
                    ProfileField(
                        label = "Full Name",
                        value = fullName,
                        onValueChange = { fullName = it },
                        icon = Icons.Default.Person,
                        isEditing = isEditing
                    ),
                    ProfileField(
                        label = "Nickname",
                        value = nickname,
                        onValueChange = { nickname = it },
                        icon = Icons.Default.Person,
                        isEditing = isEditing
                    ),
                    ProfileField(
                        label = "Date of Birth",
                        value = dateOfBirth,
                        onValueChange = { dateOfBirth = it },
                        icon = Icons.Default.DateRange,
                        isEditing = isEditing
                    ),
                    ProfileField(
                        label = "Gender",
                        value = gender,
                        onValueChange = { gender = it },
                        icon = Icons.Default.Face,
                        isEditing = isEditing,
                        isDropdown = true,
                        dropdownOptions = listOf("Male", "Female", "Other")
                    )
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            ProfileSection(
                title = "Contact Information",
                fields = listOf(
                    ProfileField(
                        label = "Email",
                        value = email,
                        onValueChange = { email = it },
                        icon = Icons.Default.Email,
                        isEditing = isEditing
                    ),
                    ProfileField(
                        label = "Phone Number",
                        value = phoneNumber,
                        onValueChange = { phoneNumber = it },
                        icon = Icons.Default.Phone,
                        isEditing = isEditing
                    )
                )
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

