package com.example.ecommercefashion.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Product(var id: String?,val name: String, val price: Int, val primaryImage: String, val sex: String) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
    constructor() : this("","",-1,"","")
}

