package com.example.ecommercefashion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import me.relex.circleindicator.CircleIndicator
import me.relex.circleindicator.CircleIndicator3
import org.w3c.dom.Text

class ItemDetail : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)



        val dotsLayout: LinearLayout = findViewById(R.id.dots_container)


        val pager2: ViewPager2 = findViewById(R.id.viewpager2)

        val images = listOf(
            resources.getColor(R.color.black),
            resources.getColor(R.color.white),
            resources.getColor(R.color.purple_200),
        )

        pager2.adapter = SliderAdapter(images)
        val circleIndicator : CircleIndicator3 = findViewById(R.id.dots_container)
        circleIndicator.setViewPager(pager2)






    }




}


