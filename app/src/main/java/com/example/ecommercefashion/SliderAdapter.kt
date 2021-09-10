package com.example.ecommercefashion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SliderAdapter(val images: List<Int>) : RecyclerView.Adapter<SliderAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val view : View

        init {
            view = itemView.findViewById(R.id.view)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_detail,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.setBackgroundColor(images[position])
    }

    override fun getItemCount(): Int {
        return images.size
    }

}