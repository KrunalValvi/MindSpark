package com.example.mindspark.courses.network

import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import com.example.mindspark.courses.model.VideoItem

fun fetchYouTubePlaylists(
    playlistId: String,
    apiKey: String,
    onResult: (List<VideoItem>) -> Unit,
    onError: (Throwable?) -> Unit
) {
    val client = AsyncHttpClient()
    val url = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet" +
            "&playlistId=$playlistId&key=$apiKey&maxResults=50"

    client.get(url, object : JsonHttpResponseHandler() {
        override fun onSuccess(statusCode: Int, headers: Array<Header>?, response: JSONObject?) {
            val videoList = mutableListOf<VideoItem>()
            response?.let {
                val itemsArray = it.getJSONArray("items")
                for (i in 0 until itemsArray.length()) {
                    val item = itemsArray.getJSONObject(i)
                    val snippet = item.getJSONObject("snippet")
                    val title = snippet.getString("title")
                    val description = snippet.getString("description")
                    val thumbnails = snippet.getJSONObject("thumbnails")
                    val mediumThumb = thumbnails.getJSONObject("medium")
                    val thumbnailUrl = mediumThumb.getString("url")
                    val resourceId = snippet.getJSONObject("resourceId")
                    val videoId = resourceId.getString("videoId")
                    videoList.add(VideoItem(videoId, title, description, thumbnailUrl))
                }
            }
            onResult(videoList)
        }

        override fun onFailure(statusCode: Int, headers: Array<Header>?, throwable: Throwable?, errorResponse: JSONObject?) {
            onError(throwable)
        }
    })
}
