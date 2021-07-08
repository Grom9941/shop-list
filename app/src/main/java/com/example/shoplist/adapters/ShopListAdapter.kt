package com.example.shoplist.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R
import com.example.shoplist.callback.CallBackInterface
import com.example.shoplist.data.ShopListData
import com.google.android.material.textview.MaterialTextView
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.core.Observable
import kotlinx.android.synthetic.main.shop_list_item.view.*


class ShopListAdapter(myData: List<ShopListData>, private var clickListener: CallBackInterface) :
    RecyclerView.Adapter<ShopListAdapter.MyViewHolder>() {

    private var filterListResult: List<ShopListData> = myData
    private var context: Context? = null
    private var positionClick: Int = 0

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: MaterialTextView = itemView.shop_list_item_title
        val description: MaterialTextView = itemView.shop_list_item_description
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // create a new view
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.shop_list_item,
            parent, false
        )
        context = parent.context

        observableClick(itemView)//.subscribeOn(Schedulers.io())
            .subscribe { item ->
                Log.v(item.toString(), "click")
                val title: String = itemView.shop_list_item_title.text as String
                clickListener.itemClick(title)
            }

        // set the view's size, margins, paddings and layout parameters
        return MyViewHolder(itemView)
    }

    private fun observableClick(itemView: View): Observable<Boolean> {
        return Observable.create { emitter ->
            itemView.clicks()
                .subscribe {
                    emitter.onNext(true)
                }
            emitter.setCancellable {
                //itemView.setOnClickListener(null)
            }
        }
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        val currentElement = filterListResult[position]
        holder.title.text = currentElement.title
        holder.description.text = currentElement.description
        positionClick = position
    }

    fun update(data: List<ShopListData>) {
        filterListResult = data
        notifyDataSetChanged()
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = filterListResult.size
}