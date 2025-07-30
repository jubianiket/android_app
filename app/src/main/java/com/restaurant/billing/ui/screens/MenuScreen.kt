package com.restaurant.billing.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.restaurant.billing.data.model.MenuItem
import com.restaurant.billing.data.model.OrderItem

@Composable
fun MenuScreen(
    menuItems: List<MenuItem>,
    newItems: List<OrderItem>,
    onAddItem: (MenuItem) -> Unit,
    onRemoveItem: (OrderItem) -> Unit,
    onViewOrder: () -> Unit,
    onFetchMenu: () -> Unit
) {
    LaunchedEffect(Unit) {
        onFetchMenu()
    }

    val categories = menuItems.map { it.category }.distinct()
    var selectedCategory by remember { mutableStateOf("All") }

    val filteredItems = if (selectedCategory == "All") {
        menuItems
    } else {
        menuItems.filter { it.category == selectedCategory }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Menu",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Category filter
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            item {
                FilterChip(
                    onClick = { selectedCategory = "All" },
                    label = { Text("All") },
                    selected = selectedCategory == "All"
                )
            }
            items(categories) { cat ->
                FilterChip(
                    onClick = { selectedCategory = cat },
                    label = { Text(cat) },
                    selected = selectedCategory == cat,
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filteredItems) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(item.name, fontSize = 18.sp)
                            Text("₹${item.price}", fontSize = 16.sp, color = Color.Gray)
                            Text(item.category, fontSize = 12.sp, color = Color.Gray)
                        }
                        Button(
                            onClick = { onAddItem(item) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF2196F3),
                                contentColor = Color.White
                            )
                        ) {
                            Text("Add")
                        }
                    }
                }
            }
        }

        // Order summary button
        if (newItems.isNotEmpty()) {
            Button(
                onClick = onViewOrder,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50),
                    contentColor = Color.White
                )
            ) {
                Text("View Order (${newItems.size} items)")
            }
        }
    }
}