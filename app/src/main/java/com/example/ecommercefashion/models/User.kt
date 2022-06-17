package com.example.ecommercefashion.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User ( var uid: String ,var email:String, var phoneNumber: String,
                  var location: String, var name: String) : Parcelable
{
    constructor() : this("","","","","")
}


