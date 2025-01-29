package com.example.mindspark.auth.ui.security

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mindspark.auth.components.AuthButton
import com.example.mindspark.auth.components.AuthTopBar
import com.example.mindspark.auth.components.OTPInputFields
import com.example.mindspark.ui.theme.customTypography

private val LightBlueBackground = Color(0xFFF5F9FF)

@Composable
fun VerifyForgotPasswordScreen(navController: NavController){
    Scaffold(
        modifier = Modifier.background(LightBlueBackground),
        containerColor = LightBlueBackground,
        topBar = {
            AuthTopBar(
                title = "Forgot Password",
                onBackClick = { navController.navigateUp() }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LightBlueBackground)
                .padding(padding)
                .padding(16.dp)
                .padding(bottom = 250.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Code has been Send to ( +1 ) *",
                textAlign = TextAlign.Center,
                style = MaterialTheme.customTypography.mulish.bold,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(60.dp))

            val pinValues = remember { mutableStateListOf("", "", "", "") }

            OTPInputFields(
                otpLength = 4,
                otpValues = pinValues,
                onUpdateOtpValuesByIndex = { index, value -> pinValues[index] = value },
                onOtpInputComplete = { /* Handle OTP input complete */ }
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Resend Code in 59s",
                textAlign = TextAlign.Center,
                style = MaterialTheme.customTypography.mulish.bold,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(40.dp))

            AuthButton(
                text = "Verify",
                onClick = { navController.navigate("CreateNewPassword") }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScreenPreview() {
    VerifyForgotPasswordScreen(navController = NavController(LocalContext.current))
}