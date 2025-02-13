package com.example.mindspark.transactions.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mindspark.transactions.model.TransactionsModel
import com.example.mindspark.ui.theme.customTypography

@Composable
fun TransactionsList(transactions: TransactionsModel, navController: NavController) {
//    val statusColor = when (transactions.status) {
//        "Paid" -> Color(0xFF167F71) // Green
//        "Pending" -> Color(0xFFFFC107) // Yellow
//        "Failed" -> Color(0xFFD32F2F) // Red
//        else -> Color.Gray
//    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .padding(18.dp)
            .width(80.dp)
            .clickable { navController.navigate("EReceiptScreen") },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            modifier = Modifier.size(70.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Black)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                // Empty for now
            }
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column {
            androidx.compose.material3.Text(
                text = transactions.title,
                color = Color(0xFF202244),
                style = MaterialTheme.customTypography.jost.semiBold,
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
            )

            androidx.compose.material3.Text(
                text = transactions.course,
                color = Color(0xFF545454),
                style = MaterialTheme.customTypography.mulish.bold,
                fontSize = 13.sp,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .background(Color(0xFF167F71))
                    .width(60.dp)
                    .height(22.dp)
            ) {
                androidx.compose.material3.Text(
                    text = "Paid",
                    color = Color.White,
                    style = MaterialTheme.customTypography.mulish.bold,
                    fontSize = 13.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionsListPreview() {
    val context = LocalContext.current
    TransactionsList(transactions = TransactionsModel(
        title = "Alex Reall",
        course = "3D Character Illustration",
//        status = "Paid"
    ), navController = NavController(context))
}

