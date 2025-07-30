package com.restaurant.billing.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MenuItem(
    val id: Int? = null,
    val name: String,
    val price: Double,
    val category: String,
    val description: String? = null,
    val image_url: String? = null,
    val available: Boolean = true
) : Parcelable

@Parcelize
data class MenuResponse(
    val status: String,
    val data: List<MenuItem>? = null,
    val message: String? = null
) : Parcelable
package com.restaurant.billing.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MenuItem(
    val id: Int? = null,
    val name: String,
    val price: Double,
    val category: String,
    val description: String? = null,
    val is_available: Boolean = true
) : Parcelable

@Parcelize
data class MenuResponse(
    val status: String,
    val data: List<MenuItem>? = null,
    val message: String? = null
) : Parcelable

@Parcelize
data class ItemsRequest(
    val items: List<OrderItem>
) : Parcelable
