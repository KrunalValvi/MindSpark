package com.example.mindspark.auth.ui.security

import android.widget.Toast
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
import com.example.mindspark.backend.updateUserPin
import com.example.mindspark.auth.components.AuthButton
import com.example.mindspark.auth.components.AuthTopBar
import com.example.mindspark.auth.components.OTPInputFields
import com.example.mindspark.ui.theme.customTypography

private val LightBlueBackground = Color(0xFFF5F9FF)

@Composable
fun CreatePinScreen(navController: NavController) {

    val context = LocalContext.current

    Scaffold(
        modifier = Modifier.background(LightBlueBackground),
        containerColor = LightBlueBackground,
        topBar = {
            AuthTopBar(
                title = "Create New Pin",
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
                .padding(bottom = 300.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Add a Pin Number to Make Your Account\nmore Secure",
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

            Spacer(modifier = Modifier.height(60.dp))

            AuthButton(
                text = "Continue",
                onClick = {
                    // Combine the four digits into a single PIN string.
                    val pin = pinValues.joinToString("")
                    if (pin.length == 4) {
                        updateUserPin(pin,
                            onSuccess = {
                                Toast.makeText(context, "PIN set successfully", Toast.LENGTH_SHORT).show()
                                navController.navigate("SetFingerprintScreen")
                            },
                            onFailure = { errorMessage ->
                                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                            }
                        )
                    } else {
                        Toast.makeText(context, "Please enter a 4-digit PIN", Toast.LENGTH_SHORT).show()
                    }
                }
            )



        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreatePinScreenPreview() {
    CreatePinScreen(navController = NavController(LocalContext.current))
}