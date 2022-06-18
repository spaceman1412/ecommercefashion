package com.example.ecommercefashion

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommercefashion.models.ItemCart
import org.w3c.dom.Text

class SearchAdapter(val item_detail_list: MutableList<ItemCart>,
                    val context: Context) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>(), Filterable {
    private val item_detail_list_all = item_detail_list


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title : TextView = view.findViewById(R.id.name_textView_searchItem)
        val price : TextView = view.findViewById(R.id.price_textView_itemSearch)
        val image : ImageView = view.findViewById(R.id.primaryImage_imageView_searchItem)
        val layout : ConstraintLayout = view.findViewById(R.id.layout_itemSearch)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = item_detail_list[position].name
        holder.price.text = "$${item_detail_list[position].price}"
//        holder.image.setImageResource(item_detail_list[position].primaryImage)
        holder.layout.setOnClickListener {
            val intent = Intent(context,ItemDetail::class.java)
            intent.putExtra(MainActivity.USER_KEY,item_detail_list[position])
            context.startActivity(intent)
        }
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