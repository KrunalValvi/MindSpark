package com.example.mindspark.profile.ui.sections

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.mindspark.R
import com.example.mindspark.auth.components.AuthButton
import com.example.mindspark.auth.components.AuthTextField
import com.example.mindspark.auth.components.AuthTopBar
import com.example.mindspark.auth.components.GenderDropdown
import com.example.mindspark.backend.ProfileData
import com.example.mindspark.backend.showDatePicker
import com.example.mindspark.backend.updateUserProfileData
import com.example.mindspark.ui.theme.LightBlueBackground
import com.example.mindspark.ui.theme.customTypography
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun EditProfileScreen(navController: NavController) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Profile fields
    var fullName by remember { mutableStateOf("") }
    var nickname by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var accountType by remember { mutableStateOf("") }
    // This variable will hold the Base64 string for the profile image.
    var profileImageBase64 by remember { mutableStateOf("") }
    var showDatePickerState by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var showAvatarDialog by remember { mutableStateOf(false) }
    val currentUser = Firebase.auth.currentUser

    // New state to hold the selected image URI from the phone.
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

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

    // Load existing profile data from Firestore.
    LaunchedEffect(currentUser) {
        currentUser?.let { user ->
            val db = FirebaseFirestore.getInstance()
            db.collection("users").document(user.uid)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        fullName = document.getString("fullName") ?: ""
                        nickname = document.getString("nickname") ?: ""
                        dateOfBirth = document.getString("dateOfBirth") ?: ""
                        email = document.getString("email") ?: ""
                        phoneNumber = document.getString("phoneNumber") ?: ""
                        gender = document.getString("gender") ?: ""
                        accountType = document.getString("accountType") ?: ""
                        // Existing Base64 string (if any)
                        profileImageBase64 = document.getString("profileImageUrl") ?: ""
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Failed to load profile", Toast.LENGTH_SHORT).show()
                }
        }
    }

    // Show date picker when needed.
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

            Spacer(modifier = Modifier.height(10.dp))
            AuthTextField(
                value = fullName,
                onValueChange = { fullName = it },
                placeholder = "Full Name",
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Person, contentDescription = "Full Name Icon")
                }
            )
            AuthTextField(
                value = nickname,
                onValueChange = { nickname = it },
                placeholder = "Nickname",
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Person, contentDescription = "Nickname Icon")
                }
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showDatePickerState = true }
            ) {
                AuthTextField(
                    value = dateOfBirth,
                    onValueChange = { /* no-op; use date picker */ },
                    placeholder = "Date of Birth",
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.DateRange, contentDescription = "Date of Birth Icon")
                    }
                )
            }
            AuthTextField(
                value = email,
                onValueChange = {},
                placeholder = "Email",
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Email, contentDescription = "Email Icon")
                }
            )
            AuthTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                placeholder = "Phone Number",
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Phone, contentDescription = "Phone Number Icon")
                }
            )
            GenderDropdown(selectedGender = gender, onGenderSelected = { gender = it })
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 8.dp, end = 8.dp, bottom = 4.dp)
                    .background(Color.White, shape = RoundedCornerShape(15.dp))
                    .padding(16.dp)
            ) {
                Text(
                    text = accountType, // For example "Student" or "Mentor"
                    style = MaterialTheme.customTypography.mulish.bold,
                    color = Color.Gray
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            AuthButton(
                text = "Update",
                onClick = {
                    isLoading = true
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
                        // Call your update function with the updated profile data.
                        updateUserProfileData(
                            ProfileData(
                                fullName = fullName,
                                email = email,
                                phoneNumber = phoneNumber,
                                nickname = nickname,
                                dateOfBirth = dateOfBirth,
                                gender = gender,
                                accountType = accountType,
                                profileImageUrl = profileImageBase64
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
            CircularProgressIndicator(color = Color.White)
        }
    }

    // Show the avatar selection dialog if enabled.
//    if (showAvatarDialog) {
//        AvatarSelectionDialog(
//            avatars = avatarUrls,
//            onAvatarSelected = { avatarUrl ->
//                // When an avatar is selected from the dialog, update the Base64 string.
//                // (If your avatars are provided as URLs, you may need to fetch and encode them.)
//                profileImageBase64 = avatarUrl
//                showAvatarDialog = false
//            },
//            onDismissRequest = { showAvatarDialog = false }
//        )
//    }
}

// Helper to decode a Base64 string into a Bitmap.
fun decodeBase64ToBitmap(base64Str: String): Bitmap? {
    return try {
        val decodedBytes = Base64.decode(base64Str, Base64.DEFAULT)
        BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

@Composable
fun AvatarSelectionDialog(
    avatars: List<String>,
    onAvatarSelected: (String) -> Unit,
    onDismissRequest: () -> Unit
) {
    // Track load states for each avatar.
    val loadedStates = remember { mutableStateListOf<Boolean>().apply { repeat(avatars.size) { add(false) } } }
    val loadedCount = loadedStates.count { it }
    val remainingCount = avatars.size - loadedCount

    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = Color.White,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Select an Avatar",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                if (remainingCount > 0) {
                    Text(
                        text = "Loading: $remainingCount images left",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier.height(300.dp),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    itemsIndexed(avatars) { index, avatarUrl ->
                        val painter = rememberAsyncImagePainter(model = avatarUrl)
                        val painterState = painter.state
                        if (painterState is AsyncImagePainter.State.Success && !loadedStates[index]) {
                            loadedStates[index] = true
                        }
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .clickable { onAvatarSelected(avatarUrl) }
                        ) {
                            Image(
                                painter = painter,
                                contentDescription = "Avatar",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                            if (painterState is AsyncImagePainter.State.Loading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.align(Alignment.Center),
                                    strokeWidth = 2.dp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
