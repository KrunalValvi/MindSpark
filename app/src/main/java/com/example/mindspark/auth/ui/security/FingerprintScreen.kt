package com.example.mindspark.auth.ui.security

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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.example.mindspark.R
import com.example.mindspark.auth.components.AuthTopBar
import com.example.mindspark.ui.theme.customTypography
import kotlinx.coroutines.delay

private val LightBlueBackground = Color(0xFFF5F9FF)

@Composable
fun SetFingerprintScreen(navController: NavController) {
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.background(LightBlueBackground),
        containerColor = LightBlueBackground,
        topBar = {
            AuthTopBar(
                title = "Set Your Fingerprint",
                onBackClick = { navController.navigateUp() }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LightBlueBackground)
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(50.dp))

            Text(
                text = "Add a Fingerprint to Make your Account\nmore Secure",
                textAlign = TextAlign.Center,
                style = MaterialTheme.customTypography.mulish.bold,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(50.dp))

            Image(
                painter = painterResource(R.drawable.image_fingerprint),
                contentDescription = "Fingerprint",
                modifier = Modifier.size(250.dp)
            )

            Spacer(modifier = Modifier.height(50.dp))

            Text(
                text = "Please Put Your Finger on the Fingerprint\nScanner to get Started,",
                textAlign = TextAlign.Center,
                style = MaterialTheme.customTypography.mulish.bold,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(60.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Skip Button
                Box(
                    modifier = Modifier
                        .weight(0.6f)
                        .height(56.dp)
                        .clip(MaterialTheme.shapes.extraLarge)
                        .background(Color(0xFFE8F1FF))
//                        .clickable { showDialog = true },
                        .clickable { navController.navigate("ForgotPasswordScreen") },
                    contentAlignment = Alignment.Center,
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 36.dp)
                    ) {
                        Text(
                            text = "Skip",
                            color = Color.Black,
                            style = MaterialTheme.customTypography.jost.semiBold,
                            fontSize = 18.sp,
                        )
                    }

                }

                // Continue Button
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                        .clip(MaterialTheme.shapes.extraLarge)
                        .background(Color(0xFF1565C0))
                        .clickable { showDialog = true },
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 7.dp)
                    ) {
                        Text(
                            modifier = Modifier.padding(start = 20.dp),
                            text = "Continue",
                            color = Color.White,
                            fontSize = 18.sp,
                            style = MaterialTheme.customTypography.jost.semiBold
                        )

                        Box(
                            modifier = Modifier
                                .size(45.dp)
                                .clip(CircleShape)
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = "Continue",
                                tint = Color(0xFF1565C0),
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }

        }
        if (showDialog) {
            AutoDismissDialog(
                onDismiss = { showDialog = false },
                onTimeout = { navController.navigate("HomeScreen") }
            )
        }
    }
}

@Composable
fun AutoDismissDialog(
    onDismiss: () -> Unit,
    onTimeout: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(1000) // Wait for 3 seconds
        onDismiss()
        onTimeout()
    }

    Dialog(
        onDismissRequest = { },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {

        Surface(
            modifier = Modifier
                .width(300.dp)
                .wrapContentHeight(),
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
        ) {

            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.c1),
                    contentDescription = null,
                    Modifier.size(100.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Congratulations",
                    style = MaterialTheme.customTypography.jost.semiBold,
                    fontSize = 24.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Your Account is Ready to use. You will be\n" +
                            "redirected to the Home page in a Few\n" +
                            "Seconds.",
                    style = MaterialTheme.customTypography.mulish.bold,
                    fontSize = 14.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))

                Image(
                    painter = painterResource(R.drawable.ic_loading),
                    contentDescription = null,
                    modifier =  Modifier.size(40.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AutoDismissDialogPreview() {
    AutoDismissDialog(onDismiss = {}, onTimeout = {})
}

@Preview(showBackground = true)
@Composable
fun SetFingerprintScreenPreview() {
    SetFingerprintScreen(navController = NavController(LocalContext.current))
}