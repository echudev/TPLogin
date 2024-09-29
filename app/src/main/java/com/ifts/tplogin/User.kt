package com.ifts.tplogin

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(val name: String, val password: String): Parcelable {
    init {
        require(name.isNotEmpty()) { "Name cannot be empty" }
        require(password.isNotEmpty()) { "Password cannot be empty" }
    }
}
