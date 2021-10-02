package com.example.ecommercefashion.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemCart(var id: String?,val name: String, val price: Int, val sex: String, val primaryImage: Int,
               val bannerImage: List<Int>,val category: List<String>):Parcelable {
                   constructor() : this("","",-1,"",-1, listOf(), listOf())
}