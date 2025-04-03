package com.example.mindspark.auth.ui.login

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mindspark.R
import com.example.mindspark.auth.components.AuthButton
import com.example.mindspark.auth.components.AuthTextField
import com.example.mindspark.backend.AuthResponse
import com.example.mindspark.backend.AuthenticationManager
import com.example.mindspark.backend.checkUserProfileExists
import com.example.mindspark.ui.theme.customTypography
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

// Helper function to check network connectivity.
fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return connectivityManager.activeNetworkInfo?.isConnectedOrConnecting == true
}

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isTermsAccepted by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    // Add single error message state
    var errorMessage by remember { mutableStateOf("") }

    val context = LocalContext.current
    val authenticationManager = remember { AuthenticationManager(context) }
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier.background(Color(0xFFF5F9FF))) {
        // Main content
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp)
                .padding(top = 50.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(R.drawable.logo_authentication),
                    contentDescription = "Auth Logo"
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 61.dp),
                    text = "Let's Sign In!",
                    style = MaterialTheme.customTypography.jost.semiBold,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    fontSize = 24.sp
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    color = Color(0xFF545454),
                    text = "Login to Your Account to Continue your Courses",
                    style = MaterialTheme.customTypography.mulish.bold,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(30.dp))

                AuthTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        errorMessage = ""
                    },
                    placeholder = "Email",
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_mail),
                            contentDescription = "Email Icon",
                            tint = Color(0xFF545454),
                            modifier = Modifier
                                .size(24.dp)
                                .padding(start = 4.dp)
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )

                AuthTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        errorMessage = ""
                    },
                    placeholder = "Password",
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_lock),
                            contentDescription = "Password Icon",
                            tint = Color(0xFF545454),
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    isPasswordField = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = PasswordVisualTransformation()
                )

                // Display error message if present
                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        style = MaterialTheme.customTypography.mulish.regular,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp),
                        textAlign = TextAlign.Center
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Checkbox(
                        checked = isTermsAccepted,
                        onCheckedChange = { isTermsAccepted = !isTermsAccepted }
                    )
                    Text(
                        text = "Remember Me",
                        style = MaterialTheme.customTypography.mulish.extraBold,
                        fontSize = 13.sp
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        modifier = Modifier
                            .clickable {
                                navController.navigate("ForgotPasswordScreen")
                            },
                        text = "Forgot Password?",
                        color = Color(0xFF0961F5),
                        style = MaterialTheme.customTypography.mulish.extraBold,
                        fontSize = 13.sp
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                AuthButton(
                    text = "Sign In",
                    onClick = myLabel@{
                        // Validate inputs
                        when {
                            email.trim().isEmpty() -> {
                                errorMessage = "Please enter your email and password"
                                return@myLabel
                            }
                            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                                errorMessage = "Please enter a valid email address"
                                return@myLabel
                            }
                            password.trim().isEmpty() -> {
                                errorMessage = "Please enter your password"
                                return@myLabel
                            }
                            !isNetworkAvailable(context) -> {
                                Toast.makeText(context, "You are offline", Toast.LENGTH_SHORT).show()
                                return@myLabel
                            }
                        }

                        isLoading = true
                        authenticationManager.loginWithEmail(email, password)
                            .onEach { response ->
                                isLoading = false
                                when (response) {
                                    is AuthResponse.Success -> {
                                        navController.navigate("HomeScreen")
                                    }
                                    is AuthResponse.Error -> {
                                        val errorString = response.message
                                        errorMessage = when {
                                            errorString.contains("password", ignoreCase = true) ->
                                                "This password is wrong. You have to add right password"
                                            errorString.contains("user", ignoreCase = true) ||
                                                    errorString.contains("not found", ignoreCase = true) ->
                                                "This email is not valid for login"
                                            else -> errorString
                                        }
                                    }
                                }
                            }
                            .launchIn(coroutineScope)
                    }
                )

                // Rest of the code remains the same
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    modifier = Modifier.padding(top = 14.dp),
                    text = "Or Continue With",
                    style = MaterialTheme.customTypography.mulish.extraBold,
                    fontSize = 14.sp
                )

                Row {
                    Image(
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .clickable {
                                if (!isNetworkAvailable(context)) {
                                    Toast.makeText(context, "You are offline", Toast.LENGTH_SHORT).show()
                                    return@clickable
                                }
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
                                                    errorMessage = error
                                                }
                                            )
                                        } else if (response is AuthResponse.Error) {
                                            isLoading = false
                                            errorMessage = response.message
                                        }
                                    }
                                    .launchIn(coroutineScope)
                            },
                        painter = painterResource(R.drawable.ic_google),
                        contentDescription = "Google"
                    )
                }

                Row(modifier = Modifier.padding(top = 10.dp)) {
                    Text(
                        text = "Don't have an Account? ",
                        style = MaterialTheme.customTypography.mulish.regular,
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
                CircularProgressIndicator(color = White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(navController = NavController(LocalContext.current))
}
