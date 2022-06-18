package com.example.ecommercefashion.models

class Coupons {
    var Id = ""
    var NameCoupons = ""
    var Discount = 0
    var EndDate = ""

    constructor(id: String, NameCoupons: String, Discount: Int, EndDate: String) {
        this.Id = id
        this.NameCoupons = NameCoupons
        this.Discount = Discount
        this.EndDate = EndDate
    }
}