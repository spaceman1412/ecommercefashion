package com.example.ecommercefashion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetBehavior
import me.relex.circleindicator.CircleIndicator
import me.relex.circleindicator.CircleIndicator3
import org.w3c.dom.Text

class ItemDetail : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)


        val pager2: ViewPager2 = findViewById(R.id.viewpager2)

        val images = listOf(
            R.drawable.pants_list,
            R.drawable.hiphop_list,
            R.drawable.whiteshirt_listt
        )

        pager2.adapter = SliderAdapter(images)
        val circleIndicator : CircleIndicator3 = findViewById(R.id.dots_container)
        circleIndicator.setViewPager(pager2)

        val sheet = findViewById<FrameLayout>(R.id.sheet)

        BottomSheetBehavior.from(sheet).apply {
            peekHeight = 200
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }




}


