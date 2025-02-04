package com.example.mindspark.profile.ui.sections

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.outlined.Payment
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mindspark.auth.components.AuthTopBar
import com.example.mindspark.profile.components.PaymentMethodItem
import com.example.mindspark.ui.theme.LightBlueBackground
import com.example.mindspark.ui.theme.customTypography

@Composable
fun PaymentOptionScreen(navController: NavController) {
    var selectedPaymentMethod by remember { mutableStateOf(0) }

    Scaffold(
        modifier = Modifier.background(LightBlueBackground),
        containerColor = LightBlueBackground,
        topBar = {
            AuthTopBar(
                title = "Payment Options",
                onBackClick = { navController.navigateUp() }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(LightBlueBackground)
                .padding(padding)
                .padding(16.dp)
        ) {
            // Payment Methods Section
            item {
                Text(
                    text = "Payment Methods",
                    style = MaterialTheme.customTypography.jost.semiBold,
                    fontSize = 18.sp,
                    color = Color(0xFF202244),
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }

            // Credit/Debit Cards
            item {
                PaymentMethodItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Default.CreditCard,
                            contentDescription = "Credit Card",
                            tint = Color(0xFF1565C0),
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    title = "Credit/Debit Card",
                    subtitle = "**** **** **** 4242",
                    isSelected = selectedPaymentMethod == 0,
                    onClick = { selectedPaymentMethod = 0 }
                )
            }

            // PayPal
            item {
                PaymentMethodItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Payment,
                            contentDescription = "PayPal",
                            tint = Color(0xFF1565C0),
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    title = "PayPal",
                    subtitle = "connected",
                    isSelected = selectedPaymentMethod == 1,
                    onClick = { selectedPaymentMethod = 1 }
                )
            }

            // Google Pay
            item {
                PaymentMethodItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Payments,
                            contentDescription = "Google Pay",
                            tint = Color(0xFF1565C0),
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    title = "Google Pay",
                    subtitle = "connected",
                    isSelected = selectedPaymentMethod == 2,
                    onClick = { selectedPaymentMethod = 2 }
                )
            }

            // Add New Payment Method Button
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = { /* Handle add payment method */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color(0xFF1565C0)
                    ),
                    shape = RoundedCornerShape(15.dp),
                    border = BorderStroke(1.dp, Color(0xFFBDBDBD)) // Changed to gray
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Payment Method",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Add New Payment Method",
                        style = MaterialTheme.customTypography.mulish.semiBold,
                        fontSize = 16.sp
                    )
                }
            }

            // Note about security
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Security,
                        contentDescription = "Security",
                        tint = Color(0xFF6E7191),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Your payment info is stored securely",
                        style = MaterialTheme.customTypography.mulish.regular,
                        fontSize = 14.sp,
                        color = Color(0xFF6E7191)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview68t() {
    PaymentOptionScreen(navController = NavController(LocalContext.current))
}
