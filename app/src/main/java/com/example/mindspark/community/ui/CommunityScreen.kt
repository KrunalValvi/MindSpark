@file:Suppress("DEPRECATION")

package com.example.mindspark.community.ui

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.text.format.DateUtils
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults.outlinedTextFieldColors
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberBottomSheetScaffoldState
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.Bitmap
import com.example.mindspark.R
import com.example.mindspark.auth.components.AuthTopBar
import com.example.mindspark.community.components.CommunityCategoriesList
import com.example.mindspark.community.data.CommunityViewModel
import com.example.mindspark.community.model.Comment
import com.example.mindspark.community.model.Post
import com.example.mindspark.ui.theme.LightBlueBackground
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityScreen(navController: NavController) {
    val viewModel: CommunityViewModel = viewModel()
    var isRefreshing by remember { mutableStateOf(false) }

    // Fix for the bottom sheet state issue - create with skipHiddenState = false
    val bottomSheetState = remember {
        SheetState(
            skipPartiallyExpanded = false,
            skipHiddenState = false,
            initialValue = SheetValue.Hidden
        )
    }
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = bottomSheetState)
    val scope = rememberCoroutineScope()
    var postContent by remember { mutableStateOf("") }
    val currentUser = Firebase.auth.currentUser
    val userId = currentUser?.uid ?: ""
    val fullName = currentUser?.displayName ?: "Unknown"
    val categories =
        remember { listOf("All", "Questions", "Advice", "Success Stories", "Challenges") }
    var selectedCategory by remember { mutableStateOf("All") }
    var expandedPostId by remember { mutableStateOf<String?>(null) }
    val focusRequester = remember { FocusRequester() }

    // Filter posts based on selected category
    val filteredPosts = if (selectedCategory == "All") {
        viewModel.posts
    } else {
        viewModel.posts.filter { it.category == selectedCategory }
    }

    // LaunchedEffect to observe state changes
    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            viewModel.refreshPosts {
                isRefreshing = false
            }
        }
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        sheetContent = {
            NewPostSheet(
                postContent = postContent,
                onPostContentChange = { postContent = it },
                onClose = {
                    scope.launch {
                        // Use partialExpand or hide based on current state
                        scaffoldState.bottomSheetState.hide()
                        postContent = ""
                    }
                },
                onPost = {
                    viewModel.addNewPost(
                        content = postContent,
                        userId = userId,
                        userName = fullName,
                        category = selectedCategory
                    ) { success ->
                        if (success) {
                            scope.launch {
                                scaffoldState.bottomSheetState.hide()
                                postContent = ""
                                viewModel.refreshPosts {}
                            }
                        }
                    }
                }
            )
        },
        sheetContainerColor = Color.White,
        sheetContentColor = Color.Black,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        modifier = Modifier.background(LightBlueBackground),
        containerColor = LightBlueBackground,
    ) { paddingValues ->
        Scaffold(
            modifier = Modifier.background(LightBlueBackground),
            containerColor = LightBlueBackground,
            topBar = {
                AuthTopBar(title = "Community", onBackClick = { navController.navigateUp() })
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        scope.launch {
                            // Just use expand, not calling it twice
                            scaffoldState.bottomSheetState.expand()
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "New Post",
                        tint = Color.White
                    )
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(LightBlueBackground)
                    .padding(innerPadding)
            ) {
                // Categories list
//                CommunityCategoriesList(
//                    categories = categories,
//                    selectedCategory = selectedCategory,
//                    onCategorySelected = { category ->
//                        selectedCategory = category
//                    }
//                )

                Spacer(modifier = Modifier.height(8.dp))

                SwipeRefresh(
                    state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
                    onRefresh = {
                        isRefreshing = true
                    }
                ) {
                    if (filteredPosts.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            if (isRefreshing) {
                                CircularProgressIndicator()
                            } else {
                                Text(
                                    text = "No posts yet. Be the first to share!",
                                    color = Color.Gray,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(LightBlueBackground)
                                .padding(horizontal = 8.dp, vertical = 12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            items(filteredPosts) { post ->
                                PostCard(
                                    post = post,
                                    isExpanded = expandedPostId == post.postId,
                                    onLikePost = { postId, isLiked ->
                                        likePost(
                                            postId, isLiked,
                                            onSuccess = { viewModel.refreshPosts {} },
                                            onFailure = { /* Handle error */ }
                                        )
                                    },
                                    onCommentClick = {
                                        expandedPostId =
                                            if (expandedPostId == post.postId) null else post.postId
                                    },
                                    onAddComment = { postId, comment ->
                                        viewModel.addComment(postId, comment) {
                                            viewModel.refreshPosts {}
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewPostSheet(
    postContent: String,
    onPostContentChange: (String) -> Unit,
    onClose: () -> Unit,
    onPost: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }

//    LaunchedEffect(Unit) {
//        focusRequester.requestFocus()
//    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onClose) {
                Icon(Icons.Default.Close, contentDescription = "Close")
            }

            Text(
                text = "New Post",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Button(
                onClick = onPost,
                enabled = postContent.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    disabledContainerColor = Color.Gray
                ),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text("Post")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = postContent,
            onValueChange = onPostContentChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .focusRequester(focusRequester),
            placeholder = { Text("What's on your mind?") },
            colors = outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = Color.LightGray
            )
        )
    }
}

@Composable
fun PostCard(
    post: Post,
    isExpanded: Boolean,
    onLikePost: (String, Boolean) -> Unit,
    onCommentClick: () -> Unit,
    onAddComment: (String, Comment) -> Unit
) {
    var profileImageUrl by remember { mutableStateOf("") }
    var showMenu by remember { mutableStateOf(false) }
    var commentText by remember { mutableStateOf("") }
    val currentUser = Firebase.auth.currentUser
    val context = LocalContext.current
    var userFullName by remember { mutableStateOf("User") }
    val isLiked = remember { mutableStateOf(post.likedBy.contains(currentUser?.uid)) }
    val commentsCount = post.comments?.size ?: 0

    val playStoreUrl = "https://play.google.com/store/apps/details?id=your.package.name"
    val gitHubUrl = "https://github.com/yourusername/yourrepository"

    LaunchedEffect(post.userId) {
        val db = FirebaseFirestore.getInstance()
        db.collection("users").document(post.userId)
            .get()
            .addOnSuccessListener { document ->
                profileImageUrl = document.getString("profileImageUrl") ?: ""
                userFullName = document.getString("fullName") ?: "User"
            }
            .addOnFailureListener {
                profileImageUrl = ""
                userFullName = "User"
            }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Profile image
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
                    Text(text = userFullName, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text(
                        text = formatTimestamp(post.timestamp),
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                if (currentUser?.uid == post.userId) {
                    Box {
                        IconButton(onClick = { showMenu = true }) {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(id = R.drawable.ic_more_vert),
                                contentDescription = "More Options"
                            )
                        }
                        DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                            DropdownMenuItem(text = { Text("Delete") }, onClick = {
                                deletePost(
                                    post.postId,
                                    onSuccess = { showMenu = false },
                                    onFailure = { /* Handle failure */ }
                                )
                            })
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = post.content,
                fontSize = 16.sp,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))
            Divider()
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Like Button
                Row(
                    modifier = Modifier.clickable {
                        isLiked.value = !isLiked.value
                        onLikePost(post.postId, isLiked.value)
                    },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (isLiked.value) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "Like",
                        tint = if (isLiked.value) Color.Red else Color.Gray,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = post.likes.toString(), fontSize = 14.sp, color = Color.Gray)
                }

                // Comment Button with counter
                Row(
                    modifier = Modifier.clickable { onCommentClick() },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.ChatBubbleOutline,
                        contentDescription = "Comment",
                        tint = Color.Gray,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = commentsCount.toString(), fontSize = 14.sp, color = Color.Gray)
                }

                // Share Button
                IconButton(onClick = {
                    shareApp(context, playStoreUrl, gitHubUrl)
                }) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share",
                        tint = Color.Gray
                    )
                }
            }

            // Comments section - only show when expanded
            if (isExpanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Divider()
                Spacer(modifier = Modifier.height(8.dp))

                // List existing comments
                post.comments?.forEach { comment ->
                    CommentItem(comment = comment)
                    Spacer(modifier = Modifier.height(8.dp))
                }

                // Add new comment
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = commentText,
                        onValueChange = { commentText = it },
                        placeholder = { Text("Add a comment...") },
                        modifier = Modifier.weight(1f),
                        maxLines = 2
                    )

                    IconButton(
                        onClick = {
                            if (commentText.isNotBlank()) {
                                val newComment = Comment(
                                    commentId = "", // Add the commentId explicitly
                                    userId = currentUser?.uid ?: "",
                                    userName = currentUser?.displayName ?: "User",
                                    content = commentText,
                                    timestamp = System.currentTimeMillis()
                                )
                                onAddComment(post.postId, newComment)
                                commentText = ""
                            }
                        },
                        enabled = commentText.isNotBlank()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "Send Comment",
                            tint = if (commentText.isBlank()) Color.Gray else MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CommentItem(comment: Comment) {
    var profileImageUrl by remember { mutableStateOf("") }
    var userFullName by remember { mutableStateOf(comment.userName) }

    LaunchedEffect(comment.userId) {
        val db = FirebaseFirestore.getInstance()
        db.collection("users").document(comment.userId)
            .get()
            .addOnSuccessListener { document ->
                profileImageUrl = document.getString("profileImageUrl") ?: ""
                userFullName = document.getString("fullName") ?: comment.userName
            }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.Top
    ) {
        // Profile image
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
                .size(32.dp)
                .clip(CircleShape)
                .background(Color.Gray),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = userFullName,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = formatTimestamp(comment.timestamp),
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Text(
                text = comment.content,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

fun shareApp(context: Context, playStoreUrl: String, gitHubUrl: String) {
    val shareMessage = "Check out this app:\nGoogle Play Store: $playStoreUrl\nGitHub: $gitHubUrl"
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, shareMessage)
    }
    context.startActivity(Intent.createChooser(shareIntent, "Share via"))
}

fun likePost(
    postId: String,
    isLiked: Boolean,
    onSuccess: () -> Unit,
    onFailure: (Exception) -> Unit
) {
    val db = FirebaseFirestore.getInstance()
    val userId = Firebase.auth.currentUser?.uid ?: return

    val postRef = db.collection("posts").document(postId)

    db.runTransaction { transaction ->
        val postSnapshot = transaction.get(postRef)
        val currentLikes = postSnapshot.getLong("likes") ?: 0
        val likedByList =
            (postSnapshot.get("likedBy") as? List<*>)?.mapNotNull { it as? String } ?: listOf()

        val updatedLikes: Long
        val updatedLikedBy: List<String>

        if (isLiked) {
            updatedLikes = currentLikes + 1
            updatedLikedBy = likedByList + userId
        } else {
            updatedLikes = (currentLikes - 1).coerceAtLeast(0)
            updatedLikedBy = likedByList - userId
        }

        transaction.update(postRef, "likes", updatedLikes)
        transaction.update(postRef, "likedBy", updatedLikedBy)
    }.addOnSuccessListener { onSuccess() }
        .addOnFailureListener { exception -> onFailure(exception) }
}


fun deletePost(postId: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    db.collection("posts").document(postId)
        .delete()
        .addOnSuccessListener {
            onSuccess()
        }
        .addOnFailureListener { exception ->
            onFailure(exception)
        }
}

fun formatTimestamp(timestamp: Long): String {
    val now = System.currentTimeMillis()
    return DateUtils.getRelativeTimeSpanString(
        timestamp,
        now,
        DateUtils.MINUTE_IN_MILLIS
    ).toString()
}

fun decodeBase64ToBitmap(base64Str: String): Bitmap? {
    return try {
        // Remove any data URI prefix if present.
        val pureBase64 = if (base64Str.contains(",")) base64Str.substringAfter(",") else base64Str
        val decodedBytes = Base64.decode(pureBase64, Base64.DEFAULT)
        BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

@Preview(showBackground = true)
@Composable
fun CommunityScreenPreview() {
    CommunityScreen(navController = NavController(LocalContext.current))
}