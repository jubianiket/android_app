package com.restaurant.billing.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int? = null,
    val username: String,
    val email: String? = null,
    val role: String = "user", // "admin" or "user"
    val password: String? = null
) : Parcelable

@Parcelize
data class LoginRequest(
    val username: String,
    val password: String
) : Parcelable

@Parcelize
data class SignupRequest(
    val username: String,
    val email: String,
    val password: String,
    val confirm_password: String,
    val role: String = "user"
) : Parcelable

@Parcelize
data class AuthResponse(
    val status: String,
    val message: String? = null,
    val user: User? = null
) : Parcelable
