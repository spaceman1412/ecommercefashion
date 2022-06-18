package com.example.ecommercefashion.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemCart(var id: String?, val name: String, val price: Int, val sex: String, val primaryImageUrl: String,
                    val bannerImage: List<Int>, val category: List<String>, var size : String = ""):Parcelable {
                   constructor() : this("","",-1,"","", listOf(), listOf(),"")
}