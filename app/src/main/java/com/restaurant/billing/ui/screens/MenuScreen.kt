package com.restaurant.billing.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.restaurant.billing.data.model.MenuItem

@Composable
fun MenuScreen(
    menuItems: List<MenuItem>,
    onAddItem: (MenuItem) -> Unit,
    selectedCategory: String?,
    onCategorySelected: (String?) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val categories = remember(menuItems) { menuItems.map { it.category }.distinct().sorted() }
    val filteredItems = menuItems.filter {
        (selectedCategory == null || it.category == selectedCategory) &&
        (searchQuery.isEmpty() || it.name.contains(searchQuery, ignoreCase = true))
    }
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF5F5F5))) {
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search items") },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        )
        Row(modifier = Modifier.horizontalScroll(rememberScrollState()).padding(8.dp)) {
            Button(
                onClick = { onCategorySelected(null) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedCategory == null) Color(0xFF4CAF50) else Color.White
                ),
                modifier = Modifier.padding(end = 8.dp)
            ) { Text("All") }
            categories.forEach { cat ->
                Button(
                    onClick = { onCategorySelected(cat) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedCategory == cat) Color(0xFF4CAF50) else Color.White
                    ),
                    modifier = Modifier.padding(end = 8.dp)
                ) { Text(cat) }
            }
        }
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(filteredItems) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(item.name, fontSize = 18.sp)
                            Text("₹${item.price}", fontSize = 14.sp, color = Color.Gray)
                            Text(item.category, fontSize = 12.sp, color = Color.Gray)
                        }
                        Button(
                            onClick = { onAddItem(item) },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3), contentColor = Color.White)
                        ) {
                            Text("Add")
                        }
                    }
                }
            }
        }
    }
}
package com.restaurant.billing.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
        
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(menuItems) { item ->
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = item.name, fontSize = 18.sp)
                            Text(text = "₹${item.price}", fontSize = 16.sp)
                        }
                        Button(
                            onClick = { onAddItem(item) }
                        ) {
                            Text("Add")
                        }
                    }
                }
            }
        }
        
        if (newItems.isNotEmpty()) {
            Button(
                onClick = onViewOrder,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text("View Order (${newItems.size} items)")
            }
        }
    }
}
