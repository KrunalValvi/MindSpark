package com.example.mindspark.profile.ui.sections

import android.widget.Toast
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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

@Composable
fun EditProfileScreen(navController: NavController) {
    val context = LocalContext.current

    var fullName by remember { mutableStateOf("") }
    var nickname by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var accountType by remember { mutableStateOf("") }
    var profileImageUrl by remember { mutableStateOf("") }
    var showDatePickerState by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var showAvatarDialog by remember { mutableStateOf(false) }
    val currentUser = Firebase.auth.currentUser

    //Normal
//    val avatarUrls = List(30) { "https://api.dicebear.com/9.x/personas/png?seed=Avatar$it&size=256" }

    //Professional
    val avatarUrls = List(200) { index ->
        val baseUrl = "https://api.dicebear.com/9.x/avataaars/png"
        val seed = if (index % 2 == 0) "ProfessionalMale$index" else "ProfessionalFemale$index"
        val size = 256
        // Allowed skin colors (hex codes), chosen to exclude any black skin tone.
        val skinColors = "F9C9B6,F1C27D,E0AC69,C68642,8D5524"
        // Limit expressions to simple, friendly ones.
        val mouth = "smile,default"   // Only simple smiling or neutral expressions.
        // Set eyes to only include open-eye options.
        val eyes = "default"    // Both options display open eyes.
        // Set eyebrows to a friendly style (here "happy").
        val eyebrow = "happy"
        // Restrict hair colors to only cool choices: black, brown, and blond (hex codes).
        val hairColor = "000000,A55728,F4E1C1"

        val params = listOf(
            "seed=$seed",
            "size=$size",
            "skinColor=$skinColors",
            "mouth=$mouth",
            "eyes=$eyes",
            "eyebrow=$eyebrow",
            "hairColor=$hairColor"
        ).joinToString("&")

        "$baseUrl?$params"
    }



    //try done
//    val avatarUrls = List(200) { index ->
//        if (index % 2 == 0)
//            "https://avatar.iran.liara.run/public/boy?unique=$index"
//        else
//            "https://avatar.iran.liara.run/public/girl?unique=$index"
//    }


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
                        profileImageUrl = document.getString("profileImageUrl") ?: ""
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Failed to load profile", Toast.LENGTH_SHORT).show()
                }
        }
    }

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
            Box(contentAlignment = Alignment.BottomEnd) {
                Image(
                    painter = rememberAsyncImagePainter(profileImageUrl.ifEmpty { R.drawable.ic_profile_placeholder }),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(Color.Gray),
                    contentScale = ContentScale.Crop
                )
                IconButton(
                    onClick = { showAvatarDialog = true },
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
            AuthTextField(value = fullName, onValueChange = { fullName = it }, placeholder = "Full Name", leadingIcon = { Icon(imageVector = androidx.compose.material.icons.Icons.Default.Person, contentDescription = "Full Name Icon") })
            AuthTextField(value = nickname, onValueChange = { nickname = it }, placeholder = "Nickname", leadingIcon = { Icon(imageVector = androidx.compose.material.icons.Icons.Default.Person, contentDescription = "Nickname Icon") })
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
            AuthTextField(value = email, onValueChange = {}, placeholder = "Email", leadingIcon = { Icon(imageVector = androidx.compose.material.icons.Icons.Default.Email, contentDescription = "Email Icon") })
            AuthTextField(value = phoneNumber, onValueChange = { phoneNumber = it }, placeholder = "Phone Number", leadingIcon = { Icon(imageVector = androidx.compose.material.icons.Icons.Default.Phone, contentDescription = "Phone Number Icon") })
            GenderDropdown(selectedGender = gender, onGenderSelected = { gender = it })
            Spacer(modifier = Modifier.height(10.dp))
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
            Spacer(modifier = Modifier.height(10.dp))

            AuthButton(
                text = "Update",
                onClick = {
                    isLoading = true
                    updateUserProfileData(
                        ProfileData(fullName, email, phoneNumber, nickname, dateOfBirth, gender, accountType, profileImageUrl),
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

    if (showAvatarDialog) {
        AvatarSelectionDialog(
            avatars = avatarUrls,
            onAvatarSelected = { avatarUrl ->
                profileImageUrl = avatarUrl
                showAvatarDialog = false
            },
            onDismissRequest = { showAvatarDialog = false }
        )
    }
}

@Composable
fun AvatarSelectionDialog(
    avatars: List<String>,
    onAvatarSelected: (String) -> Unit,
    onDismissRequest: () -> Unit
) {
    // Create a mutable list to track if each image has loaded.
    // Initially, all images are marked as not loaded.
    val loadedStates = remember { mutableStateListOf<Boolean>().apply { repeat(avatars.size) { add(false) } } }
    // Calculate how many images are loaded and how many remain.
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

                // Show loading status if not all images are loaded
                if (remainingCount > 0) {
                    Text(
                        text = "Loading: $remainingCount images left",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3), // 3 avatars per row
                    modifier = Modifier.height(300.dp), // fixed height to enable scrolling
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    itemsIndexed(avatars) { index, avatarUrl ->
                        // Use Coil's async image painter
                        val painter = rememberAsyncImagePainter(model = avatarUrl)
                        val painterState = painter.state

                        // When the image is successfully loaded, mark it as loaded.
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
                            // If still loading, overlay a CircularProgressIndicator.
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
