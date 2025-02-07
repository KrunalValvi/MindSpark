package com.example.mindspark.payment.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mindspark.auth.components.AuthButton
import com.example.mindspark.auth.components.AuthTopBar
import com.example.mindspark.ui.theme.LightBlueBackground
import com.example.mindspark.ui.theme.customTypography
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PaymentScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var selectedPaymentMethod by remember { mutableStateOf<String?>(null) }
    var showPaymentDetails by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    val isAtBottom = remember { mutableStateOf(false) }

    var showCongratulationsDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    // Add the Congratulations Dialog
    if (showCongratulationsDialog) {
        AlertDialog(
            onDismissRequest = { },
            containerColor = Color(0xFFEFF4FD),
            title = {
                Text(
                    text = "Congratulations!",
                    style = MaterialTheme.customTypography.jost.semiBold,
                    fontSize = 20.sp,

                )
            },
            text = {
                Text(
                    text = "Your payment was successful. Thank you for choosing MindSpark!",
                    style = MaterialTheme.customTypography.mulish.regular,
                    fontSize = 16.sp
                )
            },
            confirmButton = { }
        )
    }

    // Track scroll position to show/hide the button
    LaunchedEffect(listState) {
        snapshotFlow { listState.canScrollForward }
            .collect { canScroll ->
                isAtBottom.value = !canScroll
            }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = modifier.background(LightBlueBackground),
            containerColor = LightBlueBackground,
            topBar = {
                AuthTopBar(
                    title = "Payment",
                    onBackClick = { navController.navigateUp() }
                )
            }
        ) { padding ->
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .background(LightBlueBackground)
                    .padding(padding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Text(
                        text = "Select Payment Method",
                        fontSize = 20.sp,
                        style = MaterialTheme.customTypography.jost.semiBold,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                }

                // Payment Methods Section
                item {
                    PaymentMethodCard(
                        title = "Credit/Debit Card",
                        icon = Icons.Filled.CreditCard,
                        isSelected = selectedPaymentMethod == "card",
                        onClick = {
                            selectedPaymentMethod = "card"
                            showPaymentDetails = true
                        }
                    )
                }

                item {
                    PaymentMethodCard(
                        title = "Google Pay",
                        icon = Icons.Filled.AccountBalance,
                        isSelected = selectedPaymentMethod == "bank",
                        onClick = {
                            selectedPaymentMethod = "bank"
                            showPaymentDetails = true
                        }
                    )
                }

                // Payment Details Section
                item {
                    AnimatedVisibility(
                        visible = showPaymentDetails,
                        enter = fadeIn() + expandVertically(),
                        exit = fadeOut() + shrinkVertically()
                    ) {
                        PaymentDetailsCard(
                            amount = "₹999.00",
                            description = "Thank you for choosing MindSpark! Your payment of ₹999.00 has been processed successfully."
                        )
                    }
                }

                // Add space at the bottom for the floating button
                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }

        AnimatedVisibility(
            visible = isAtBottom.value,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(LightBlueBackground)
                .padding(16.dp),
            enter = fadeIn() + slideInVertically(initialOffsetY = { it }),
            exit = fadeOut() + slideOutVertically(targetOffsetY = { it })
        ) {
            AuthButton(
                text = "Apply",
                onClick = {
                    coroutineScope.launch {
                        showCongratulationsDialog = true
                        // Show dialog for 2 seconds
                        delay(1000)
                        // Navigate to home screen
                        navController.navigate("HomeScreen") {
                            popUpTo(0) // Clear the back stack
                            launchSingleTop = true
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth() // Changed from width(20.dp) for better UX
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PaymentScreenPreview() {
    PaymentScreen(navController = NavController(LocalContext.current))
}

@Composable
fun PaymentMethodCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
//            .border(
//                width = 1.dp,
////                color = if (isSelected) MaterialTheme.colorScheme.primary
////                else Color(0xFFFFFFFF),
//                shape = RoundedCornerShape(12.dp)
//            ),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF4FD)),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
//                    tint = if (isSelected) MaterialTheme.colorScheme.primary
//                    else MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = title,
                    style = MaterialTheme.customTypography.mulish.semiBold,
                    fontSize = 16.sp,
                )
            }
            if (isSelected) {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Selected",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun PaymentDetailsCard(amount: String, description: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF4FD)),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Payment Details",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Amount",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Text(
                    text = amount,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            Text(
                text = description,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
fun SecurityNote() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Outlined.Info,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Secured by SSL encryption",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            textAlign = TextAlign.Center
        )
    }
}