package com.restaurant.billing.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TableSelectionScreen(
    totalTables: Int = 12,
    onTableSelected: (Int) -> Unit = {}
) {
    var selectedTable by remember { mutableStateOf(-1) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Select Table",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(top = 32.dp, bottom = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Table buttons grid
        for (row in 0 until (totalTables + 3) / 4) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                for (col in 0..3) {
                    val tableNum = row * 4 + col + 1
                    if (tableNum <= totalTables) {
                        val isSelected = selectedTable == tableNum
                        Button(
                            onClick = {
                                selectedTable = tableNum
                                onTableSelected(tableNum)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isSelected) Color(0xFF4CAF50) else Color.White,
                                contentColor = if (isSelected) Color.White else Color.Black
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .padding(8.dp)
                                .size(80.dp, 48.dp)
                        ) {
                            Text("Table $tableNum", fontSize = 16.sp)
                        }
                    } else {
                        Spacer(modifier = Modifier.size(80.dp, 48.dp))
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        if (selectedTable > 0) {
            Button(
                onClick = { /* TODO: Navigate to menu/order screen */ },
                enabled = selectedTable > 0,
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Proceed to Order")
            }
        }
    }
}
