package com.example.mindspark.courses.model

import androidx.annotation.DrawableRes
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.PropertyName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

data class FeatureModel(
    val description: String,
    @DrawableRes val iconRes: Int // Use drawable resource for icon if needed
)

data class VideoDetails(
    val title: String = "",
    val duration: String = "",
    val videoUrl: String = ""
) {
    // Explicit no-argument constructor for Firestore deserialization
    constructor() : this("", "", "")
}

@IgnoreExtraProperties
data class CourseModel(
    @PropertyName("course_id") val id: Int = 0, // legacy field (can be ignored)
    val category: String = "",
    val title: String = "",
    val price: String = "",
    val rating: String = "",
    val students: String = "",
    val imageRes: String = "",
    val videos: Any = "", // Kept flexible to handle different types
    val hours: String = "",
    val about: String = "",
    val difficultyLevel: String = "",
    val certification: Boolean = false,
    val language: String = "",
    val mentorIds: List<Int> = emptyList(),
    val mentorName: String = "", // Added field for mentor name
    val creatorId: String = "", // Added field for creator ID
    val features: List<String> = emptyList(),
    val playlistVideos: List<VideoDetails> = emptyList(),
    var isBookmarked: Boolean = false,
    val docId: String = "" // New property to store the Firestore document ID
) {
    // Conversion method to handle different video field types
    fun getVideosAsString(): String {
        return when (videos) {
            is String -> videos
            is Number -> videos.toString()
            else -> ""
        }
    }
}


data class Review(
    val name: String,
    val review: String,
    val likes: String,
    val timeAgo: String
)

data class VideoItem(
    val videoId: String,
    val title: String,
    val description: String,
    val thumbnailUrl: String
)

class CourseRepository {
    private val db = FirebaseFirestore.getInstance()
    private val coursesCollection = db.collection("courses")

    suspend fun getCourses(): List<CourseModel> {
        return try {
            val snapshot = coursesCollection.get().await()
            snapshot.documents.mapNotNull { it.toObject(CourseModel::class.java) }
        } catch (e: Exception) {
            emptyList() // Return empty list if there's an error
        }
    }
}

suspend fun fetchYoutubePlaylistData(playlistLink: String): List<VideoDetails> = withContext(Dispatchers.IO) {
    // 1) Extract the playlist ID (using substringAfter and substringBefore to handle extra params)
    val playlistId = playlistLink.substringAfter("list=", "").substringBefore("&")
    if (playlistId.isEmpty()) return@withContext emptyList<VideoDetails>()

    // 2) Build the API URL (replace with your actual API key)
    val apiUrl = "https://www.googleapis.com/youtube/v3/playlistItems" +
            "?part=snippet,contentDetails" +
            "&maxResults=50" +
            "&playlistId=$playlistId" +
            "&key=AIzaSyBRsaVZjMmGbdmUpswzHYp1XeiuFnl1vOE"

    // 3) Make the network call using OkHttp
    val client = OkHttpClient()
    val request = Request.Builder().url(apiUrl).build()
    val response = client.newCall(request).execute()
    val jsonString = response.body?.string().orEmpty()

    // 4) Parse the JSON
    val jsonObj = JSONObject(jsonString)
    val itemsArray = jsonObj.optJSONArray("items") ?: return@withContext emptyList<VideoDetails>()

    val videoList = mutableListOf<VideoDetails>()
    for (i in 0 until itemsArray.length()) {
        val item = itemsArray.getJSONObject(i)
        val snippet = item.optJSONObject("snippet")
        val contentDetails = item.optJSONObject("contentDetails")

        val title = snippet?.optString("title").orEmpty()
        val videoId = contentDetails?.optString("videoId").orEmpty()
        val durationIso = contentDetails?.optString("duration").orEmpty()

        // Build a video URL from the videoId
        val videoUrl = "https://youtube.com/watch?v=$videoId"

        // For now, we store the raw ISO 8601 duration string.
        val duration = durationIso

        videoList.add(VideoDetails(title, duration, videoUrl))
    }

    return@withContext videoList
}
