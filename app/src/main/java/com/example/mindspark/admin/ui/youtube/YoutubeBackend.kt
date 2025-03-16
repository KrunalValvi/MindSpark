package com.example.mindspark.admin.ui.youtube

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// Data models matching YouTube API response (plus resourceId for videoId)
data class PlaylistResponse(val items: List<PlaylistItem>)
data class PlaylistItem(val id: String, val snippet: Snippet)
data class Snippet(
    val title: String,
    val description: String,
    val thumbnails: Thumbnails,
    val resourceId: ResourceId // contains videoId for each item
)
data class Thumbnails(val medium: Thumbnail)
data class Thumbnail(val url: String)
data class ResourceId(val videoId: String)

// Retrofit interface
interface YouTubeApi {
    @GET("playlistItems")
    suspend fun getPlaylistItems(
        @Query("part") part: String = "snippet",
        @Query("playlistId") playlistId: String,
        @Query("key") apiKey: String,
        @Query("maxResults") maxResults: Int = 50
    ): PlaylistResponse
}

// Retrofit instance
private val retrofit = Retrofit.Builder()
    .baseUrl("https://www.googleapis.com/youtube/v3/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

private val api = retrofit.create(YouTubeApi::class.java)

// Function to fetch playlist items
suspend fun fetchPlaylistItems(playlistId: String, apiKey: String): List<PlaylistItem> {
    return try {
        val response = api.getPlaylistItems(playlistId = playlistId, apiKey = apiKey)
        response.items
    } catch (e: Exception) {
        e.printStackTrace()
        emptyList()
    }
}

// Helper to extract playlist ID from URL
fun extractPlaylistId(url: String): String? {
    val regex = "(?<=list=)[a-zA-Z0-9_-]+".toRegex()
    return regex.find(url)?.value
}
