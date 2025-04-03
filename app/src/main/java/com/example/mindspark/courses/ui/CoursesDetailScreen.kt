package com.example.mindspark.courses.ui

import android.app.Activity
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mindspark.R
import com.example.mindspark.auth.components.AuthButton
import com.example.mindspark.auth.components.AuthTopBar
import com.example.mindspark.community.ui.decodeBase64ToBitmap
import com.example.mindspark.courses.components.CourseDetailComponents
import com.example.mindspark.courses.data.CourseData
import com.example.mindspark.courses.model.CourseModel
import com.example.mindspark.courses.model.CourseReviewModel
import com.example.mindspark.courses.model.MentorModel
import com.example.mindspark.ui.theme.customTypography
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.json.JSONObject

private val LightBlueBackground = Color(0xFFF5F9FF)

@Composable
fun CourseDetailScreen(navController: NavController, id: String) {
    var course by remember { mutableStateOf<CourseModel?>(null) }
    var mentors by remember { mutableStateOf<List<MentorModel>>(emptyList()) }
    var reviews by remember { mutableStateOf<List<CourseReviewModel>>(emptyList()) }
    var reviewText by remember { mutableStateOf("") }
    var isSubmittingReview by remember { mutableStateOf(false) }
    var reviewSubmissionMessage by remember { mutableStateOf<String?>(null) }
    val scrollState = rememberScrollState()
    val isAtBottom = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var paymentStatus by remember { mutableStateOf<String?>(null) }

    // Initialize Razorpay Checkout
    LaunchedEffect(Unit) {
        Checkout.preload(context)
    }

    // Payment result callback
    val activity = context as Activity

    class PaymentActivity : PaymentResultListener {
        override fun onPaymentSuccess(razorpayPaymentId: String) {
            paymentStatus = "Payment Successful: $razorpayPaymentId"
            // Update Firebase with payment status
            scope.launch {
                try {
                    val db = FirebaseFirestore.getInstance()
                    val userId = FirebaseAuth.getInstance().currentUser?.uid
                    if (userId != null && course != null) {
                        val purchaseData = hashMapOf(
                            "courseId" to id,
                            "userId" to userId,
                            "paymentId" to razorpayPaymentId,
                            "purchaseDate" to com.google.firebase.Timestamp.now(),
                            "status" to "completed"
                        )

                        // Add to purchases collection
                        db.collection("purchases").add(purchaseData).await()

                        // Add to user's purchased courses
                        db.collection("users").document(userId)
                            .collection("purchasedCourses")
                            .document(id)
                            .set(
                                hashMapOf(
                                    "purchaseDate" to com.google.firebase.Timestamp.now(),
                                    "courseId" to id
                                )
                            ).await()

                        Toast.makeText(context, "Course purchased successfully!", Toast.LENGTH_LONG)
                            .show()
                    }
                } catch (e: Exception) {
                    Log.e("CourseDetailScreen", "Error updating purchase status", e)
                    Toast.makeText(context, "Error updating purchase status", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        override fun onPaymentError(code: Int, description: String) {
            paymentStatus = "Payment Failed: $description"
            Toast.makeText(context, "Payment failed: $description", Toast.LENGTH_LONG).show()
        }
    }

    val paymentListener = PaymentActivity()
    DisposableEffect(activity) {
        onDispose {}
    }

    fun startPayment(course: CourseModel) {
        val activity = context as? Activity ?: return
        val checkout = Checkout()
        checkout.setKeyID("rzp_test_QJ87l2Xl212rFc") // Replace with your test key

        try {
            val options = JSONObject()
            options.put("name", "MindSpark")
            options.put("description", course.title)
            options.put("currency", "INR")
            options.put("amount", (course.price.toDouble() * 100).toInt()) // Convert to paise
            options.put("prefill.email", FirebaseAuth.getInstance().currentUser?.email ?: "")
            options.put("prefill.contact", "")
            options.put("theme.color", "#0961F5")

            checkout.open(activity, options)
        } catch (e: Exception) {
            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    // Fetch course data from Firebase using the document ID
    LaunchedEffect(id) {
        course = CourseData.getCourseByDocId(id)

        // Fetch mentors for this course
        if (course != null && course!!.mentorIds.isNotEmpty()) {
            val db = FirebaseFirestore.getInstance()
            val mentorsList = mutableListOf<MentorModel>()

            try {
                // For each mentorId in the course, fetch the mentor data
                val currentCourse = course // Create a local non-null variable
                currentCourse?.mentorIds?.forEach { mentorId ->
                    // Try to get mentor from users collection
                    val userDoc = db.collection("users").document(mentorId.toString()).get().await()

                    if (userDoc.exists()) {
                        val userData = userDoc.data
                        if (userData != null) {
                            val mentor = MentorModel(
                                id = mentorId,
                                userId = userDoc.id,
                                name = userData["fullName"] as? String ?: "Unknown",
                                profession = userData["profession"] as? String ?: "Mentor",
                                courses = (userData["courses"] as? Long)?.toInt() ?: 0,
                                students = (userData["students"] as? Long)?.toInt() ?: 0,
                                ratings = (userData["ratings"] as? Long)?.toInt() ?: 0,
                                profileImageUrl = userData["profileImageUrl"] as? String ?: "",
                                imageRes = R.drawable.ic_profile_placeholder

                            )
                            mentorsList.add(mentor)
                        }
                    } else {
                        // Try to get mentor from mentors collection as fallback
                        val mentorDoc =
                            db.collection("mentors").document(mentorId.toString()).get().await()
                        if (mentorDoc.exists()) {
                            val mentorData = mentorDoc.data
                            if (mentorData != null) {
                                val mentor = MentorModel(
                                    id = mentorId,
                                    userId = mentorDoc.id,
                                    name = mentorData["name"] as? String ?: "Unknown",
                                    profession = mentorData["profession"] as? String ?: "Mentor",
                                    courses = (mentorData["courses"] as? Long)?.toInt() ?: 0,
                                    students = (mentorData["students"] as? Long)?.toInt() ?: 0,
                                    ratings = (mentorData["ratings"] as? Long)?.toInt() ?: 0,
                                    profileImageUrl = mentorData["profile_image"] as? String ?: "",
                                    imageRes = R.drawable.ic_profile_placeholder
                                )
                                mentorsList.add(mentor)
                            }
                        }
                    }
                }

                // If no mentors found by ID, try to find by creator name
                if (mentorsList.isEmpty() && course?.mentorName?.isNotEmpty() == true) {
                    val mentorName = course?.mentorName ?: ""
                    val userQuery = db.collection("users")
                        .whereEqualTo("fullName", mentorName)
                        .get().await()

                    if (!userQuery.isEmpty) {
                        val userDoc = userQuery.documents.first()
                        val userData = userDoc.data
                        if (userData != null) {
                            val mentor = MentorModel(
                                id = 0, // Default ID
                                userId = userDoc.id,
                                name = userData["fullName"] as? String ?: mentorName,
                                profession = userData["profession"] as? String ?: "Mentor",
                                courses = (userData["courses"] as? Long)?.toInt() ?: 0,
                                students = (userData["students"] as? Long)?.toInt() ?: 0,
                                ratings = (userData["ratings"] as? Long)?.toInt() ?: 0,
                                profileImageUrl = userData["profileImageUrl"] as? String ?: "",
                                imageRes = R.drawable.ic_profile_placeholder
                            )
                            mentorsList.add(mentor)
                        }
                    }
                }

                mentors = mentorsList
                Log.d("CourseDetailScreen", "Loaded ${mentors.size} mentors for course")
            } catch (e: Exception) {
                Log.e("CourseDetailScreen", "Error loading mentors", e)
            }
        }

        // Fetch reviews for this course
        try {
            val db = FirebaseFirestore.getInstance()
            val reviewsQuery = db.collection("courses")
                .document(id)
                .collection("reviews")
                .get().await()

            // Inside LaunchedEffect in CourseDetailScreen
            val reviewsList = reviewsQuery.documents.mapNotNull { reviewDoc ->
                val reviewData = reviewDoc.data ?: return@mapNotNull null
                CourseReviewModel(
                    id = reviewDoc.id, // Store the document ID
                    reviewerName = reviewData["reviewerName"] as? String ?: "Anonymous",
                    reviewText = reviewData["reviewText"] as? String ?: "",
                    date = reviewData["date"] as? String ?: "Recently",
                    likes = (reviewData["likes"] as? Long)?.toInt() ?: 0,
                    reviewerImageUrl = reviewData["reviewerImageUrl"] as? String ?: ""
                )
            }

            reviews = reviewsList
            Log.d("CourseDetailScreen", "Loaded ${reviews.size} reviews for course")
        } catch (e: Exception) {
            Log.e("CourseDetailScreen", "Error loading reviews", e)
        }
    }

    // Track scroll position to show/hide the Purchase button
    LaunchedEffect(scrollState) {
        snapshotFlow { scrollState.value }
            .collect { scrollPosition ->
                isAtBottom.value = scrollPosition >= scrollState.maxValue - 100
            }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier.background(LightBlueBackground),
            containerColor = LightBlueBackground,
            topBar = {
                AuthTopBar(
                    title = "Course Details",
                    onBackClick = { navController.navigateUp() }
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(scrollState)
            ) {
                if (course != null) {
                    // Create a local non-null variable to avoid smart cast issues
                    val currentCourse = course!!
                    // Display course details. We pass a lambda for onMentorClick.
                    CourseDetailComponents(
                        course = currentCourse,
                        mentors = mentors,
                        onMentorClick = { mentor ->
                            Log.d(
                                "Navigation",
                                "Navigating to SingleMentorDetails with mentorId: ${mentor.userId}"
                            )
                            navController.navigate("SingleMentorDetails/${mentor.userId}")
                        },
                        onPlayVideo = { videoUrl ->
                            // Encode the video URL before passing it to the route.
                            val encodedUrl = Uri.encode(videoUrl)
                            navController.navigate("VideoPlayerScreen/$encodedUrl")
                        }
                    )

                    // Review Section
                    ReviewSection(
                        courseId = id, // Pass the course ID
                        reviews = reviews,
                        reviewText = reviewText,
                        onReviewTextChange = { reviewText = it },
                        isSubmitting = isSubmittingReview,
                        submissionMessage = reviewSubmissionMessage,
                        onSubmitReview = {
                            if (reviewText.isNotBlank()) {
                                isSubmittingReview = true
                                scope.launch {
                                    try {
                                        val currentUser = FirebaseAuth.getInstance().currentUser
                                        if (currentUser == null) {
                                            reviewSubmissionMessage =
                                                "Error: Please log in to submit a review"
                                            isSubmittingReview = false
                                            return@launch
                                        }

                                        val db = FirebaseFirestore.getInstance()
                                        val reviewsCollection =
                                            db.collection("courses").document(id)
                                                .collection("reviews")

                                        // Get the user's profile image from Firestore
                                        var profileImageBase64 = ""
                                        try {
                                            val userDoc =
                                                db.collection("users").document(currentUser.uid)
                                                    .get().await()
                                            if (userDoc.exists() && userDoc.data?.containsKey("profileImageUrl") == true) {
                                                profileImageBase64 =
                                                    userDoc.data?.get("profileImageUrl") as? String
                                                        ?: ""
                                            }
                                        } catch (e: Exception) {
                                            Log.e(
                                                "CourseDetailScreen",
                                                "Error fetching user profile image",
                                                e
                                            )
                                        }

                                        val reviewData = hashMapOf(
                                            "reviewerName" to (currentUser.displayName
                                                ?: "Anonymous"),
                                            "reviewText" to reviewText,
                                            "date" to "Just now", // You can format a proper date here
                                            "likes" to 0,
                                            "reviewerImageUrl" to profileImageBase64, // Use the Base64 image from user's profile
                                            "userId" to currentUser.uid,
                                            "timestamp" to com.google.firebase.Timestamp.now()
                                        )

                                        // Add the review
                                        val addedReviewRef =
                                            reviewsCollection.add(reviewData).await()

                                        // Create new review object with the document ID
                                        val newReview = CourseReviewModel(
                                            id = addedReviewRef.id,
                                            reviewerName = reviewData["reviewerName"] as String,
                                            reviewText = reviewData["reviewText"] as String,
                                            date = reviewData["date"] as String,
                                            likes = 0,
                                            reviewerImageUrl = profileImageBase64
                                        )

                                        // Update UI
                                        reviews = listOf(newReview) + reviews
                                        reviewText = ""
                                        reviewSubmissionMessage = "Review submitted successfully"
                                    } catch (e: Exception) {
                                        Log.e("CourseDetailScreen", "Error submitting review", e)
                                        reviewSubmissionMessage = "Error: ${e.message}"
                                    } finally {
                                        isSubmittingReview = false
                                    }
                                }
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(80.dp))
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Course not found",
                            style = MaterialTheme.customTypography.mulish.bold
                        )
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = isAtBottom.value,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(LightBlueBackground)
                .padding(16.dp),
            enter = fadeIn() + slideInVertically(initialOffsetY = { it }),
            exit = fadeOut() + slideOutVertically(targetOffsetY = { it })
        ) {
            AuthButton(
                text = "Purchase Course",
                onClick = {
                    course?.let { startPayment(it) }
                },
                modifier = Modifier
                    .width(200.dp)
                    .wrapContentHeight()
            )
        }

        // Show payment status if available
        paymentStatus?.let { status ->
            Toast.makeText(context, status, Toast.LENGTH_LONG).show()
            paymentStatus = null
        }
    }
}


@Composable
fun ReviewSection(
    courseId: String, // Add courseId parameter
    reviews: List<CourseReviewModel>,
    reviewText: String,
    onReviewTextChange: (String) -> Unit,
    isSubmitting: Boolean,
    submissionMessage: String?,
    onSubmitReview: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            "Reviews",
            style = MaterialTheme.customTypography.jost.semiBold,
            fontSize = 18.sp
        )
        Spacer(Modifier.height(16.dp))

        // Review submission form
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF4FD)),
            elevation = CardDefaults.cardElevation(3.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Add Your Review",
                    style = MaterialTheme.customTypography.jost.semiBold,
                    fontSize = 16.sp
                )
                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = reviewText,
                    onValueChange = onReviewTextChange,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Share your experience with this course...") },
                    minLines = 3,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF0961F5),
                        unfocusedBorderColor = Color.Gray,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    shape = MaterialTheme.shapes.small
                )

                Spacer(Modifier.height(12.dp))

                Button(
                    onClick = onSubmitReview,
                    modifier = Modifier.align(Alignment.End),
                    enabled = reviewText.isNotBlank() && !isSubmitting,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0961F5)),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    if (isSubmitting) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(
                            "Submit Review",
                            style = MaterialTheme.customTypography.mulish.bold
                        )
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

        Spacer(Modifier.height(24.dp))

        // Display existing reviews
        if (reviews.isEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(1.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No reviews yet. Be the first to review!",
                        style = MaterialTheme.customTypography.mulish.bold,
                        fontSize = 15.sp,
                        color = Color.Gray
                    )
                }
            }
        } else {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "${reviews.size} ${if (reviews.size == 1) "Review" else "Reviews"}",
                        style = MaterialTheme.customTypography.jost.semiBold,
                        fontSize = 16.sp
                    )

                    Divider(
                        modifier = Modifier.padding(vertical = 12.dp),
                        color = Color(0xFFECECEC)
                    )

                    reviews.forEach { review ->
                        ReviewItem(courseId, review)
                        Divider(
                            modifier = Modifier.padding(vertical = 12.dp),
                            color = Color(0xFFECECEC)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ReviewItem(courseId: String, review: CourseReviewModel) {
    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()
    val reviewId = remember { review.id }
    var isLiked by remember { mutableStateOf(false) }
    var likeCount by remember { mutableStateOf(review.likes) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var profileImageUrl by remember { mutableStateOf("") }


    // Create a painter from the base64 image string
    val painter = if (review.reviewerImageUrl.isNotEmpty()) {
        val bitmap = decodeBase64ToBitmap(review.reviewerImageUrl)
        if (bitmap != null)
            BitmapPainter(bitmap.asImageBitmap())
        else
            painterResource(id = R.drawable.ic_profile_placeholder)
    } else {
        painterResource(id = R.drawable.ic_profile_placeholder)
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        // Profile image using the painter
        val painter = if (profileImageUrl.isNotEmpty()) {
            val bitmap = decodeBase64ToBitmap(profileImageUrl)
            if (bitmap != null)
                BitmapPainter(bitmap.asImageBitmap())
            else
                painterResource(id = R.drawable.ic_profile_placeholder)
        } else {
            painterResource(id = R.drawable.ic_profile_placeholder)
        }
        Image(
            painter = painter,
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(Color.Gray),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = review.reviewerName,
                style = MaterialTheme.customTypography.jost.semiBold,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = review.reviewText,
                style = MaterialTheme.customTypography.mulish.regular,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = review.date,
                    style = MaterialTheme.customTypography.mulish.regular,
                    fontSize = 12.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.width(16.dp))

                // Like button
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        if (auth.currentUser != null) {
                            scope.launch {
                                try {
                                    isLiked = !isLiked
                                    val newLikeCount =
                                        if (isLiked) likeCount + 1 else likeCount - 1

                                    // Update UI immediately for better UX
                                    likeCount = newLikeCount

                                    // Update in Firestore
                                    db.collection("courses")
                                        .document(courseId)
                                        .collection("reviews")
                                        .document(reviewId)
                                        .update("likes", newLikeCount)
                                        .await()

                                } catch (e: Exception) {
                                    // Revert UI on error
                                    isLiked = !isLiked
                                    likeCount = review.likes
                                    Toast.makeText(
                                        context,
                                        "Failed to update like",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        } else {
                            Toast.makeText(
                                context,
                                "Please sign in to like reviews",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                ) {
                    Icon(
                        imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = if (isLiked) "Unlike" else "Like",
                        tint = if (isLiked) Color.Red else Color.Gray,
                        modifier = Modifier.size(18.dp)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = likeCount.toString(),
                        style = MaterialTheme.customTypography.mulish.regular,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}