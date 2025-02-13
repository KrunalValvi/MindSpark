package com.example.mindspark.profile.ui.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mindspark.auth.components.AuthButton
import com.example.mindspark.auth.components.AuthTopBar
import com.example.mindspark.ui.theme.LightBlueBackground
import com.example.mindspark.ui.theme.customTypography
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PaymentOptionScreen(navController: NavController) {
    Scaffold(
        // Only one background is used via containerColor
        containerColor = LightBlueBackground,
        topBar = {
            AuthTopBar(
                title = "Payment Option",
                onBackClick = { navController.navigateUp() }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)      // Use the scaffold's padding
                .padding(16.dp),       // Internal padding
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val cards = listOf(
//                "**** **** **65 8765",
//                "**** **** **45 1234",
                " ",
                " ",
                "**** **** **76 3054"
            )

            cards.forEach { cardNumber ->
                PaymentCardItem(cardNumber)
                Spacer(modifier = Modifier.height(16.dp))
            }

            Spacer(modifier = Modifier.weight(1f))

            // Add New Card Button
            AuthButton(
                text = "Add New Card",
                onClick = { navController.navigate("AddNewCardScreen") }
            )
        }
    }
}

@Composable
fun PaymentCardItem(cardNumber: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .height(60.dp)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = cardNumber,
            style = MaterialTheme.customTypography.mulish.bold,
            color = Color.Black,
            fontSize = 14.sp
        )
        Text(
            text = "Connected",
            style = MaterialTheme.customTypography.mulish.bold,
            color = Color(0xFF167F71),
            fontSize = 14.sp
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PaymentOptionScreenPreview() {
    PaymentOptionScreen(navController = NavController(LocalContext.current))
}
