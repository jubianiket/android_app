package com.restaurant.billing.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.restaurant.billing.ui.screens.TableSelectionScreen

@Composable
fun RestaurantBillingApp() {
    MaterialTheme {
        Surface(modifier = Modifier) {
            AppNavigation()
        }
    }
}
