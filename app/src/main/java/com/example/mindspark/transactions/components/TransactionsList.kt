package com.example.mindspark.transactions.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindspark.transactions.model.TransactionsModel
import com.example.mindspark.ui.theme.customTypography

@Composable
fun TransactionsList(transaction: TransactionsModel) {
    val statusColor = when (transaction.status) {
        "Paid" -> Color(0xFF167F71)
        "Pending" -> Color(0xFFFFC107)
        "Failed" -> Color(0xFFD32F2F)
        else -> Color.Gray
    }

    val statusIcon = when (transaction.status) {
        "Paid" -> Icons.Default.CheckCircle
        "Pending" -> Icons.Default.Pending
        "Failed" -> Icons.Default.Cancel
        else -> Icons.Default.Help
    }

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Course Image/Icon
            Card(
                modifier = Modifier.size(60.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF1565C0).copy(alpha = 0.1f)
                )
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.School,
                        contentDescription = null,
                        tint = Color(0xFF1565C0),
                        modifier = Modifier.size(30.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Transaction Details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = transaction.title,
                    style = MaterialTheme.customTypography.jost.semiBold,
                    fontSize = 16.sp,
                    color = Color(0xFF202244)
                )

                Text(
                    text = transaction.course,
                    style = MaterialTheme.customTypography.mulish.regular,
                    fontSize = 14.sp,
                    color = Color(0xFF6E7191)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = statusIcon,
                        contentDescription = null,
                        tint = statusColor,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = transaction.status,
                        style = MaterialTheme.customTypography.mulish.regular,
                        fontSize = 12.sp,
                        color = statusColor
                    )
                }
            }

            // Price (you might want to add this to your TransactionsModel)
            Text(
                text = "$99.99",  // Replace with actual price from model
                style = MaterialTheme.customTypography.jost.bold,
                fontSize = 16.sp,
                color = Color(0xFF202244)
            )
        }
    }
}