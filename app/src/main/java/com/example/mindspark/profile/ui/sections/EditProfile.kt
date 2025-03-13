package com.example.mindspark.profile.ui.sections

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mindspark.R
import com.example.mindspark.auth.components.AuthButton
import com.example.mindspark.auth.components.AuthTextField
import com.example.mindspark.auth.components.AuthTopBar
import com.example.mindspark.auth.components.GenderDropdown
import com.example.mindspark.backend.ProfileData
import com.example.mindspark.backend.fetchUserProfileDataFromFirestore
import com.example.mindspark.backend.showDatePicker
import com.example.mindspark.backend.updateUserProfileData
import com.example.mindspark.ui.theme.LightBlueBackground
import com.example.mindspark.ui.theme.customTypography

@Composable
fun EditProfileScreen(navController: NavController) {
    val context = LocalContext.current

    // Profile state variables
    var fullName by remember { mutableStateOf("") }
    var nickname by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var accountType by remember { mutableStateOf("") }  // Account type: "Student" or "Mentor"
    var isLoading by remember { mutableStateOf(false) }
    var showDatePickerState by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    // Fetch profile from Firestore when the screen loads.
    LaunchedEffect(Unit) {
        isLoading = true
        fetchUserProfileDataFromFirestore(
            onSuccess = { profile ->
                fullName = profile.fullName
                nickname = profile.nickname
                dateOfBirth = profile.dateOfBirth
                email = profile.email
                phoneNumber = profile.phoneNumber
                gender = profile.gender
                accountType = profile.accountType   // Set the account type from Firestore (e.g., "Student" or "Mentor")
                isLoading = false
            },
            onFailure = { error ->
                isLoading = false
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            }
        )
    }

    // Launch DatePickerDialog when requested.
    LaunchedEffect(showDatePickerState) {
        if (showDatePickerState) {
            showDatePicker(context) { selectedDate ->
                dateOfBirth = selectedDate
                showDatePickerState = false
            }
        }
    }

    Scaffold(
        modifier = Modifier.background(LightBlueBackground),
        containerColor = LightBlueBackground,
        topBar = {
            AuthTopBar(
                title = "Edit Profile",
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
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Picture Section
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
                    onClick = { /* Open image picker */ },
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
                value = fullName,
                onValueChange = { fullName = it },
                placeholder = "Full Name",
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Full Name Icon"
                    )
                }
            )

            // Nickname Field
            AuthTextField(
                value = nickname,
                onValueChange = { nickname = it },
                placeholder = "Nickname",
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Nickname Icon"
                    )
                }
            )

            // Date of Birth Field (clickable to open DatePicker)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showDatePickerState = true }
            ) {
                AuthTextField(
                    value = dateOfBirth,
                    onValueChange = { /* no-op, use date picker */ },
                    placeholder = "Date of Birth",
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Date of Birth Icon"
                        )
                    }
                )
            }

            // Email Field (read-only)
            AuthTextField(
                value = email,
                onValueChange = { /* no-op, email is not editable */ },
                placeholder = "Email",
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Email Icon"
                    )
                }
            )

            // Phone Number Field
            AuthTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                placeholder = "Phone Number",
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = "Phone Number Icon"
                    )
                }
            )

            // Gender Dropdown Field
            GenderDropdown(
                selectedGender = gender,
                onGenderSelected = { gender = it }
            )

            // Display Account Type (non-editable)
            // Instead of showing a labeled TextField, we simply display the account type ("Student" or "Mentor")
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 8.dp, end = 8.dp, bottom = 4.dp)
//                    .shadow(elevation = 4.dp, shape = RoundedCornerShape(15.dp))
                    .background(Color.White, RoundedCornerShape(15.dp))
                    .padding(16.dp)
            ) {
                Text(
                    text = accountType, // Shows "Student" or "Mentor"
                    style = MaterialTheme.customTypography.mulish.bold,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            // Update Button
            AuthButton(
                text = "Update",
                onClick = {
                    isLoading = true
                    updateUserProfileData(
                        profileData = ProfileData(
                            fullName = fullName,
                            email = email,
                            phoneNumber = phoneNumber,
                            nickname = nickname,
                            dateOfBirth = dateOfBirth,
                            gender = gender
                        ),
                        onSuccess = {
                            isLoading = false
                            Toast.makeText(context, "Profile updated", Toast.LENGTH_SHORT).show()
                        },
                        onFailure = { error ->
                            isLoading = false
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            )
        }
    }

    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color.White)
        }
    }
}
