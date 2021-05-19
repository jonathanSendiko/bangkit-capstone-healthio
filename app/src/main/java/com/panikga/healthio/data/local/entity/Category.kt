package com.panikga.healthio.data.local.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    var categoryName: String? = null,
    var categoryImage: Int? = null
) : Parcelable
