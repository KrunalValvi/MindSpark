package com.example.mindspark.courses.components

import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindspark.ui.theme.customTypography
import com.example.mindspark.courses.model.MentorModel

@Composable
fun TopMentorCardHorizontal(mentor: MentorModel, onClick: (MentorModel) -> Unit) {
    // State to hold the profile image
    var profileImageUrl by remember { mutableStateOf(mentor.profileImageUrl) }
    // Default fallback image resource
    val defaultImageRes = mentor.imageRes

    Column(
        modifier = Modifier
            .padding(top = 8.dp)
            .width(80.dp)
            .clickable { onClick(mentor) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            // Check if we have a Firebase profile image URL
            if (profileImageUrl.isNotEmpty()) {
                // Use the decodeBase64ToBitmap function from CommunityScreen.kt
                val bitmap = decodeBase64ToBitmap(profileImageUrl)
                if (bitmap != null) {
                    Image(
                        painter = BitmapPainter(bitmap.asImageBitmap()),
                        contentDescription = "Profile picture of ${mentor.name}",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    // Fall back to resource image if decoding fails
                    Image(
                        painter = painterResource(id = defaultImageRes),
                        contentDescription = "Profile picture of ${mentor.name}",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
            } else {
                // Use the resource image if no URL is available
                Image(
                    painter = painterResource(id = defaultImageRes),
                    contentDescription = "Profile picture of ${mentor.name}",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = mentor.name,
            color = Color.Black,
            style = MaterialTheme.customTypography.jost.semiBold,
            fontSize = 14.sp, // Slightly increased font size
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            maxLines = 2, // Allow two lines for longer names
            overflow = TextOverflow.Ellipsis, // Add ellipsis for very long names
            modifier = Modifier.fillMaxWidth()
        )
    }
}

// Helper function to decode base64 image (improved error handling)
fun decodeBase64ToBitmap(base64Str: String): android.graphics.Bitmap? {
    return try {
        // Add debugging
        Log.d("ProfileImage", "Attempting to decode image: ${base64Str.take(50)}...")

        // Remove any data URI prefix if present.
        val pureBase64 = if (base64Str.contains(",")) {
            Log.d("ProfileImage", "Found comma in base64 string, extracting content after comma")
            base64Str.substringAfter(",")
        } else {
            base64Str
        }

        val decodedBytes = Base64.decode(pureBase64, Base64.DEFAULT)
        Log.d("ProfileImage", "Decoded ${decodedBytes.size} bytes")

        BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    } catch (e: Exception) {
        Log.e("ProfileImage", "Failed to decode image", e)
        null
    }
}

@Composable
fun TopMentorCardVertical(mentor: MentorModel, onClick: (MentorModel) -> Unit) {
    // Log mentor data for debugging
    Log.d("TopMentorCardVertical", "Rendering mentor: ${mentor.name}, ProfileImageUrl: ${mentor.profileImageUrl}")

    // State to hold the profile image
    var profileImageUrl by remember { mutableStateOf(mentor.profileImageUrl) }
    // Default fallback image resource
    val defaultImageRes = mentor.imageRes

    // State to track if the image is loading or has errored
    var isImageLoading by remember { mutableStateOf(true) }
    var hasImageError by remember { mutableStateOf(false) }

    // Determine if the profileImageUrl is a Firebase Storage URL or a Base64 string
    val isFirebaseStorageUrl = profileImageUrl.startsWith("https://firebasestorage.googleapis.com")

    // If it's a Firebase Storage URL, we'll use Coil to load it
    // If it's a Base64 string, we'll use our existing decoding function

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .clickable { onClick(mentor) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            if (profileImageUrl.isNotEmpty()) {
                // Log attempt to load image
                Log.d("TopMentorCardVertical", "Attempting to load image for ${mentor.name}")

                if (isFirebaseStorageUrl) {
                    // For Firebase Storage URLs, we should use Coil
                    // However, since we don't have Coil in the project yet,
                    // we'll need to add it to the build.gradle file:
                    // implementation "io.coil-kt:coil-compose:2.4.0"

                    // Instead, let's just log this for now and use the default image
                    Log.d("TopMentorCardVertical", "Firebase Storage URL detected: $profileImageUrl")
                    Log.d("TopMentorCardVertical", "To properly load this image, add Coil to your project")

                    // Using default image as fallback until Coil is added
                    Image(
                        painter = painterResource(id = defaultImageRes),
                        contentDescription = "Profile picture of ${mentor.name}",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    // Assume it's a Base64 string
                    // Use the decodeBase64ToBitmap function with improved debugging
                    val bitmap = try {
                        val decodedBitmap = decodeBase64ToBitmap(profileImageUrl)
                        if (decodedBitmap == null) {
                            Log.e("TopMentorCardVertical", "Failed to decode bitmap for ${mentor.name}")
                            hasImageError = true
                        }
                        decodedBitmap
                    } catch (e: Exception) {
                        Log.e("TopMentorCardVertical", "Exception decoding bitmap: ${e.message}", e)
                        hasImageError = true
                        null
                    }

                    if (bitmap != null) {
                        Log.d("TopMentorCardVertical", "Successfully decoded bitmap for ${mentor.name}")
                        Image(
                            painter = BitmapPainter(bitmap.asImageBitmap()),
                            contentDescription = "Profile picture of ${mentor.name}",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        // Fall back to resource image if decoding fails
                        Log.d("TopMentorCardVertical", "Using default image for ${mentor.name}")
                        Image(
                            painter = painterResource(id = defaultImageRes),
                            contentDescription = "Profile picture of ${mentor.name}",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            } else {
                // Use the resource image if no URL is available
                Log.d("TopMentorCardVertical", "No profile image URL for ${mentor.name}, using default image")
                Image(
                    painter = painterResource(id = defaultImageRes),
                    contentDescription = "Profile picture of ${mentor.name}",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column {
            Text(
                text = mentor.name,
                color = Color.Black,
                style = MaterialTheme.customTypography.jost.semiBold,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = mentor.profession,
                color = Color.Black,
                style = MaterialTheme.customTypography.mulish.bold,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

