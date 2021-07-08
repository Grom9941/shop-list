package com.example.shoplist.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.shoplist.MainActivity
import com.example.shoplist.R
import com.example.shoplist.adapters.ShopListAdapter
import com.example.shoplist.data.ShopListData

/**
 * A simple [Fragment] subclass.
 */
class ShopListFragment(mainActivity: MainActivity) : Fragment() {
    private val callBackInterface = mainActivity
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.shop_list_fragment, container, false)
        val adapter = ShopListAdapter(ArrayList(), callBackInterface)
        generateDataShopList(adapter)

        val recyclerViewShopList = view.findViewById(R.id.shop_list_recycler_view) as RecyclerView

        recyclerViewShopList.adapter = adapter
        recyclerViewShopList.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerViewShopList.setHasFixedSize(true)

        return view
    }

    private fun generateDataShopList(adapter: ShopListAdapter) {
        val list = ArrayList<ShopListData>()

        val listSample = listOf(
            "New product",
            "Create list",
            "Try smth new",
            "Begin with our application",
            "Get started"
        )
        val listSample1 = listOf(
            "New productew productew productew productew productew product",
            "Create listeate listeate listeate listeate list",
            "Try smth newry smth newry smth newry smth new",
            "Begin with our application",
            "Get startedet startedet startedet startedet started"
        )

        for (j in 0 until 10) {
            for (i in 0 until 4) {
                val item = ShopListData(i, listSample[i], listSample1[i], null, null)
                list.add(item)
            }
        }
        adapter.update(list)
    }

}
