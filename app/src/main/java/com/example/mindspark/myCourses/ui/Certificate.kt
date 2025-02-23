package com.example.mindspark.myCourses.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mindspark.R
import com.example.mindspark.auth.components.AuthTopBar
import com.example.mindspark.myCourses.data.sampleData
import com.example.mindspark.myCourses.model.CertificateData
import com.example.mindspark.ui.theme.customTypography

private val LightBlueBackground = Color(0xFFF5F9FF)

@Composable
fun CertificateScreen(
    navController: NavController,
    certificateData: CertificateData,
) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        visible = true
    }
    val alphaAnim by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        label = "Certificate Animation"
    )

    Scaffold(
        topBar = {
            AuthTopBar(
                title = "My Courses",
                onBackClick = { navController.navigateUp() }
            )
        },
        containerColor = LightBlueBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LightBlueBackground)
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CertificateCard(certificateData = certificateData, alpha = alphaAnim)
        }
    }
}

@Composable
private fun CertificateCard(
    certificateData: CertificateData,
    alpha: Float
) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
//            .graphicsLayer { this.alpha = alpha }
            .animateContentSize(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
//        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            // Decorative canvas background for a subtle artistic touch
//            Canvas(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(200.dp)
//            ) {
//                drawCircle(
//                    brush = Brush.radialGradient(
//                        colors = listOf(
//                            Color(0xFFa18cd1).copy(alpha = 0.2f),
//                            Color.Transparent
//                        ),
//                        center = Offset(x = size.width * 0.8f, y = size.height * 0.2f),
//                        radius = size.minDimension / 3
//                    ),
//                    center = Offset(x = size.width * 0.8f, y = size.height * 0.2f),
//                    radius = size.minDimension / 3
//                )
//                drawCircle(
//                    brush = Brush.radialGradient(
//                        colors = listOf(
//                            Color(0xFFfbc2eb).copy(alpha = 0.2f),
//                            Color.Transparent
//                        ),
//                        center = Offset(x = size.width * 0.2f, y = size.height * 0.8f),
//                        radius = size.minDimension / 4
//                    ),
//                    center = Offset(x = size.width * 0.2f, y = size.height * 0.8f),
//                    radius = size.minDimension / 4
//                )
//            }
            // Certificate content overlay
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Profile image with edit button
                Box(contentAlignment = Alignment.BottomEnd) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_profile_placeholder),
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(110.dp)
                            .clip(CircleShape)
                            .background(Color.Gray),
                        contentScale = ContentScale.Crop
                    )
                    IconButton(
                        onClick = { /* Open image picker */ },
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_edit_photo),
                            contentDescription = "Edit Profile",
                            tint = Color.Black
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "CERTIFICATE",
                    style = MaterialTheme.customTypography.jost.semiBold,
                    fontSize = 24.sp,
                    color = Color(0xFF333333)
                )

                Text(
                    text = "OF ACHIEVEMENT",
                    style = MaterialTheme.customTypography.jost.semiBold,
                    fontSize = 18.sp,
                    color = Color(0xFF555555)
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Recipient Section
                Text(
                    text = "This certifies that",
                    style = MaterialTheme.customTypography.mulish.semiBold,
                    fontSize = 24.sp,
                    color = Color(0xFF333333)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Recipient name with a horizontal gradient background
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFFa18cd1),
                                    Color(0xFFfbc2eb)
                                )
                            )
                        )
                        .padding(horizontal = 24.dp, vertical = 12.dp)
                ) {
                    Text(
                        text = certificateData.recipientName,
                        style = MaterialTheme.customTypography.jost.semiBold,
                        fontSize = 28.sp,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Course Details
                Text(
                    text = "has successfully completed",
                    style = MaterialTheme.customTypography.jost.semiBold,
                    fontSize = 16.sp,
                    color = Color(0xFF444444)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = certificateData.courseTitle,
                    style = MaterialTheme.customTypography.jost.semiBold,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    color = Color(0xFF333333)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "ID: ${certificateData.certificateId}",
                    style = MaterialTheme.customTypography.mulish.semiBold,
                    fontSize = 16.sp,
                    color = Color(0xFF666666)
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Signature Section
                Text(
                    text = certificateData.signerName,
                    style = MaterialTheme.customTypography.mulish.semiBold,
                    fontSize = 16.sp,
                    color = Color(0xFF333333)
                )

                Text(
                    text = "Instructor",
                    style = MaterialTheme.customTypography.mulish.semiBold,
                    fontSize = 16.sp,
                    color = Color(0xFF333333)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Issued on ${certificateData.issuedDate}",
                    style = MaterialTheme.customTypography.mulish.semiBold,
                    fontSize = 16.sp,
                    color = Color(0xFF666666)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CertificatePreview() {
    CertificateScreen(
        navController = NavController(LocalContext.current),
        certificateData = sampleData,
    )
}
