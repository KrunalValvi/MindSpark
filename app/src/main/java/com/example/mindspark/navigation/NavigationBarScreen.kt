package com.example.mindspark.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.mindspark.ui.theme.customTypography

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.MyCourses,
        BottomNavItem.Inbox,
        BottomNavItem.Transactions,
        BottomNavItem.Profile
    )

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp),
        containerColor = Color(0xFFF5F9FF),
//        containerColor = Color.Black,
        tonalElevation = 0.dp
    ) { // Background color of navbar
        val currentBackStackEntry = navController.currentBackStackEntryAsState()
        val currentRoute = currentBackStackEntry.value?.destination?.route

        items.forEach { item ->
            val isSelected = currentRoute == item.route

            NavigationBarItem(
                icon = {
                    Image(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.label,
                        colorFilter = if (isSelected) ColorFilter.tint(Color(0xFF008060)) else ColorFilter.tint(Color(0xFF1E1E1E)) // Change colors here
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        style = MaterialTheme.customTypography.mulish.bold,
                        fontSize = 9.sp,
                        color = if (isSelected) Color(0xFF008060) else Color(0xFF1E1E1E) // Change text color on selection
                    )
                },
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

