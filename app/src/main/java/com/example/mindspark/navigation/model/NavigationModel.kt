package com.example.mindspark.navigation.model

import androidx.annotation.DrawableRes
import com.example.mindspark.R

sealed class BottomNavItem(val route: String, val label: String, @DrawableRes val icon: Int) {
    object Home : BottomNavItem("HomeScreen", "Home", R.drawable.ic_home)
    object MyCourses : BottomNavItem("CoursesListScreen", "Courses", R.drawable.ic_courses)
    object MentorCourses : BottomNavItem("MentorScreen", "Mentor", R.drawable.ic_courses) // New Mentor-Specific Screen
    object Inbox : BottomNavItem("InboxScreen", "Inbox", R.drawable.ic_inbox)
    object Community : BottomNavItem("CommunityScreen", "Community", R.drawable.ic_transaction)
    object Profile : BottomNavItem("ProfileScreen", "Profile", R.drawable.ic_profile)
}
