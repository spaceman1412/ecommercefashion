package com.example.ecommercefashion.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ItemCart(var id: String?,val name: String, val price: Int, val sex: String, val primaryImage: Int,
               val bannerImage: List<Int>):Parcelable {
                   constructor() : this("","",-1,"",-1, listOf())
}