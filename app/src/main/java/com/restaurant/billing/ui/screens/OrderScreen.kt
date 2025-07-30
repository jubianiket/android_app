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
import com.restaurant.billing.data.model.OrderItem
import com.restaurant.billing.data.model.MenuItem

@Composable
fun OrderScreen(
    confirmedItems: List<OrderItem>,
    newItems: List<OrderItem>,
    onConfirmOrder: () -> Unit,
    onPrintBill: () -> Unit,
    onRemoveItem: (OrderItem) -> Unit
) {
    val allItems = confirmedItems + newItems
    var subtotal by remember { mutableStateOf(0.0) }
    var gstEnabled by remember { mutableStateOf(true) }
    val gstRate = if (gstEnabled) 0.18 else 0.0
    subtotal = allItems.sumOf { it.price * it.quantity }
    val gst = subtotal * gstRate
    val total = subtotal + gst

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF5F5F5))) {
        Text("Order Items", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(16.dp))
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(allItems) { item ->
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
                            Text("Qty: ${item.quantity}", fontSize = 14.sp)
                            Text("₹${item.price}", fontSize = 14.sp, color = Color.Gray)
                        }
                        IconButton(onClick = { onRemoveItem(item) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Remove")
                        }
                    }
                }
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Checkbox(checked = gstEnabled, onCheckedChange = { gstEnabled = it })
            Text("GST (18%)", modifier = Modifier.padding(start = 8.dp))
        }
        Text("Subtotal: ₹${"%.2f".format(subtotal)}", fontSize = 16.sp, modifier = Modifier.padding(8.dp))
        Text("GST: ₹${"%.2f".format(gst)}", fontSize = 16.sp, modifier = Modifier.padding(8.dp))
        Text("Total: ₹${"%.2f".format(total)}", fontSize = 18.sp, modifier = Modifier.padding(8.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Button(onClick = onConfirmOrder, enabled = newItems.isNotEmpty()) {
                Text("Confirm Order")
            }
            Button(onClick = onPrintBill, enabled = confirmedItems.isNotEmpty()) {
                Text("Print Bill")
            }
        }
    }
}
