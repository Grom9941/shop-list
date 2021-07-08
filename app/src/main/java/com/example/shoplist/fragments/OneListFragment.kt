package com.example.shoplist.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shoplist.R

/**
 * A simple [Fragment] subclass.
 */
class OneListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //        Dialogs.createDialogProduct(context!!)
        return inflater.inflate(R.layout.one_list_fragment, container, false)
    }
}
