package com.panikga.healthio.data.local.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var name: String? = "",
    var address: String? = "",
    var age : Int? = null,
    var phoneNumber: Int? = null,
    var idCardNumber: Int? = null
) : Parcelable
