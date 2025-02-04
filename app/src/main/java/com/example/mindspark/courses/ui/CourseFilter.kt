package com.example.mindspark.courses.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mindspark.auth.components.AuthButton
import com.example.mindspark.auth.components.AuthTopBar
import com.example.mindspark.auth.ui.login.LoginScreen
import com.example.mindspark.courses.components.FilterCategoryItem
import com.example.mindspark.courses.data.FilterData
import com.example.mindspark.courses.model.FilterCategory

private val LightBlueBackground = Color(0xFFF5F9FF)


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    CoursesFilterScreen(navController = NavController(LocalContext.current))
}


@Composable
fun CoursesFilterScreen(navController: NavController) {
    val filters = remember { mutableStateListOf<FilterCategory>().apply { addAll(FilterData.getFilterOptions()) } }
    val isAtBottom = remember { mutableStateOf(false) }
    val listState = rememberLazyListState()

    LaunchedEffect(listState) {
        snapshotFlow { listState.canScrollForward }
            .collect { canScroll ->
                isAtBottom.value = !canScroll
            }
    }

    Scaffold(
        modifier = Modifier.background(LightBlueBackground),
        containerColor = LightBlueBackground,
        topBar = {
            AuthTopBar(
                title = "Filter",
                onBackClick = { navController.navigateUp() }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(LightBlueBackground)
                .padding(padding)
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                items(filters) { category ->
                    FilterCategoryItem(
                        category = category
                    )
                }

                // Spacer at the bottom to ensure last items are visible above the button
                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }

            // Apply button
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
                    text = "Apply",
                    onClick = {
                        val selectedFilters = filters.flatMap { category ->
                            category.options.filter { it.isSelected }
                        }
                        // Navigate back to OnlineCoursesScreen with parameters
                        navController.navigate("OnlineCourses/mentors") {
                            popUpTo("OnlineCourses") { inclusive = true }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}