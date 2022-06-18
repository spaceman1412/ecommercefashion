package com.example.ecommercefashion.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.time.LocalDate
import java.util.*

@Parcelize
data class Coupon(val id: String, val discount: Int, val endDate: String, val nameCoupons: String) : Parcelable {
    constructor() : this("",-1,"", "")
}