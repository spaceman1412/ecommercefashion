package com.example.ecommercefashion

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommercefashion.models.ItemCart

class SearchAdapter(val item_detail_list: MutableList<ItemCart>,
                    ) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>(), Filterable {
    private val item_detail_list_all = mutableListOf<ItemCart>(
        ItemCart(
            "1",
            "Cotton Pant",
            58,
            "man",
            R.drawable.pants,
            listOf(R.drawable.pants_list, R.drawable.hiphop_list),
            listOf("Trousers", "Winter Collection")
        ),
        ItemCart(
            "2",
            "White Shirt",
            58,
            "man",
            R.drawable.whitetee,
            listOf(R.drawable.whiteshirt_listt, R.drawable.hiphop_list),
            listOf("Shirt")
        ),
    )


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title : TextView = view.findViewById(R.id.name_textView_searchItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = item_detail_list[position].name
    }

    override fun getItemCount(): Int {
        return item_detail_list.size
    }

    override fun getFilter(): Filter {
        return filter
    }

    private val filter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList: MutableList<ItemCart> = ArrayList()


            if (constraint.toString().isEmpty()) {
                Log.d("Adapter","Empty now")
                filteredList.addAll(item_detail_list_all)
                Log.d("Adapter","$item_detail_list_all")
            }else{
                for(item in item_detail_list_all){
                    if(item.name.toLowerCase().contains(constraint.toString().toLowerCase())){
                        filteredList.add(item)
                    }
                }
            }

            val filterResults : FilterResults = FilterResults()
            filterResults.values = filteredList
            return filterResults
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            item_detail_list.clear()
            item_detail_list.addAll(results?.values as Collection<ItemCart>)
            notifyDataSetChanged()
        }

    }

}