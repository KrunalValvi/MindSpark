package com.example.mindspark.auth.ui.security

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
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
import com.example.mindspark.auth.components.AuthTextField
import com.example.mindspark.auth.components.AuthTopBar
import com.example.mindspark.ui.theme.customTypography
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun ForgotPasswordScreen(navController: NavController) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.background(Color(0xFFF5F9FF)),
        containerColor = Color(0xFFF5F9FF),
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
                .background(Color(0xFFF5F9FF))
                .padding(padding)
                .padding(16.dp),
//                .padding(bottom = 200.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Enter your E-Mail to reset your password",
                textAlign = TextAlign.Center,
                style = MaterialTheme.customTypography.mulish.bold,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Email text field
            AuthTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = "Email",
                leadingIcon = {
                    androidx.compose.material3.Icon(
                        imageVector = androidx.compose.material.icons.Icons.Default.Email,
                        contentDescription = "Email Icon"
                    )
                },
                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Email)
            )
            Spacer(modifier = Modifier.height(16.dp))
            AuthButton(
                text = "Continue",
                onClick = {
                    if(email.isNotBlank()){
                        Firebase.auth.sendPasswordResetEmail(email)
                            .addOnCompleteListener { task ->
                                if(task.isSuccessful){
                                    Toast.makeText(context, "Password reset email sent", Toast.LENGTH_SHORT).show()
                                    navController.navigate("LoginScreen")
                                } else {
                                    Toast.makeText(context, task.exception?.message ?: "Error sending reset email", Toast.LENGTH_SHORT).show()
                                }
                            }
                    } else {
                        Toast.makeText(context, "Please enter your email", Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun ForgotPasswordScreenPreview() {
    ForgotPasswordScreen(navController = NavController(LocalContext.current))
}
