package com.example.mindspark.profile.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mindspark.auth.components.AuthTopBar
import com.example.mindspark.profile.components.ProfileHeader
import com.example.mindspark.profile.components.SettingsList
import com.example.mindspark.ui.theme.LightBlueBackground
import com.example.mindspark.ui.theme.customTypography
import org.w3c.dom.Text

@Composable
fun TermsScreen(navController: NavController) {

    Scaffold(
        modifier = Modifier.background(LightBlueBackground),
        containerColor = LightBlueBackground,
        topBar = {
            AuthTopBar(
                title = "Terms & Conditions",
                onBackClick = { navController.navigateUp() }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LightBlueBackground)
                .padding(padding)
                .padding(18.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start
        ) {

//            // Commercial use
//            //Section 1
//            Text(
//                text = "Condition & Attending",
//                style = MaterialTheme.customTypography.jost.semiBold,
//                fontSize = 20.sp
//            )
//            Spacer(modifier = Modifier.height(10.dp))
//            Text(
//                text = "To use MindSpark, users must be at least 13 years old. Creating an account requires providing accurate information, and any false details or misuse may lead to suspension. Access to courses and materials is strictly for registered users, and sharing accounts, plagiarizing content, or engaging in unethical behavior is prohibited.\n" +
//                        "\n" +
//                        "All users are expected to follow community guidelines and maintain respectful communication. Any form of harassment, abuse, or disruptive behavior will result in immediate removal from the platform. MindSpark reserves the right to monitor activities to ensure a safe and productive learning environment.",
//                style = MaterialTheme.customTypography.mulish.bold,
//                fontSize = 15.sp
//            )
//            Spacer(modifier = Modifier.height(20.dp))
//            //Section 2
//            Text(
//                text = "Terms & Use",
//                style = MaterialTheme.customTypography.jost.semiBold,
//                fontSize = 20.sp
//            )
//            Spacer(modifier = Modifier.height(10.dp))
//            Text(
//                text = "MindSpark provides learning resources for personal educational purposes only. Users are not allowed to copy, resell, or distribute any course materials without permission. The app integrates third-party services like YouTube and Firebase, and users must comply with their respective terms and policies when accessing related content.\n" +
//                        "\n" +
//                        "Any attempts to hack, manipulate, or exploit the app’s features will lead to legal consequences. MindSpark may update its terms at any time, and continued use of the platform implies acceptance of the latest terms. Users are responsible for staying informed about any changes to ensure compliance.",
//                style = MaterialTheme.customTypography.mulish.bold,
//                fontSize = 15.sp
//            )

            // Fun use
            //Section 1
            Text(
                text = "Condition & Attending",
                style = MaterialTheme.customTypography.jost.semiBold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Alright, listen up, future genius. By using this app, you agree that if anything breaks, it's your fault. Yes, even if it's clearly our mistake—congratulations, you just unlocked a new \"deal with it\" feature. \n" +
                        "\n" +
                        "This app comes with zero guarantees except that if you forget your password for the 100th time, we’ll personally send you a certificate for \"Professional Password Forgetter.\" If the app crashes, you have two options: scream into the void or blame your internet (we recommend both for maximum effectiveness). If that still doesn’t work, try turning your phone off and on… and if that fails, well, we believe in you. Kinda.",
                style = MaterialTheme.customTypography.mulish.bold,
                fontSize = 15.sp
            )
            Spacer(modifier = Modifier.height(20.dp))
            //Section 2
            Text(
                text = "Terms & Use",
                style = MaterialTheme.customTypography.jost.semiBold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "All the amazing content in MindSpark is for learning, not for making a quick buck on the internet. If you try to resell or leak stuff, we won’t send lawyers, but we will judge you silently. The app runs on Firebase and YouTube—if they break, blame them, not us.\n" +
                        "\n" +
                        "If something in the app doesn’t work as expected, feel free to complain… to yourself. Fix it? Probably not. Live with it? Highly recommended. We might update these terms whenever we feel like it, and it’s your job to keep up. If you don’t like the changes, well, you always have the option to uninstall (but we know you won’t).",
                style = MaterialTheme.customTypography.mulish.bold,
                fontSize = 15.sp
            )


        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview2() {
    TermsScreen(navController = NavController(LocalContext.current))
}