package com.example.mindspark.profile.ui.sections

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mindspark.R
import com.example.mindspark.auth.components.AuthButton
import com.example.mindspark.auth.components.AuthTopBar
import com.example.mindspark.auth.components.CustomTextField
import com.example.mindspark.profile.components.SimpleCustomTextField
import com.example.mindspark.ui.theme.LightBlueBackground
import com.example.mindspark.ui.theme.customTypography

@Composable
fun AddNewCardScreen(navController: NavController) {
    // State variables for each editable field
    var cardName by remember { mutableStateOf("alexia") }
    var cardNumber by remember { mutableStateOf("**** **65 8765 3456") }
    var expiryDate by remember { mutableStateOf("12/28") }
    var cvv by remember { mutableStateOf("***") }

    Scaffold(
        modifier = Modifier.background(LightBlueBackground),
        containerColor = LightBlueBackground,
        topBar = {
            AuthTopBar(
                title = "Add New Card",
                onBackClick = { navController.navigateUp() }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            // Credit Card UI
            CardWithBackground()
            Spacer(modifier = Modifier.height(20.dp))
            // Editable Input Fields using our custom TextField design with external labels.
            SimpleCustomTextField(
                label = "Card Name*",
                value = cardName,
                onValueChange = { cardName = it }
            )
            Spacer(modifier = Modifier.height(10.dp)) // Space between fields

            SimpleCustomTextField(
                label = "Card Number*",
                value = cardNumber,
                onValueChange = { cardNumber = it }
            )
            Spacer(modifier = Modifier.height(10.dp)) // Space between fields

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SimpleCustomTextField(
                    label = "Expiry Date*",
                    value = expiryDate,
                    onValueChange = { expiryDate = it },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(16.dp))
                SimpleCustomTextField(
                    label = "CVV*",
                    value = cvv,
                    onValueChange = { cvv = it },
                    modifier = Modifier.weight(1f),
                )
            }
            Spacer(modifier = Modifier.weight(0.5f))
            AuthButton(
                text = "Go to Profile",
                onClick = { navController.navigate("ProfileScreen") }
            )
        }
    }
}

@Composable
fun CardWithBackground() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
    ) {
        // Background image for the card
        Image(
            painter = painterResource(id = R.drawable.ic_card_background),
            contentDescription = "Card Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(360.dp)
                .height(180.dp)
        )
        // Overlay content on the card
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 30.dp, top = 25.dp)
        ) {
            // Card Icon (Top-left)
            Icon(
                painter = painterResource(id = R.drawable.ic_card_icon),
                contentDescription = "Card Icon",
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.height(30.dp))
            // Card Number
            Text(
                text = "1234 5678 8765 0876",
                fontSize = 17.sp,
                style = MaterialTheme.customTypography.mulish.extraBold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            // Expiry Date Row
            Row {
                Column {
                    Text(
                        text = "VALID",
                        fontSize = 8.sp,
                        style = MaterialTheme.customTypography.mulish.extraBold,
                        color = Color.White
                    )
                    Text(
                        text = "THRU",
                        fontSize = 8.sp,
                        style = MaterialTheme.customTypography.mulish.extraBold,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "12/28",
                    fontSize = 14.sp,
                    style = MaterialTheme.customTypography.mulish.extraBold,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            // Card Holder Name
            Text(
                text = "ALEX",
                fontSize = 14.sp,
                style = MaterialTheme.customTypography.mulish.extraBold,
                color = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddNewCardScreenPreview() {
    AddNewCardScreen(navController = NavController(LocalContext.current))
}
