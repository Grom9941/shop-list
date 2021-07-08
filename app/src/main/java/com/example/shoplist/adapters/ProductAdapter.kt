package com.example.shoplist.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R
import com.example.shoplist.data.ProductData
import com.google.android.material.textview.MaterialTextView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.product_item.view.*

class ProductAdapter(myData: List<ProductData>) :
    RecyclerView.Adapter<ProductAdapter.MyViewHolder>(), Filterable {

    var filterListResult: List<ProductData> = myData
    var filterListResult1: List<ProductData> = filterListResult
    private var context: Context? = null

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val image: ImageView = itemView.product_item_image
        val title: MaterialTextView = itemView.product_item_title
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            @SuppressLint("DefaultLocale")
            override fun performFiltering(charString: CharSequence?): FilterResults {
                val charSearch = charString.toString()
                filterListResult = if (charSearch.isEmpty())
                    filterListResult1
                else {
                    val resultList = ArrayList<ProductData>()
                    for (row in filterListResult1) {
                        if (row.title.toLowerCase().contains(charSearch.toLowerCase())) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResult = FilterResults()
                filterResult.values = filterListResult
                return filterResult
            }

            override fun publishResults(
                charSequence: CharSequence?,
                filterResults: FilterResults?
            ) {
                filterListResult = filterResults?.values as List<ProductData>
                notifyDataSetChanged()
            }

        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // create a new view
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.product_item,
            parent, false)
        context = parent.context
        // set the view's size, margins, paddings and layout parameters
        return MyViewHolder(itemView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        val currentElement = filterListResult[position]
        val url = "https://api.adorable.io/avatars/1.png"
        Picasso.get().load(url).into(holder.image)
        holder.title.text = currentElement.title
    }

    fun update(data: List<ProductData>){
        filterListResult = data
        filterListResult1 = data
        notifyDataSetChanged()
    }
    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = filterListResult.size
}