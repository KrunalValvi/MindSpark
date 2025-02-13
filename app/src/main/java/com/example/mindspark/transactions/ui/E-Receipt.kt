package com.example.mindspark.transactions.ui

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mindspark.R
import com.example.mindspark.auth.components.AuthTopBar
import com.example.mindspark.ui.theme.LightBlueBackground
import com.example.mindspark.ui.theme.customTypography

@Composable
fun EReceiptScreen(navController: NavController) {

    Scaffold(
        modifier = Modifier.background(LightBlueBackground),
        containerColor = LightBlueBackground,
        topBar = {
            AuthTopBar(
                title = "E-Receipt",
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

            Image(
                painter = painterResource(id = R.drawable.ic_receipt),
                contentDescription = "Receipt",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.ic_barcode),
                contentDescription = "Bor Code",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            Column(modifier = Modifier.fillMaxSize()) {

                Column(modifier = Modifier.padding(16.dp)) {
                    DetailRow("Name", "Alex")
                    DetailRow("Email ID", "alexreall@gmail.com")
                    DetailRow("Course", "3D Character Illustration")
                    DetailRow("Category", "Web Development")
                    DetailRow("Transaction ID", "SK345680976")
                    DetailRow("Price", "₹799/-")
                    DetailRow("Date", "Nov 20, 2023 / 15:45")
                    DetailRow("Status", "Paid")
                }
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.customTypography.jost.semiBold,
            fontSize = 15.sp
        )
        when (value) {
            "Paid" -> {
                Box(
                    modifier = Modifier
                        .background(Color(0xFF167F71))
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = value,
                        style = MaterialTheme.customTypography.mulish.bold,
                        fontSize = 14.sp,
                        color = Color.White
                    )
                }
            }
            else -> {
                Text(
                    text = value,
                    style = MaterialTheme.customTypography.mulish.bold,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}

@Preview
@Composable
fun EReceiptPreview() {
    EReceiptScreen(navController = NavController(LocalContext.current))
}