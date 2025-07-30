
package com.restaurant.billing.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
fun RestaurantBillingApp(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.TableSelection.route
    ) {
        composable(Screen.TableSelection.route) {
            TableSelectionScreen(
                onTableSelected = { tableNumber ->
                    navController.navigate(Screen.Menu.createRoute(tableNumber))
                }
            )
        }
        
        composable(Screen.Menu.route) { backStackEntry ->
            val tableNumber = backStackEntry.arguments?.getString("tableNumber")?.toInt() ?: 1
            val orderViewModel: OrderViewModel = hiltViewModel()
            val menuItems by orderViewModel.menuItems.collectAsState()
            val newItems by orderViewModel.newItems.collectAsState()
            
            MenuScreen(
                menuItems = menuItems,
                newItems = newItems,
                onAddItem = { orderViewModel.addItem(it) },
                onRemoveItem = { orderViewModel.removeItem(it) },
                onViewOrder = {
                    navController.navigate(Screen.Order.createRoute(tableNumber))
                },
                onFetchMenu = { orderViewModel.fetchMenu() }
            )
        }
        
        composable(Screen.Order.route) { backStackEntry ->
            val tableNumber = backStackEntry.arguments?.getString("tableNumber")?.toInt() ?: 1
            val orderViewModel: OrderViewModel = hiltViewModel()
            val confirmedItems by orderViewModel.confirmedItems.collectAsState()
            val newItems by orderViewModel.newItems.collectAsState()
            
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
