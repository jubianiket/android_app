package com.restaurant.billing.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.restaurant.billing.data.model.MenuItem
import com.restaurant.billing.data.model.OrderItem
import com.restaurant.billing.data.remote.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {
    private val _menuItems = MutableStateFlow<List<MenuItem>>(emptyList())
    val menuItems: StateFlow<List<MenuItem>> = _menuItems

    private val _confirmedItems = MutableStateFlow<List<OrderItem>>(emptyList())
    val confirmedItems: StateFlow<List<OrderItem>> = _confirmedItems

    private val _newItems = MutableStateFlow<List<OrderItem>>(emptyList())
    val newItems: StateFlow<List<OrderItem>> = _newItems

    private val _orderId = MutableStateFlow<Int?>(null)
    val orderId: StateFlow<Int?> = _orderId

    fun fetchMenu() {
        viewModelScope.launch {
            val response = apiService.getMenuItems()
            _menuItems.value = response.data ?: emptyList()
        }
    }

    fun addItem(item: MenuItem) {
        val existing = _newItems.value.find { it.name == item.name }
        if (existing != null) {
            _newItems.value = _newItems.value.map {
                if (it.name == item.name) it.copy(quantity = it.quantity + 1) else it
            }
        } else {
            _newItems.value = _newItems.value + OrderItem(
                menu_item_id = item.id ?: 0,
                name = item.name,
                price = item.price,
                quantity = 1
            )
        }
    }

    fun removeItem(item: OrderItem) {
        _newItems.value = _newItems.value.filterNot { it.name == item.name }
    }

    fun confirmOrder(tableNumber: Int) {
        viewModelScope.launch {
            if (_orderId.value != null) {
                // Append items to existing order
                apiService.appendItemsToOrder(_orderId.value!!, ItemsRequest(_newItems.value))
                _confirmedItems.value = _confirmedItems.value + _newItems.value
                _newItems.value = emptyList()
            } else {
                // Create new order
                val response = apiService.saveOrder(
                    OrderRequest(
                        items = _newItems.value,
                        table_number = tableNumber,
                        order_type = "dine-in"
                    )
                )
                _orderId.value = response.order_id
                _confirmedItems.value = _confirmedItems.value + _newItems.value
                _newItems.value = emptyList()
            }
        }
    }

    fun printBill() {
        viewModelScope.launch {
            if (_orderId.value != null) {
                apiService.completeOrder(_orderId.value!!)
                _confirmedItems.value = emptyList()
                _newItems.value = emptyList()
                _orderId.value = null
            }
        }
    }
}
