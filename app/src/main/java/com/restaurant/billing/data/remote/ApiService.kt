package com.restaurant.billing.data.remote

import com.restaurant.billing.data.model.MenuItem
import com.restaurant.billing.data.model.Order
import retrofit2.http.*

interface ApiService {
    @GET("/menu_items")
    suspend fun getMenuItems(): MenuResponse

    @POST("/save_order")
    suspend fun saveOrder(@Body order: OrderRequest): OrderResponse

    @POST("/orders/{order_id}/append_items")
    suspend fun appendItemsToOrder(@Path("order_id") orderId: Int, @Body items: ItemsRequest): ApiResponse

    @POST("/orders/{order_id}/complete")
    suspend fun completeOrder(@Path("order_id") orderId: Int): ApiResponse

    @GET("/api/settings")
    suspend fun getSettings(): SettingsResponse
}

// Data classes for responses and requests

data class MenuResponse(val status: String, val data: List<MenuItem>?)
data class OrderResponse(val status: String, val order_id: Int?)
data class ApiResponse(val status: String, val message: String?)
data class SettingsResponse(val status: String, val data: SettingsData?)
data class SettingsData(val restaurant_name: String, val total_tables: Int)
data class ItemsRequest(val items: List<OrderItem>)
