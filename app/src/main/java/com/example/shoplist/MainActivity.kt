package com.example.shoplist

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.shoplist.callback.CallBackInterface
import com.example.shoplist.dialogs.Dialogs
import com.example.shoplist.fragments.OneListFragment
import com.example.shoplist.fragments.ShopListFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

import kotlinx.android.synthetic.main.front_layout.*

class MainActivity : AppCompatActivity(), CallBackInterface {

    private var oneListFragment = OneListFragment()
    
    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.backdrop_layout)

        val shopListFragment = ShopListFragment(this)
        oneListFragment = OneListFragment()
        makeCurrentFragment(shopListFragment)

        val floatingActionButton: FloatingActionButton by lazy { floating_action_button }
/*
        observableClick.subscribe{ item ->
            Log.v(item.toString(), "click")
        }
*/
        floatingActionButton.clicks()
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe {
                sub_header.text = getString(R.string.title)
                makeCurrentFragment(oneListFragment)
            }

/*
        navigation.setOnNavigationItemSelectedListener{
            when (it.itemId) {
                R.id.nav_shopping_basket -> makeCurrentFragment(shopListFragment)
                R.id.nav_settings -> makeCurrentFragment(playlistFragment)
            }
            true
        }
*/
    }

    override fun onBackPressed() {
        sub_header.text = getString(R.string.sub_header)
        super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.bottom_navigation, menu)
        return true
    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            supportFragmentManager.executePendingTransactions()
            attach(fragment)
            addToBackStack(fragment.tag)
            commit()
        }

    override fun itemClick(title: String) {
        Toast.makeText(this, title, Toast.LENGTH_SHORT).show()
        sub_header.text = title
        makeCurrentFragment(oneListFragment)
        Dialogs.createDialogProduct(this)
    }
}
