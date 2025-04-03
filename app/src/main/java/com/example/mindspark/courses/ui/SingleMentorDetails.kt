package com.example.mindspark.courses.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mindspark.R
import com.example.mindspark.auth.components.AuthTopBar
import com.example.mindspark.courses.components.ReviewItem
import com.example.mindspark.courses.model.MentorCourseModel
import com.example.mindspark.courses.model.MentorModel
import com.example.mindspark.courses.model.ReviewModel
import com.example.mindspark.profile.components.decodeBase64ToBitmap
import com.example.mindspark.ui.theme.LightBlueBackground
import com.example.mindspark.ui.theme.customTypography
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun SingleMentorDetails(navController: NavController, mentorId: String) {
    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()
    var mentor by remember { mutableStateOf<MentorModel?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var followCount by remember { mutableStateOf(0) }
    var isFollowing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    // Check if current user is following this mentor
    LaunchedEffect(mentorId, auth.currentUser?.uid) {
        if (auth.currentUser != null) {
            try {
                val currentUserId = auth.currentUser!!.uid
                val followDocument = db.collection("mentors")
                    .document(mentorId)
                    .collection("followers")
                    .document(currentUserId)
                    .get()
                    .await()

                isFollowing = followDocument.exists()
                Log.d("SingleMentorDetails", "Is current user following: $isFollowing")
            } catch (e: Exception) {
                Log.e("SingleMentorDetails", "Error checking follow status", e)
            }
        }
    }

    // Fetch mentor data from Firestore
    LaunchedEffect(mentorId) {
        try {
            Log.d("SingleMentorDetails", "Fetching mentor with ID: $mentorId")
            val mentorDoc = db.collection("mentors").document(mentorId).get().await()
            val userDoc = db.collection("users").document(mentorId).get().await()

            if (mentorDoc.exists() || userDoc.exists()) {
                // Get mentor data from mentors collection
                val mentorData = mentorDoc.data ?: emptyMap()
                // Get user data from users collection
                val userData = userDoc.data ?: emptyMap()

                // Combine data from both collections
                val name = userData["fullName"] as? String ?: mentorData["name"] as? String ?: "Unknown"
                val profession = userData["profession"] as? String ?: mentorData["profession"] as? String ?: "Mentor"
                val profileImageUrl = userData["profileImageUrl"] as? String ?: mentorData["profile_image"] as? String ?: ""
                val email = userData["email"] as? String ?: ""

                // Get followers count
                // Get the followers collection for this mentor to count follows
                db.collection("mentors").document(mentorId).collection("followers").get()
                    .addOnSuccessListener { followersSnapshot ->
                        followCount = followersSnapshot.documents.size
                        Log.d("SingleMentorDetails", "Follower count: $followCount")
                    }
                    .addOnFailureListener { e ->
                        Log.e("SingleMentorDetails", "Error getting followers", e)
                    }

                // Initialize empty lists for courses and reviews
                var coursesList = mutableListOf<MentorCourseModel>()
                var reviewsList = mutableListOf<ReviewModel>()
                
                // Fetch courses for this mentor
                try {
                    val coursesQuery = db.collection("mentors")
                        .document(mentorId)
                        .collection("courses")
                        .get()
                        .await()
                    
                    coursesList = coursesQuery.documents.mapNotNull { courseDoc ->
                        val courseData = courseDoc.data ?: return@mapNotNull null
                        MentorCourseModel(
                            id = (courseData["id"] as? Long)?.toInt() ?: 0,
                            title = courseData["title"] as? String ?: "Unknown Course",
                            level = courseData["level"] as? String ?: "Beginner",
                            price = courseData["price"] as? String ?: "Free",
                            rating = courseData["rating"] as? String ?: "0.0",
                            videos = courseData["videos"] as? String ?: "0",
                            hours = courseData["hours"] as? String ?: "0"
                        )
                    }.toMutableList()
                    
                    Log.d("SingleMentorDetails", "Fetched ${coursesList.size} courses")
                } catch (e: Exception) {
                    Log.e("SingleMentorDetails", "Error fetching courses", e)
                }
                
                // Fetch reviews for this mentor
                try {
                    val reviewsQuery = db.collection("mentors")
                        .document(mentorId)
                        .collection("reviews")
                        .get()
                        .await()
                    
                    reviewsList = reviewsQuery.documents.mapNotNull { reviewDoc ->
                        val reviewData = reviewDoc.data ?: return@mapNotNull null
                        ReviewModel(
                            reviewerName = reviewData["reviewerName"] as? String ?: "Anonymous",
                            reviewText = reviewData["reviewText"] as? String ?: "",
                            date = reviewData["date"] as? String ?: "Recently"
                        )
                    }.toMutableList()
                    
                    Log.d("SingleMentorDetails", "Fetched ${reviewsList.size} reviews")
                } catch (e: Exception) {
                    Log.e("SingleMentorDetails", "Error fetching reviews", e)
                }

                // Create mentor model with fetched data
                mentor = MentorModel(
                    id = 0, // Not using numeric IDs anymore
                    userId = mentorId,
                    name = name,
                    profession = profession,
                    email = email,
                    courses = coursesList.size,
                    students = userData["students"] as? Int ?: 0,
                    ratings = reviewsList.size,
                    coursesList = coursesList,
                    reviews = reviewsList,
                    imageRes = R.drawable.ic_profile_placeholder,
                    profileImageUrl = profileImageUrl
                )

                Log.d("SingleMentorDetails", "Successfully loaded mentor: $name with ${coursesList.size} courses")
            } else {
                errorMessage = "Mentor not found"
                Log.e("SingleMentorDetails", "Mentor document not found for ID: $mentorId")
            }
        } catch (e: Exception) {
            errorMessage = "Error loading mentor: ${e.message}"
            Log.e("SingleMentorDetails", "Error fetching mentor data", e)
        } finally {
            isLoading = false
        }
    }

    // Function to handle follow/unfollow
    val handleFollowToggle = {
        if (auth.currentUser == null) {
            // User not logged in - show message
            Toast.makeText(context, "Please login to follow mentors", Toast.LENGTH_SHORT).show()
        } else {
            scope.launch {
                try {
                    val currentUserId = auth.currentUser!!.uid
                    val followerRef = db.collection("mentors")
                        .document(mentorId)
                        .collection("followers")
                        .document(currentUserId)

                    if (isFollowing) {
                        // If already following, remove follower document to unfollow
                        followerRef.delete().await()
                        followCount -= 1
                        isFollowing = false
                        Toast.makeText(context, "Unfollowed successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        // If not following, add follower document to follow
                        followerRef.set(mapOf(
                            "timestamp" to com.google.firebase.Timestamp.now(),
                            "userId" to currentUserId,
                            "userName" to (auth.currentUser?.displayName ?: "")
                        )).await()
                        followCount += 1
                        isFollowing = true
                        Toast.makeText(context, "Following successfully", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Log.e("SingleMentorDetails", "Error toggling follow status", e)
                    Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = LightBlueBackground,
        topBar = {
            AuthTopBar(
                title = "Mentor",
                onBackClick = { navController.navigateUp() }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(LightBlueBackground)
                .padding(padding)
        ) {
            if (isLoading) {
                item {
                    Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
            } else if (errorMessage != null) {
                item {
                    Text(
                        text = errorMessage ?: "Unknown error",
                        modifier = Modifier.padding(16.dp),
                        color = Color.Red
                    )
                }
            } else {
                mentor?.let { mentorData ->
                    item {
                        MentorInfoSection(
                            mentorData = mentorData,
                            followCount = followCount,
                            isFollowing = isFollowing,
                            onFollowToggle = handleFollowToggle as () -> Unit,
                            onMessageClick = {
                                // Navigate to chat screen with this mentor
                                if (mentorData.email.isNotEmpty()) {
                                    val encodedName = URLEncoder.encode(mentorData.name, StandardCharsets.UTF_8.toString())
                                    val encodedEmail = URLEncoder.encode(mentorData.email, StandardCharsets.UTF_8.toString())
                                    navController.navigate("ChatDetailScreen/$encodedName/$encodedEmail")
                                }
                            }
                        )
                    }
                    item {
                        ReviewsSection(mentorData, mentorId, scope)
                    }
                } ?: run {
                    item {
                        Text("Mentor not found", modifier = Modifier.padding(16.dp), color = Color.Red)
                    }
                }
            }
        }
    }
}

@Composable
private fun MentorInfoSection(
    mentorData: MentorModel,  // This already contains the mentor information
    followCount: Int,
    isFollowing: Boolean,
    onFollowToggle: () -> Unit,
    onMessageClick: () -> Unit
) {
    // We don't need to fetch the current user's data
    // Instead, use the mentorData that's passed to this composable

    // Extract mentor information from mentorData
//    val mentorName = mentorData.fullName ?: "Mentor"
    val mentorProfileImageUrl = mentorData.profileImageUrl ?: ""

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Profile Image with AsyncImage
        Box(
            modifier = Modifier.size(100.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            if (mentorProfileImageUrl.isNotEmpty()) {
                // Decode the Base64 string to Bitmap and display it
                val bitmap = decodeBase64ToBitmap(mentorProfileImageUrl)
                if (bitmap != null) {
                    Image(
                        painter = BitmapPainter(bitmap.asImageBitmap()),
                        contentDescription = "Mentor Profile Picture",
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
                        contentDescription = "Mentor Profile Picture",
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
                    contentDescription = "Mentor Profile Picture",
                    modifier = Modifier
                        .size(110.dp)
                        .clip(CircleShape)
                        .background(Color.Gray),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = mentorData.name,
            style = MaterialTheme.customTypography.jost.semiBold,
            fontSize = 24.sp
        )

        Text(
            text = mentorData.profession,
            style = MaterialTheme.customTypography.mulish.bold,
            fontSize = 16.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Stats Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Show Followers count
            StatItem("Followers", followCount.toString())
            StatItem("Students", mentorData.students.toString())
            StatItem("Reviews", mentorData.ratings.toString())
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Buttons Row
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            val buttonModifier = Modifier
                .height(60.dp)
                .weight(1f)
                .padding(8.dp)

            // Pass the isFollowing state and toggle callback to FollowButton
            FollowButton(
                modifier = buttonModifier,
                isFollowing = isFollowing,
                onToggle = onFollowToggle
            )

            Button(
                onClick = onMessageClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0961F5)),
                modifier = buttonModifier,
                shape = RoundedCornerShape(40.dp)
            ) {
                Text(
                    text = "Message",
                    style = MaterialTheme.customTypography.jost.semiBold,
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
private fun StatItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = MaterialTheme.customTypography.jost.semiBold,
            fontSize = 17.sp
        )
        Text(
            text = label,
            fontSize = 13.sp,
            style = MaterialTheme.customTypography.mulish.bold
        )
    }
}

@Composable
private fun ReviewsSection(mentor: MentorModel, mentorId: String, scope: CoroutineScope) {
    val db = FirebaseFirestore.getInstance()
    var reviewText by remember { mutableStateOf("") }
    var isSubmitting by remember { mutableStateOf(false) }
    var submissionMessage by remember { mutableStateOf<String?>(null) }
    var reviews by remember { mutableStateOf(mentor.reviews) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Reviews", style = MaterialTheme.customTypography.jost.semiBold, fontSize = 18.sp)
        Spacer(Modifier.height(16.dp))

        // Review submission form
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF4FD)),
            elevation = CardDefaults.cardElevation(3.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Add Your Review",
                    style = MaterialTheme.customTypography.jost.semiBold,
                    fontSize = 16.sp
                )
                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = reviewText,
                    onValueChange = { reviewText = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Write your review here...") },
                    minLines = 3,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF0961F5),
                        unfocusedBorderColor = Color.Gray
                    )
                )

                Spacer(Modifier.height(8.dp))

                Button(
                    onClick = {
                        if (reviewText.isNotBlank()) {
                            isSubmitting = true
                            scope.launch {
                                try {
                                    // Create a new review document
                                    val newReview = hashMapOf(
                                        "reviewerName" to (FirebaseAuth.getInstance().currentUser?.displayName ?: "Anonymous"),
                                        "reviewText" to reviewText,
                                        "date" to "Just now",
                                        "timestamp" to com.google.firebase.Timestamp.now()
                                    )

                                    // Add to Firestore
                                    db.collection("mentors")
                                        .document(mentorId)
                                        .collection("reviews")
                                        .add(newReview)
                                        .await()

                                    // Create a ReviewModel from the submitted data
                                    val addedReview = ReviewModel(
                                        reviewerName = newReview["reviewerName"] as String,
                                        reviewText = newReview["reviewText"] as String,
                                        date = newReview["date"] as String
                                    )

                                    // Update the UI
                                    reviews = reviews + addedReview
                                    reviewText = ""
                                    submissionMessage = "Review submitted successfully!"
                                } catch (e: Exception) {
                                    Log.e("ReviewSubmission", "Error submitting review", e)
                                    submissionMessage = "Error submitting review: ${e.message}"
                                } finally {
                                    isSubmitting = false
                                }
                            }
                        }
                    },
                    modifier = Modifier.align(Alignment.End),
                    enabled = reviewText.isNotBlank() && !isSubmitting,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0961F5))
                ) {
                    if (isSubmitting) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text("Submit Review")
                    }
                }

                // Show submission message if any
                submissionMessage?.let {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = it,
                        color = if (it.startsWith("Error")) Color.Red else Color(0xFF167F71),
                        style = MaterialTheme.customTypography.mulish.bold,
                        fontSize = 14.sp
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // Display existing reviews
        if (reviews.isEmpty()) {
            Text(
                text = "No reviews yet. Be the first to review!",
                style = MaterialTheme.customTypography.mulish.bold,
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        } else {
            reviews.forEach { review ->
                ReviewItem(review)
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun FollowButton(
    modifier: Modifier = Modifier,
    isFollowing: Boolean = false,
    onToggle: () -> Unit = {}
) {
    Button(
        onClick = onToggle,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isFollowing) Color.LightGray else Color(0xFF0961F5)
        ),
        modifier = modifier,
        shape = RoundedCornerShape(40.dp)
    ) {
        Text(
            text = if (isFollowing) "Unfollow" else "Follow",
            style = MaterialTheme.customTypography.jost.semiBold,
            color = Color.White,
            fontSize = 18.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SingleMentorDetailsPreview() {
    SingleMentorDetails(
        navController = NavController(LocalContext.current),
        mentorId = "sample_user_id"
    )
}