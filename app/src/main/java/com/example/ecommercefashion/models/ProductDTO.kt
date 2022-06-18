package com.example.ecommercefashion.models

class ProductDTO {
    var id = ""
    var name = ""
    var price = 0
    var primaryImage = ""
    var sex = ""

    constructor(id: String,name: String, price: Int, primaryImage: String, sex: String) {
        this.id = id
        this.name = name
        this.price = price
        this.primaryImage = primaryImage
        this.sex = sex
    }
}