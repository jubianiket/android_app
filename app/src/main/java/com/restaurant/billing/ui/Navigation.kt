package com.restaurant.billing.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.restaurant.billing.ui.screens.TableSelectionScreen
import com.restaurant.billing.ui.screens.MenuScreen
import com.restaurant.billing.ui.screens.OrderScreen
import com.restaurant.billing.ui.viewmodel.OrderViewModel

sealed class Screen(val route: String) {
    object TableSelection : Screen("table_selection")
    object Menu : Screen("menu/{tableNumber}") {
        fun createRoute(tableNumber: Int) = "menu/$tableNumber"
    }
    object Order : Screen("order/{tableNumber}") {
        fun createRoute(tableNumber: Int) = "order/$tableNumber"
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val orderViewModel: OrderViewModel = hiltViewModel()
    NavHost(navController = navController, startDestination = Screen.TableSelection.route) {
        composable(Screen.TableSelection.route) {
            TableSelectionScreen(
                totalTables = 12,
                onTableSelected = { tableNum ->
                    navController.navigate(Screen.Menu.createRoute(tableNum))
                }
            )
        }
        composable(Screen.Menu.route) { backStackEntry ->
            val tableNumber = backStackEntry.arguments?.getString("tableNumber")?.toIntOrNull() ?: 1
            orderViewModel.fetchMenu()
            val menuItems = orderViewModel.menuItems.value
            var selectedCategory by remember { mutableStateOf<String?>(null) }
            MenuScreen(
                menuItems = menuItems,
                onAddItem = { orderViewModel.addItem(it) },
                selectedCategory = selectedCategory,
                onCategorySelected = { selectedCategory = it }
            )
            // Navigation to order screen can be triggered by a button in MenuScreen
        }
        composable(Screen.Order.route) { backStackEntry ->
            val tableNumber = backStackEntry.arguments?.getString("tableNumber")?.toIntOrNull() ?: 1
            val confirmedItems = orderViewModel.confirmedItems.value
            val newItems = orderViewModel.newItems.value
            OrderScreen(
                confirmedItems = confirmedItems,
                newItems = newItems,
                onConfirmOrder = { orderViewModel.confirmOrder(tableNumber) },
                onPrintBill = { orderViewModel.printBill() },
                onRemoveItem = { orderViewModel.removeItem(it) }
            )
        }
    }
}
