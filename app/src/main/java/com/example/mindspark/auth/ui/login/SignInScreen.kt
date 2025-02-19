package com.example.mindspark.auth.ui.login

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mindspark.R
import com.example.mindspark.auth.components.AuthButton
import com.example.mindspark.auth.network.AuthResponse
import com.example.mindspark.auth.network.AuthenticationManager
import com.example.mindspark.ui.theme.customTypography
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun SignInScreen(navController: NavController) {

    val context = LocalContext.current
    val authenticationManager = remember {
        AuthenticationManager(context)
    }
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier.background(Color(0xFFF5F9FF))) {
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
                            authenticationManager.signInWithGoogle(context)
                                .onEach { responce ->
                                    if (responce is AuthResponse.Success) {

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
                        contentDescription = "LaunchScreen Icon",
                        modifier = Modifier
                            .size(36.dp)
                            .shadow(4.dp, shape = CircleShape)
                            .clickable{ },

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
                    fontWeight = FontWeight.Light,
                    color = Color.Black,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                AuthButton(
                    text = "Sign In with Your Account",
                    onClick = { navController.navigate("LoginScreen") }
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row {
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
    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    SignInScreen(navController = NavController(LocalContext.current))
}