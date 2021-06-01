package com.panikga.healthio.data.local.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Appointment(
    var id: String? = null,
    var doctorName: String? = null,
    var spesialization: String? = null,
    var date: String? = null,
    var hour: String? = null,
    var hospial: String? = null
) : Parcelable
