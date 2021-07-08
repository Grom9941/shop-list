package com.example.shoplist.dialogs

import android.content.Context
import android.util.Log
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R
import com.example.shoplist.adapters.ProductAdapter
import com.example.shoplist.data.ProductData
import com.google.android.material.bottomsheet.BottomSheetDialog
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import kotlinx.android.synthetic.main.product_bottom_sheet_dialog.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

object Dialogs : AppCompatActivity() {

    fun createDialogProduct(context: Context) {
        val dialog = BottomSheetDialog(context)
        dialog.setContentView(R.layout.product_bottom_sheet_dialog)
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()

        val recyclerViewProduct = dialog.findViewById<RecyclerView>(R.id.product_recycler_view)

        val adapter = ProductAdapter(ArrayList())
        generateDataProduct(adapter)

        recyclerViewProduct?.adapter = adapter
        recyclerViewProduct?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewProduct?.setHasFixedSize(true)

        //adapter.filter.filter("cr")

        createObservableSearch(dialog)
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { item ->
                Log.v(item.toString(), "click")
                adapter.filter.filter(item)

            }
    }

    private fun createObservableSearch(dialog: BottomSheetDialog): Observable<String> {
        val observableClick: Observable<String> = Observable.create { emitter ->
            dialog.product_search_view.setOnQueryTextListener(object :
                SearchView.OnQueryTextListener {

                override fun onQueryTextChange(newText: String): Boolean {
                    if (!emitter.isDisposed) {
                        emitter.onNext(newText)
                    }
                    //adapter.filter.filter(newText)
                    return false
                }

                override fun onQueryTextSubmit(query: String): Boolean {
                    //emitter.onNext(query)
                    //adapter.filter.filter(query)
                    return false
                }

            })
            emitter.setCancellable {
                //itemView.setOnClickListener(null)
            }
        }

        return observableClick
            .filter { it.length >= 2 || it.isEmpty() }
            .debounce(1, TimeUnit.SECONDS)
    }

    private fun generateDataProduct(adapter: ProductAdapter) {
        val list = ArrayList<ProductData>()

        val listSample = listOf(
            "New product",
            "Create list",
            "Try something new",
            "Begin with our application",
            "Get started"
        )

        for (i in 0 until 3) {
            val item = ProductData(i, listSample[i])
            list.add(item)
        }
        adapter.update(list)
    }
}