package com.example.ecommercefashion.models

import java.time.LocalDate
import java.util.*

data class Notification(val id: String, val title: String, val description: String, val coupon: Coupon, val date: String, val imageUrl: String) {

}