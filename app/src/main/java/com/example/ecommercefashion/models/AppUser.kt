package com.example.ecommercefashion.models

data class AppUser ( var uid: String ,var email:String, var phoneNumber: String,
                  var location: String, var name: String
)
{
    constructor() : this("","","","","")
}
