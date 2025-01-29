package com.example.mindspark.courses.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mindspark.auth.components.AuthButton
import com.example.mindspark.auth.components.AuthTopBar
import com.example.mindspark.courses.components.FilterCategoryItem
import com.example.mindspark.courses.data.FilterData
import com.example.mindspark.courses.model.FilterCategory

private val LightBlueBackground = Color(0xFFF5F9FF)

@Composable
fun CoursesFilterScreen(navController: NavController) {

    val filters = remember { mutableStateListOf<FilterCategory>().apply { addAll(FilterData.getFilterOptions()) } }


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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LightBlueBackground)
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Filter List
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(filters) { category ->
                    FilterCategoryItem(category)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Apply Button
            AuthButton(
                text = "Apply",
                onClick = { navController.navigate("MentorListScreen") }
            )

        }


    }
}

