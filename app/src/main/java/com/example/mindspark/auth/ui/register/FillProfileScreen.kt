package com.example.mindspark.auth.ui.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mindspark.R
import com.example.mindspark.backend.getFirebaseProfileData
import com.example.mindspark.backend.showDatePicker
import com.example.mindspark.backend.validateProfile
import com.example.mindspark.backend.storeProfileData
import com.example.mindspark.auth.components.AuthButton
import com.example.mindspark.auth.components.AuthTextField
import com.example.mindspark.auth.components.AuthTopBar
import com.example.mindspark.auth.components.GenderDropdown
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

@Composable
fun FillProfileScreen(navController: NavController) {
    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()

    // Retrieve initial profile data from Firebase.
    var profileData by remember { mutableStateOf(getFirebaseProfileData()) }

    // State to control showing the DatePicker.
    var showDatePickerState by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    // Launch DatePicker when showDatePickerState is true.
    LaunchedEffect(showDatePickerState) {
        if (showDatePickerState) {
            showDatePicker(context) { selectedDate ->
                profileData = profileData.copy(dateOfBirth = selectedDate, dobError = "")
                showDatePickerState = false
            }
        }
    }

    Scaffold(
        modifier = Modifier.background(Color(0xFFF5F9FF)),
        containerColor = Color(0xFFF5F9FF),
        topBar = {
            AuthTopBar(
                title = "Fill Your Profile",
                onBackClick = { navController.navigateUp() }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F9FF))
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(contentAlignment = Alignment.BottomEnd) {
                Image(
                    painter = painterResource(id = R.drawable.ic_profile_placeholder),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .height(110.dp)
                        .width(110.dp)
                        .clip(CircleShape)
                        .background(Color.Gray),
                    contentScale = ContentScale.Crop
                )
                IconButton(
                    onClick = { /* Open image picker if needed */ },
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_edit_photo),
                        contentDescription = "Edit Profile",
                        tint = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(5.dp))

            // Full Name Field
            AuthTextField(
                value = profileData.fullName,
                onValueChange = { profileData = profileData.copy(fullName = it) },
                placeholder = "Full Name",
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Full Name Icon"
                    )
                }
            )
            if (profileData.fullNameError.isNotEmpty()) {
                Text(
                    text = profileData.fullNameError,
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            // Nickname Field (Optional)
            AuthTextField(
                value = profileData.nickname,
                onValueChange = { profileData = profileData.copy(nickname = it) },
                placeholder = "Nickname",
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Nickname Icon"
                    )
                }
            )

            // Date of Birth Field (read-only; launches DatePicker on tap)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showDatePickerState = true }
            ) {
                AuthTextField(
                    value = profileData.dateOfBirth,
                    onValueChange = { /* Prevent manual input */ },
                    placeholder = "Date of Birth",
                    leadingIcon = {
                        Icon(
                            modifier = Modifier.clickable { showDatePickerState = true },
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Date of Birth Icon"
                        )
                    }
                )
            }
            if (profileData.dobError.isNotEmpty()) {
                Text(
                    text = profileData.dobError,
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            // Email Field (Pre-populated)
            AuthTextField(
                value = profileData.email,
                onValueChange = { profileData = profileData.copy(email = it) },
                placeholder = "Email",
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Email Icon"
                    )
                }
            )
            if (profileData.emailError.isNotEmpty()) {
                Text(
                    text = profileData.emailError,
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            // Phone Number Field (digits only; maximum 10 digits)
            AuthTextField(
                value = profileData.phoneNumber,
                onValueChange = { input ->
                    if (input.length <= 10 && input.all { it.isDigit() }) {
                        profileData = profileData.copy(phoneNumber = input)
                    }
                },
                placeholder = "Phone Number",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = "Phone Number Icon"
                    )
                }
            )
            if (profileData.phoneError.isNotEmpty()) {
                Text(
                    text = profileData.phoneError,
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            // Gender Dropdown Field
            GenderDropdown(
                selectedGender = profileData.gender,
                onGenderSelected = { profileData = profileData.copy(gender = it) }
            )
            if (profileData.genderError.isNotEmpty()) {
                Text(
                    text = profileData.genderError,
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            // Continue Button: validates, stores data, shows Toast, then navigates.
            AuthButton(
                text = "Continue",
                onClick = {
                    val validatedProfile = validateProfile(profileData)
                    profileData = validatedProfile
                    if (validatedProfile.fullNameError.isEmpty() &&
                        validatedProfile.dobError.isEmpty() &&
                        validatedProfile.emailError.isEmpty() &&
                        validatedProfile.phoneError.isEmpty() &&
                        validatedProfile.genderError.isEmpty()
                    ) {
                        storeProfileData(
                            validatedProfile,
                            onSuccess = {
                                coroutineScope.launch {
                                    navController.navigate("CreatePinScreen")
                                }
                            },
                            onFailure = { errorMessage ->
                            }
                        )
                    }
                }
            )
        }
    }
}
