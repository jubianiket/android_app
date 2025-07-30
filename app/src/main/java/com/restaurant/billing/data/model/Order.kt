package com.restaurant.billing.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderItem(
    val menu_item_id: Int,
    val name: String,
    val price: Double,
    val quantity: Int,
    val total: Double = price * quantity
) : Parcelable

@Parcelize
data class Order(
    val id: Int? = null,
    val items: List<OrderItem>,
    val table_number: Int? = null,
    val order_type: String = "dine-in", // "dine-in" or "delivery"
    val status: String = "received", // "received", "completed"
    val payment_status: String = "pending", // "pending", "paid"
    val date: String,
    val total: Double,
    val gst: Double? = null,
    val customer_name: String? = null,
    val customer_phone: String? = null,
    val delivery_address: String? = null
) : Parcelable

@Parcelize
data class OrderRequest(
    val items: List<OrderItem>,
    val table_number: Int? = null,
    val order_type: String = "dine-in",
    val customer_name: String? = null,
    val customer_phone: String? = null,
    val delivery_address: String? = null
) : Parcelable

@Parcelize
data class OrderResponse(
    val status: String,
    val data: List<Order>? = null,
    val message: String? = null,
    val order_id: Int? = null
) : Parcelable
