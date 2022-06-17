package com.example.ecommercefashion.models

class ProductDTO {
    var name = ""
    var price = 0
    var url = ""
    var sex = ""

    constructor(name: String, price: Int, url: String, sex: String) {
        this.name = name
        this.price = price
        this.url = url
        this.sex = sex
    }
}