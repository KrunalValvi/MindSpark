package com.example.mindspark.navigation

import androidx.annotation.DrawableRes
import com.example.mindspark.R

data class IconModel(val id: Int, @DrawableRes val icon: Int)

sealed class BottomNavItem(val route: String, val label: String, @DrawableRes val icon: Int) {
    object Home : BottomNavItem("HomeScreen", "Home", R.drawable.ic_home)
    object MyCourses : BottomNavItem("CoursesListScreen", "My Courses", R.drawable.ic_courses)
    object Inbox : BottomNavItem("InboxScreen", "Inbox", R.drawable.ic_inbox)
    object Transactions : BottomNavItem("TransactionsScreen", "Transaction", R.drawable.ic_transaction)
    object Profile : BottomNavItem("ProfileScreen", "Profile", R.drawable.ic_profile)
}