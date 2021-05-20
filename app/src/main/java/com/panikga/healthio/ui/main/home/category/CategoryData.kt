package com.panikga.healthio.ui.main.home.category

import com.panikga.healthio.R
import com.panikga.healthio.data.local.entity.Category

object CategoryData {
    private val categoryNames = arrayOf(
        "RSU",
        "JANTUNG",
        "RSIA",
        "BEDAH",
        "GM",
        "JANTUNG"
    )
    private val categoryImages = intArrayOf(
        R.drawable.general,
        R.drawable.cardiology,
        R.drawable.gastrology,
        R.drawable.neurology,
        R.drawable.pediatric,
        R.drawable.entdoctor
    )

    val listData: ArrayList<Category>
        get() {
            val list = arrayListOf<Category>()
            for (position in categoryNames.indices) {
                val category = Category()
                category.categoryName = categoryNames[position]
                category.categoryImage = categoryImages[position]
                list.add(category)
            }
            return list
        }
}