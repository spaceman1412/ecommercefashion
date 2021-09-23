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
import com.example.ecommercefashion.databinding.ActivityItemDetailBinding
import com.example.ecommercefashion.databinding.ItemDetailBinding
import com.example.ecommercefashion.models.ItemCart
import com.google.android.material.bottomsheet.BottomSheetBehavior
import me.relex.circleindicator.CircleIndicator
import me.relex.circleindicator.CircleIndicator3
import org.w3c.dom.Text

class ItemDetail : AppCompatActivity() {
    private lateinit var binding: ActivityItemDetailBinding

    var shopItem : ItemCart? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        shopItem = intent.getParcelableExtra(MainActivity.USER_KEY)

        val pager2: ViewPager2 = binding.viewpager2

        val images = shopItem?.bannerImage

        pager2.adapter = SliderAdapter(images!!)
        val circleIndicator : CircleIndicator3 = binding.dotsContainer
        circleIndicator.setViewPager(pager2)

        val add_to_cart : LinearLayout = binding.addToCartBtnItemDetail
        add_to_cart.setOnClickListener {

        }

        binding.titleNameTextViewItemDetail.text = shopItem?.name
        binding.priceTextViewItemDetail.text = "$${shopItem?.price}"


        val sheet : FrameLayout = binding.sheet

        BottomSheetBehavior.from(sheet).apply {
            peekHeight = 200
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }
}


