package am2.hu.ezshop.ui.main

import am2.hu.ezshop.R
import am2.hu.ezshop.persistance.entity.Shop
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var navAdapter: NavigationAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)

        setSupportActionBar(toolbar)

        supportActionBar?.run {
            setHomeAsUpIndicator(R.drawable.ic_menu_24dp)
            setDisplayHomeAsUpEnabled(true)
        }

        setupDrawer()

        mainViewModel.getNavMenuItems().observe(this, Observer { displayNavMenuItems(it) })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START)

            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupDrawer() {
        navigationRecycler.layoutManager = LinearLayoutManager(this)
        navAdapter = NavigationAdapter(this)
        {
            //TODO handle menu click
            toast(it.shopName)
            drawerLayout.closeDrawers()
        }

        navigationRecycler.adapter = navAdapter
    }

    private fun displayNavMenuItems(menuItems: List<Shop>?) {
        if (menuItems == null || menuItems.isEmpty()) {

            navAdapter.menuItems = listOf(Shop("No shops yet"))

        } else {

            navAdapter.menuItems = menuItems
            toolbar.title = menuItems[0].shopName

        }
    }

    fun newShopClick(view: View) {
        toast("New shop")
    }
}
