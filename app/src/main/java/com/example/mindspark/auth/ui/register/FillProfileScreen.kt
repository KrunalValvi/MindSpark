package com.example.mindspark.auth.ui.register

import android.net.Uri
import android.util.Base64
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.mindspark.R
import com.example.mindspark.auth.components.AuthButton
import com.example.mindspark.auth.components.AuthTextField
import com.example.mindspark.auth.components.AuthTopBar
import com.example.mindspark.auth.components.GenderDropdown
import com.example.mindspark.backend.getFirebaseProfileData
import com.example.mindspark.backend.showDatePicker
import com.example.mindspark.backend.storeProfileData
import com.example.mindspark.backend.validateProfile
import com.example.mindspark.profile.ui.sections.decodeBase64ToBitmap
import com.example.mindspark.ui.theme.customTypography
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun FillProfileScreen(navController: NavController) {
    val context = LocalContext.current

    // Retrieve initial profile data from Firebase.
    var profileData by remember { mutableStateOf(getFirebaseProfileData()) }
    var showDatePickerState by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    // Profile image handling states
    var profileImageUrl by remember { mutableStateOf("") }
    var showAvatarDialog by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var profileImageBase64 by remember { mutableStateOf("") }

    // Add profession error state
    var professionError by remember { mutableStateOf("") }

    // Launcher for image picking from the device.
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            selectedImageUri = it
            // Clear any previously stored Base64 string if a new image is selected.
            profileImageBase64 = ""
        }
    }

    // Launch DatePicker when requested.
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
            // Profile image display box.
            Box(contentAlignment = Alignment.BottomEnd) {
                // If a new image was selected, load it using its URI.
                // Otherwise, if there is an existing Base64 string, decode and display it.
                val painter = when {
                    selectedImageUri != null -> {
                        rememberAsyncImagePainter(selectedImageUri)
                    }
                    profileImageBase64.isNotEmpty() -> {
                        val bitmap = decodeBase64ToBitmap(profileImageBase64)
                        if (bitmap != null)
                            BitmapPainter(bitmap.asImageBitmap())
                        else painterResource(id = R.drawable.ic_profile_placeholder)
                    }
                    else -> painterResource(id = R.drawable.ic_profile_placeholder)
                }
                Image(
                    painter = painter,
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(Color.Gray),
                    contentScale = ContentScale.Crop
                )
                IconButton(
                    onClick = {
                        // Launch image picker when icon clicked.
                        imagePickerLauncher.launch("image/*")
                    },
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

            // Account Type Dropdown Field (User/Mentor)
            AccountTypeDropdown(
                selectedAccountType = profileData.accountType,
                onAccountTypeSelected = { profileData = profileData.copy(accountType = it, profession = "") }
            )
            if (profileData.accountTypeError.isNotEmpty()) {
                Text(
                    text = profileData.accountTypeError,
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            // Profession Dropdown - Only visible if accountType is "Mentor"
            if (profileData.accountType == "Mentor") {
                ProfessionDropdown(
                    selectedProfession = profileData.profession ?: "",
                    onProfessionSelected = { profileData = profileData.copy(profession = it) }
                )
                if (professionError.isNotEmpty()) {
                    Text(
                        text = professionError,
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            // Continue Button: validates, stores data, then navigates.
            AuthButton(
                text = "Continue",
                onClick = {
                    isLoading = true
                    val validatedProfile = validateProfile(profileData)
                    profileData = validatedProfile

                    // Additional validation for profession when account type is Mentor
                    if (profileData.accountType == "Mentor" && (profileData.profession.isNullOrEmpty())) {
                        professionError = "Please select your profession"
                        isLoading = false
                        return@AuthButton
                    } else {
                        professionError = ""
                    }

                    if (validatedProfile.fullNameError.isEmpty() &&
                        validatedProfile.dobError.isEmpty() &&
                        validatedProfile.emailError.isEmpty() &&
                        validatedProfile.phoneError.isEmpty() &&
                        validatedProfile.genderError.isEmpty() &&
                        validatedProfile.accountTypeError.isEmpty() &&
                        professionError.isEmpty()
                    ) {
                        coroutineScope.launch {
                            // If a new image is selected, convert it to a Base64 string.
                            if (selectedImageUri != null) {
                                val base64 = withContext(Dispatchers.IO) {
                                    try {
                                        context.contentResolver.openInputStream(selectedImageUri!!)?.readBytes()
                                            ?.let { bytes ->
                                                Base64.encodeToString(bytes, Base64.DEFAULT)
                                            } ?: ""
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                        ""
                                    }
                                }
                                profileImageBase64 = base64
                                // Clear the selected image URI after conversion.
                                selectedImageUri = null
                            }

                            // Store profile data with the profile image Base64 string
                            storeProfileData(
                                validatedProfile.copy(profileImageUrl = profileImageBase64),
                                onSuccess = {
                                    isLoading = false
                                    navController.navigate("CreatePinScreen")
                                },
                                onFailure = { errorMessage ->
                                    isLoading = false
                                    Toast.makeText(context, "Failed to save profile: $errorMessage", Toast.LENGTH_SHORT).show()
                                }
                            )
                        }
                    } else {
                        isLoading = false
                    }
                }
            )
        }
    }

    // Display a loading overlay while updating.
    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f)),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.material3.CircularProgressIndicator(color = Color.White)
        }
    }
}

@Composable
fun ProfessionDropdown(
    selectedProfession: String,
    onProfessionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val professions = listOf(
        "Mathematics",
        "Physics",
        "Chemistry",
        "Biology",
        "Computer Science",
        "History",
        "Literature",
        "Art",
        "Economics",
        "Psychology"
    )
    val rotationState by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        label = "dropdown_rotation"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(15.dp),
                    spotColor = Color(0x1A000000),
                    ambientColor = Color(0x1A000000)
                )
                .background(Color.White, RoundedCornerShape(15.dp))
                .border(
                    width = 1.dp,
                    color = if (expanded) Color(0xFF1565C0) else Color.Transparent,
                    shape = RoundedCornerShape(15.dp)
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,  // You might want to use a more specific icon here
                        contentDescription = "Profession Icon",
                        tint = Color.Gray,
                        modifier = Modifier.padding(end = 8.dp)
                    )

                    Text(
                        text = if (selectedProfession.isEmpty()) "Select Profession" else selectedProfession,
                        style = MaterialTheme.customTypography.mulish.bold,
                        fontSize = 14.sp,
                        color = if (selectedProfession.isEmpty()) Color.Gray else Color(0xFF1A1A1A)
                    )
                }

                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Dropdown Arrow",
                    tint = Color(0xFF1565C0),
                    modifier = Modifier.rotate(rotationState)
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) {
                    (LocalConfiguration.current.screenWidthDp - 48).dp
                })
                .background(Color.White, RoundedCornerShape(12.dp))
                .shadow(4.dp, RoundedCornerShape(12.dp))
        ) {
            professions.forEach { profession ->
                DropdownMenuItem(
                    onClick = {
                        onProfessionSelected(profession)
                        expanded = false
                    },
                    text = {
                        Text(
                            text = profession,
                            style = MaterialTheme.customTypography.mulish.bold,
                            fontSize = 14.sp,
                            color = if (profession == selectedProfession) Color(0xFF1565C0) else Color.Black
                        )
                    },
                    leadingIcon = if (profession == selectedProfession) {
                        {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Selected",
                                tint = Color(0xFF1565C0),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    } else null,
                    modifier = Modifier.background(
                        if (profession == selectedProfession) Color(0xFFF5F9FF) else Color.White
                    )
                )
            }
        }
    }
}

@Composable
fun AccountTypeDropdown(
    selectedAccountType: String,
    onAccountTypeSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val accountTypes = listOf("Student", "Mentor")
    val rotationState by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        label = "dropdown_rotation"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(15.dp),
                    spotColor = Color(0x1A000000),
                    ambientColor = Color(0x1A000000)
                )
                .background(Color.White, RoundedCornerShape(15.dp))
                .border(
                    width = 1.dp,
                    color = if (expanded) Color(0xFF1565C0) else Color.Transparent,
                    shape = RoundedCornerShape(15.dp)
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Account Type Icon",
                        tint = Color.Gray,
                        modifier = Modifier.padding(end = 8.dp)
                    )

                    Text(
                        text = if (selectedAccountType.isEmpty()) "Account Type" else selectedAccountType,
                        style = MaterialTheme.customTypography.mulish.bold, // Replace with your style if needed.
                        fontSize = 14.sp,
                        color = if (selectedAccountType.isEmpty()) Color.Gray else Color(0xFF1A1A1A)
                    )
                }

                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Dropdown Arrow",
                    tint = Color(0xFF1565C0),
                    modifier = Modifier.rotate(rotationState)
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) {
                    (LocalConfiguration.current.screenWidthDp - 48).dp
                })
                .background(Color.White, RoundedCornerShape(12.dp))
                .shadow(4.dp, RoundedCornerShape(12.dp))
        ) {
            accountTypes.forEach { type ->
                DropdownMenuItem(
                    onClick = {
                        onAccountTypeSelected(type)
                        expanded = false
                    },
                    text = {
                        Text(
                            text = type,
                            style = MaterialTheme.customTypography.mulish.bold, // Replace with your style.
                            fontSize = 14.sp,
                            color = if (type == selectedAccountType) Color(0xFF1565C0) else Color.Black
                        )
                    },
                    leadingIcon = if (type == selectedAccountType) {
                        {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Selected",
                                tint = Color(0xFF1565C0),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    } else null,
                    modifier = Modifier.background(
                        if (type == selectedAccountType) Color(0xFFF5F9FF) else Color.White
                    )
                )
            }
        }
    }
}