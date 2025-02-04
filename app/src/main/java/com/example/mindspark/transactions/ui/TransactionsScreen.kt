package com.example.mindspark.transactions.ui

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mindspark.transactions.components.*
import com.example.mindspark.transactions.data.getAllTransactions
import com.example.mindspark.ui.theme.customTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionsScreen(navController: NavController) {
    val transactions = getAllTransactions()
    var selectedFilter by remember { mutableStateOf("All") }
    val filters = listOf("All", "Paid", "Pending", "Failed")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Transactions",
                        style = MaterialTheme.customTypography.jost.semiBold,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color(0xFF202244)
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(padding)
        ) {
            // Transaction Summary Card
            TransactionSummaryCard()

            // Filter Chips
            FilterChips(
                filters = filters,
                selectedFilter = selectedFilter,
                onFilterSelected = { selectedFilter = it }
            )

            // Transactions List
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(transactions.size) { index ->
                    TransactionsList(transactions[index])
                }
            }
        }
    }
}

@Composable
fun TransactionSummaryCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Total Spent",
                style = MaterialTheme.customTypography.mulish.regular,
                fontSize = 14.sp,
                color = Color(0xFF6E7191)
            )
            Text(
                text = "$1,234.56",
                style = MaterialTheme.customTypography.jost.bold,
                fontSize = 24.sp,
                color = Color(0xFF202244)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TransactionStatItem(
                    icon = Icons.Default.CheckCircle,
                    label = "Paid",
                    value = "8",
                    color = Color(0xFF167F71)
                )
                TransactionStatItem(
                    icon = Icons.Default.Pending,
                    label = "Pending",
                    value = "3",
                    color = Color(0xFFFFC107)
                )
                TransactionStatItem(
                    icon = Icons.Default.Cancel,
                    label = "Failed",
                    value = "1",
                    color = Color(0xFFD32F2F)
                )
            }
        }
    }
}

@Composable
fun FilterChips(
    filters: List<String>,
    selectedFilter: String,
    onFilterSelected: (String) -> Unit
) {
    ScrollableTabRow(
        selectedTabIndex = filters.indexOf(selectedFilter),
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .background(Color(0xFFF5F5F5)),
        containerColor = Color(0xFFF5F5F5),
        contentColor = Color(0xFF1565C0),
        edgePadding = 0.dp
    ) {
        filters.forEach { filter ->
            Tab(
                selected = selectedFilter == filter,
                onClick = { onFilterSelected(filter) },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                FilterChip(
                    selected = selectedFilter == filter,
                    onClick = { onFilterSelected(filter) },
                    label = {
                        Text(
                            text = filter,
                            style = MaterialTheme.customTypography.mulish.semiBold,
                            fontSize = 14.sp
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(0xFF1565C0),
                        selectedLabelColor = Color.White
                    )
                )
            }
        }
    }
}

@Composable
fun TransactionStatItem(
    icon: ImageVector,
    label: String,
    value: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = value,
            style = MaterialTheme.customTypography.jost.semiBold,
            fontSize = 16.sp,
            color = Color(0xFF202244)
        )
        Text(
            text = label,
            style = MaterialTheme.customTypography.mulish.regular,
            fontSize = 12.sp,
            color = Color(0xFF6E7191)
        )
    }
}