package com.example.mindspark.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mindspark.home.ui.HomeScreen
import com.example.mindspark.profile.ui.ProfileScreen
import com.example.mindspark.courses.ui.CoursesListScreen
import com.example.mindspark.inbox.ui.InboxScreen
import com.example.mindspark.inbox.ui.ActiveCallScreen
import com.example.mindspark.inbox.ui.ChatDetailScreen
import com.example.mindspark.courses.ui.CourseDetailScreen
import com.example.mindspark.courses.ui.CoursesFilterScreen
import com.example.mindspark.transactions.ui.TransactionsScreen
import com.example.mindspark.courses.ui.PopularCoursesList
import com.example.mindspark.courses.ui.SingleMentorDetails
import com.example.mindspark.courses.ui.TopMentorScreen
import com.example.mindspark.myCourses.data.sampleData
import com.example.mindspark.myCourses.ui.CertificateScreen
import com.example.mindspark.myCourses.ui.MyCourseCompleted
import com.example.mindspark.myCourses.ui.MyLessons
import com.example.mindspark.myCourses.ui.MyOngoingLessons
import com.example.mindspark.notifications.ui.NotificationsScreen
import com.example.mindspark.payment.ui.PaymentScreen
import com.example.mindspark.profile.ui.sections.AddNewCardScreen
import com.example.mindspark.profile.ui.sections.EditProfileScreen
import com.example.mindspark.profile.ui.sections.HelpCenterScreen
import com.example.mindspark.profile.ui.sections.InviteFriendsScreen
import com.example.mindspark.profile.ui.sections.LanguageScreen
import com.example.mindspark.profile.ui.sections.PaymentOptionScreen
import com.example.mindspark.profile.ui.sections.ProfileNotificationsScreen
import com.example.mindspark.profile.ui.sections.SecurityScreen
import com.example.mindspark.profile.ui.sections.TermsScreen
import com.example.mindspark.transactions.ui.EReceiptScreen

fun NavGraphBuilder.UserNavigation(navController: NavController) {
    composable(
        "HomeScreen",
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn(tween(300)) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(tween(300)) }
    ) { HomeScreen(navController) }

    composable(
        "ProfileScreen",
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn(tween(300)) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(tween(300)) }
    ) { ProfileScreen(navController) }

    composable(
        "CoursesListScreen",
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn(tween(300)) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(tween(300)) }
    ) { CoursesListScreen(navController) }

    composable(
        "InboxScreen",
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn(tween(300)) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(tween(300)) }
    ) { InboxScreen(navController) }

    composable(
        "TransactionsScreen",
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn(tween(300)) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(tween(300)) }
    ) { TransactionsScreen(navController) }

    composable(
        "PopularCoursesList",
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn(tween(300)) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(tween(300)) }
    ) { PopularCoursesList(navController) }

    composable(
        "TopMentorScreen",
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn(tween(300)) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(tween(300)) }
    ) { TopMentorScreen(navController) }

    composable(
        "NotificationsScreen",
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn(tween(300)) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(tween(300)) }
    ) { NotificationsScreen(navController) }

    composable(
        "TermsScreen",
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn(tween(300)) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(tween(300)) }
    ) { TermsScreen(navController) }

    composable(
        "SecurityScreen",
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn(tween(300)) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(tween(300)) }
    ) { SecurityScreen(navController) }

    composable(
        "EditProfileScreen",
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn(tween(300)) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(tween(300)) }
    ) { EditProfileScreen(navController) }

    composable(
        "ProfileNotificationsScreen",
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn(tween(300)) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(tween(300)) }
    ) { ProfileNotificationsScreen(navController) }

    composable(
        "PaymentOptionScreen",
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn(tween(300)) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(tween(300)) }
    ) { PaymentOptionScreen(navController) }

    composable(
        "HelpCenterScreen",
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn(tween(300)) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(tween(300)) }
    ) { HelpCenterScreen(navController) }

    composable(
        "InviteFriendsScreen",
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn(tween(300)) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(tween(300)) }
    ) { InviteFriendsScreen(navController) }

    composable(
        "PaymentScreen",
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn(tween(300)) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(tween(300)) }
    ) { PaymentScreen(navController) }

    composable(
        "AddNewCardScreen",
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn(tween(300)) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(tween(300)) }
    ) { AddNewCardScreen(navController) }

    composable(
        "EReceiptScreen",
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn(tween(300)) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(tween(300)) }
    ) { EReceiptScreen(navController) }

    composable(
        "LanguageScreen",
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn(tween(300)) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(tween(300)) }
    ) { LanguageScreen(navController) }

    composable(
        "MyCourseCompleted",
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn(tween(300)) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(tween(300)) }
    ) { MyCourseCompleted(navController) }

    composable(
        "MyLessons",
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn(tween(300)) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(tween(300)) }
    ) { MyLessons(navController) }

     composable(
         "MyOngoingLessons",
         enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn(tween(300)) },
         exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(tween(300)) }
        ) { MyOngoingLessons(navController) }

    composable(
        "CoursesFilterScreen",
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn(tween(300)) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(tween(300)) }
    ) { CoursesFilterScreen(navController) }

    composable(
         "CertificateScreen",
         enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn(tween(300)) },
         exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(tween(300)) }
        ) { CertificateScreen(
            navController = navController,
            certificateData = sampleData
    ) }

    composable(
        "CourseDetailScreen/{courseId}",
        arguments = listOf(navArgument("courseId") { type = NavType.IntType }),
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn(tween(300)) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(tween(300)) }
    ) { backStackEntry ->
        val courseId = backStackEntry.arguments?.getInt("courseId") ?: 0
        CourseDetailScreen(navController, courseId)
    }

    composable(
        "SingleMentorDetails/{mentorId}",
        arguments = listOf(navArgument("mentorId") { type = NavType.IntType }),
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn(tween(300)) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(tween(300)) }
    ) { backStackEntry ->
        val mentorId = backStackEntry.arguments?.getInt("mentorId") ?: 0
        SingleMentorDetails(navController, mentorId)
    }

    composable(
        "OnlineCourses/{section}",
        arguments = listOf(
            navArgument("section") {
                type = NavType.StringType
                defaultValue = "courses"
            }
        ),
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn(tween(300)) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(tween(300)) }
    ) { backStackEntry ->
        val section = backStackEntry.arguments?.getString("section") ?: "courses"
        CoursesListScreen(
            navController = navController,
            startWithMentors = section == "mentors"
        )
    }

    composable(
        "ChatDetailScreen/{chatId}",
//        arguments = listOf(navArgument("chatId") { type = NavType.StringType }),
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn(tween(300)) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(tween(300)) }
    ) { ChatDetailScreen(navController = navController) }

    composable(
        "ActiveCallScreen/{callId}",
//        arguments = listOf(navArgument("callId") { type = NavType.StringType }),
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn(tween(300)) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(tween(300)) }
    ) { ActiveCallScreen(navController = navController) }
}
