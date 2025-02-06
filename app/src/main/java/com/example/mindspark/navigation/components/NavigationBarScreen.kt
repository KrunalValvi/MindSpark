package com.example.mindspark.navigation.components

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
import com.example.mindspark.navigation.model.BottomNavItem
import com.example.mindspark.ui.theme.customTypography

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.MyCourses,
        BottomNavItem.Inbox,
        BottomNavItem.BookMark,
        BottomNavItem.Profile
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    fun isRouteInCategory(route: String?, category: BottomNavItem): Boolean {
        return when (category) {
            BottomNavItem.Home -> {
                route == "HomeScreen" ||
                        route?.startsWith("CourseDetailScreen") == true ||
                        route == "SearchScreen" ||
                        route?.startsWith("SingleMentorDetails") == true ||
                        route == "PopularCoursesList"
            }
            BottomNavItem.MyCourses -> {
                route == "CoursesListScreen" ||
                        route == "TopMentorScreen"
            }
            BottomNavItem.Inbox -> route == "InboxScreen"
            BottomNavItem.BookMark -> route == "BookMarkScreen"
            BottomNavItem.Profile -> {
                route == "ProfileScreen" ||
                        route == "TermsScreen"
            }
        }
    }

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp),
        containerColor = Color(0xFFF5F9FF),
        tonalElevation = 0.dp
    ) {
        items.forEach { item ->
            val isSelected = isRouteInCategory(currentRoute, item)

            NavigationBarItem(
                icon = {
                    Image(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.label,
                        colorFilter = if (isSelected) ColorFilter.tint(Color(0xFF008060)) else ColorFilter.tint(Color(0xFF1E1E1E))
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        style = MaterialTheme.customTypography.mulish.bold,
                        fontSize = 9.sp,
                        color = if (isSelected) Color(0xFF008060) else Color(0xFF1E1E1E)
                    )
                },
                selected = isSelected,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        // Remove the 'if' condition to allow reselection
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent,
                    selectedIconColor = Color(0xFF008060),
                    unselectedIconColor = Color(0xFF1E1E1E)
                )
            )
        }
    }
}