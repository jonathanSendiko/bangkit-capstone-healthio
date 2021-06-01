package com.panikga.healthio.data.local.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Doctor(
    var jumlahpasien: String? = "",
    var jumlahpengalaman: String? = "",
    var nama: String? = "",
    var photo: String? = "",
    var spesialisasi: String? = "",
    var rumahsakit: String? = ""
) : Parcelable
