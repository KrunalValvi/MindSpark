package com.example.mindspark.profile.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.mindspark.R
import com.example.mindspark.ui.theme.customTypography
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.ui.graphics.painter.BitmapPainter
import com.google.firebase.ktx.Firebase

@Composable
fun ProfileHeader() {
    // Firebase user
    val user = Firebase.auth.currentUser
    val currentUser = user

    // State variables for profile info
    var fullName by remember { mutableStateOf("Loading...") }
    // Here profileImageUrl is expected to be a Base64 string
    var profileImageUrl by remember { mutableStateOf("") }

    // Fetch user data from Firestore
    LaunchedEffect(currentUser) {
        if (currentUser != null) {
            val db = FirebaseFirestore.getInstance()
            db.collection("users").document(currentUser.uid)
                .get()
                .addOnSuccessListener { document ->
                    fullName = document.getString("fullName")
                        ?: currentUser.displayName ?: "User"
                    profileImageUrl = document.getString("profileImageUrl") ?: ""
                }
                .addOnFailureListener {
                    fullName = currentUser.displayName ?: "User"
                    profileImageUrl = ""
                }
        } else {
            fullName = "User"
            profileImageUrl = ""
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        Box(contentAlignment = Alignment.BottomEnd) {
            if (profileImageUrl.isNotEmpty()) {
                // Decode the Base64 string to Bitmap and display it
                val bitmap = decodeBase64ToBitmap(profileImageUrl)
                if (bitmap != null) {
                    Image(
                        painter = BitmapPainter(bitmap.asImageBitmap()),
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(110.dp)
                            .clip(CircleShape)
                            .background(Color.Gray),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    // Fallback to placeholder if decoding fails
                    Image(
                        painter = painterResource(id = R.drawable.ic_profile_placeholder),
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(110.dp)
                            .clip(CircleShape)
                            .background(Color.Gray),
                        contentScale = ContentScale.Crop
                    )
                }
            } else {
                // Show placeholder if no image is provided
                Image(
                    painter = painterResource(id = R.drawable.ic_profile_placeholder),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(110.dp)
                        .clip(CircleShape)
                        .background(Color.Gray),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = fullName,
            style = MaterialTheme.customTypography.jost.semiBold,
            fontSize = 24.sp
        )
        Text(
            text = user?.email ?: "Email",
            style = MaterialTheme.customTypography.mulish.bold,
            fontSize = 15.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun SettingsList(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(18.dp)
            .background(Color.White, shape = RoundedCornerShape(16.dp))
            .padding(vertical = 16.dp)
    ) {
        SettingItem(icon = R.drawable.ic_edit, title = "Edit Profile") { navController.navigate("EditProfileScreen")}
//        SettingItem(icon = R.drawable.ic_payment, title = "Payment Option") { navController.navigate("PaymentOptionScreen") }
        SettingItem(icon = R.drawable.ic_notifications, title = "Notifications") { navController.navigate("ProfileNotificationsScreen") }
//        SettingItem(icon = R.drawable.ic_security, title = "Security") { navController.navigate("SecurityScreen") }
//        SettingItem(icon = R.drawable.ic_language, title = "Language") { navController.navigate("LanguageScreen")}
//        SettingItem(icon = R.drawable.ic_dark_mode, title = "Dark Mode") { navController.navigate("AdminScreen") }
        SettingItem(icon = R.drawable.ic_terms, title = "Terms & Conditions") { navController.navigate("TermsScreen") }
//        SettingItem(icon = R.drawable.ic_help, title = "Help Center") { }
//        SettingItem(icon = R.drawable.ic_invite, title = "Invite Friends") { navController.navigate("InviteFriendsScreen")}
    }
}

@Composable
fun SettingItem(icon: Int, title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painter = painterResource(id = icon), contentDescription = title, tint = Color.Black)
        Spacer(modifier = Modifier.width(16.dp))
        Text(title, style = MaterialTheme.customTypography.mulish.bold, fontSize = 15.sp, modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_right),
            contentDescription = "Navigate",
            tint = Color.Black
        )
    }
}

fun decodeBase64ToBitmap(base64Str: String): Bitmap? {
    return try {
        // Remove any "data:image/..." prefix if present.
        val pureBase64 = if (base64Str.contains(",")) base64Str.substringAfter(",") else base64Str
        val decodedBytes = Base64.decode(pureBase64, Base64.DEFAULT)
        BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
