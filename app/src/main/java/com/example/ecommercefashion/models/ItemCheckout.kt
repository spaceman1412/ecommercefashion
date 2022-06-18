package com.example.ecommercefashion.models


data class ItemCheckout(val id: String, val coupon: Coupon?, val price: Int, val product_list: MutableList<ItemCart>)
    {
    constructor() : this("",null,-1, mutableListOf())
}