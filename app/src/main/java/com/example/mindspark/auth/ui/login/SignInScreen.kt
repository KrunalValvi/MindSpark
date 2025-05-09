package com.example.mindspark.auth.ui.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mindspark.R
import com.example.mindspark.backend.checkUserProfileExists
import com.example.mindspark.auth.components.AuthButton
import com.example.mindspark.backend.AuthResponse
import com.example.mindspark.backend.AuthenticationManager
import com.example.mindspark.ui.theme.customTypography
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun SignInScreen(navController: NavController) {
    val context = LocalContext.current
    val authenticationManager = remember { AuthenticationManager(context) }
    val coroutineScope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(false) }

    Box(modifier = Modifier.background(Color(0xFFF5F9FF))) {
        // Main content
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 200.dp)
            ) {
                Text(
                    text = "Let's you in",
                    style = MaterialTheme.customTypography.jost.semiBold,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.medium)
                        .clickable {
                            isLoading = true
                            authenticationManager.signInWithGoogle(context)
                                .onEach { response ->
                                    if (response is AuthResponse.Success) {
                                        // After a successful Google sign in, check if the user's profile exists.
                                        checkUserProfileExists(
                                            onResult = { exists ->
                                                isLoading = false
                                                if (exists) {
                                                    navController.navigate("HomeScreen")
                                                } else {
                                                    navController.navigate("FillProfileScreen")
                                                }
                                            },
                                            onError = { error ->
                                                isLoading = false
                                                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                                            }
                                        )
                                    } else if (response is AuthResponse.Error) {
                                        isLoading = false
                                        Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
                                    }
                                }
                                .launchIn(coroutineScope)
                        }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_google),
                        contentDescription = "Google Icon",
                        modifier = Modifier
                            .size(36.dp)
                            .shadow(4.dp, shape = CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Continue with Google",
                        style = MaterialTheme.customTypography.mulish.extraBold,
                        fontSize = 16.sp
                    )
                }

                Text(
                    text = "(Or)",
                    fontSize = 15.sp,
                    style = MaterialTheme.customTypography.mulish.extraBold,
                    color = Color.Gray,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))

                // Option to sign in with email/password if needed
                AuthButton(
                    text = "Sign In with Your Account",
                    onClick = { navController.navigate("LoginScreen") }
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row {
                    Text(
                        text = "Don't have an Account? ",
                        style = MaterialTheme.customTypography.mulish.semiBold,
                        fontSize = 14.sp
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                                append("SIGN UP")
                            }
                        },
                        color = Color.Blue,
                        style = MaterialTheme.customTypography.mulish.semiBold,
                        fontSize = 14.sp,
                        modifier = Modifier.clickable { navController.navigate("RegisterScreen") }
                    )
                }
            }
        }
        // Loading overlay
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    SignInScreen(navController = NavController(LocalContext.current))
}
