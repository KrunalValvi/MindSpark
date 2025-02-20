package com.example.mindspark.auth.ui.register

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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.mindspark.auth.network.AuthResponse
import com.example.mindspark.auth.network.AuthenticationManager
import com.example.mindspark.ui.theme.customTypography
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun RegisterScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isTermsAccepted by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val authenticationManager = remember { AuthenticationManager(context) }
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier.background(Color(0xFFF5F9FF))) {
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
                    text = "Getting Started.!",
                    style = MaterialTheme.customTypography.jost.semiBold,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    fontSize = 24.sp
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    text = "Create an Account to Continue your allCourses",
                    style = MaterialTheme.customTypography.mulish.bold,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(30.dp))

                AuthTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = "Email",
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_mail),
                            contentDescription = "Email Icon",
                            modifier = Modifier
                                .size(24.dp)
                                .padding(start = 4.dp)
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )

                AuthTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = "Password",
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_lock),
                            contentDescription = "Password Icon",
                            modifier = Modifier
                                .size(24.dp)
                                .padding(start = 4.dp)
                        )
                    },
                    isPasswordField = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = PasswordVisualTransformation()
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 1.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = isTermsAccepted,
                        onClick = { isTermsAccepted = !isTermsAccepted }
                    )
                    Text(
                        text = "Agree to Terms & Conditions",
                        style = MaterialTheme.customTypography.mulish.extraBold,
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                AuthButton(
                    text = "Sign Up",
                    onClick = {
                        authenticationManager.createAccountWithEmail(email, password)
                            .onEach { response ->
                                when (response) {
                                    is AuthResponse.Success -> {
                                        navController.navigate("FillProfileScreen")
                                    }
                                    is AuthResponse.Error -> {
                                        Toast.makeText(
                                            context,
                                            response.message,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }
                            .launchIn(coroutineScope)
                    }
                )

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
                                authenticationManager.signInWithGoogle(context)
                                    .onEach { response ->
                                        if (response is AuthResponse.Success) {
                                            navController.navigate("FillProfileScreen")
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
                        text = "Already have an Account? ",
                        style = MaterialTheme.customTypography.mulish.regular,
                        fontSize = 14.sp
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                                append("SIGN IN")
                            }
                        },
                        color = Color.Blue,
                        style = MaterialTheme.customTypography.mulish.semiBold,
                        fontSize = 14.sp,
                        modifier = Modifier.clickable { navController.navigate("LoginScreen") }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(navController = NavController(LocalContext.current))
}
