package com.example.mindspark.bookmark.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mindspark.auth.components.AuthTopBar
import com.example.mindspark.bookmark.components.BookMarkList
import com.example.mindspark.bookmark.data.getAllBookMarks
import com.example.mindspark.ui.theme.LightBlueBackground

@Composable
fun BookMarkScreen(navController: NavController) {
    val bookmarks = getAllBookMarks()

    Scaffold(
        modifier = Modifier.background(LightBlueBackground),
        containerColor = LightBlueBackground,
        topBar = {
            AuthTopBar(
                title = "BookMarks",
                onBackClick = { navController.navigateUp() }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LightBlueBackground)
                .padding(padding)
        ) {
            BookMarkList(bookmarks = bookmarks, onCourseClick = { bookmark ->
                // Handle course click
            })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookMarkScreenPreview() {
    BookMarkScreen(navController = NavController(LocalContext.current))
}