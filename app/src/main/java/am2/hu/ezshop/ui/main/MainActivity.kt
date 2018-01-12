package am2.hu.ezshop.ui.main

import am2.hu.ezshop.R
import am2.hu.ezshop.persistance.entity.History
import am2.hu.ezshop.persistance.entity.Item
import am2.hu.ezshop.persistance.entity.ListName
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_content.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.toast
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var navAdapter: NavigationAdapter

    private lateinit var listAdapter: ItemListAdapter

    private lateinit var historyAdapter: ArrayAdapter<History>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var mainViewModel: MainViewModel

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

        setupListView()

        setupAutoComplete()

        mainViewModel.getNavMenuItems().observe(this, Observer { displayNavMenuItems(it) })

        mainViewModel.getSelectedList().observe(this, Observer { displaySelectedShop(it) })

        mainViewModel.getItems().observe(this, Observer { displayItems(it) })

        mainViewModel.getHistory().observe(this, Observer { addAutoCompleteList(it) })
    }

    private fun setupListView() {
        itemListView.layoutManager = LinearLayoutManager(this)

        listAdapter = ItemListAdapter(this)
        {
            mainViewModel.updateItem(it)
        }

        itemListView.adapter = listAdapter
    }

    private fun displayItems(items: List<Item>?) {
        if (items == null || items.isEmpty()) {
            emptyItemView.visibility = View.VISIBLE
            itemListView.visibility = View.GONE
        } else {
            listAdapter.itemList = items
            itemListView.visibility = View.VISIBLE
            emptyItemView.visibility = View.GONE
        }
    }

    private fun setupAutoComplete() {
        historyAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line)
        newItemView.setAdapter(historyAdapter)
        newItemView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            //addNewItem(historyAdapter.getItem(position).itemName)
            newItemView.setText("${historyAdapter.getItem(position).itemName} ")
            newItemView.setSelection(newItemView.text.length)
        }
    }

    private fun addAutoCompleteList(history: List<History>?) {
        historyAdapter.clear()
        history?.let { historyAdapter.addAll(history) }
        historyAdapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.main_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                true
            }
            R.id.menuDeleteList -> {
                alert {
                    message = getString(R.string.delete_list_confirmation)

                    positiveButton(android.R.string.ok)
                    {
                        mainViewModel.deleteList()
                    }
                    negativeButton(android.R.string.cancel)
                    {}
                }.show()
                true
            }
            R.id.menuDeleteCompleted -> {
                mainViewModel.deleteAllCompleted()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    private fun displaySelectedShop(shopName: String?) {
        shopName?.let {
            navAdapter.selectedShop = shopName
            supportActionBar?.title = shopName
        }
    }

    private fun setupDrawer() {
        navigationListView.layoutManager = LinearLayoutManager(this)

        navAdapter = NavigationAdapter(this) {
            supportActionBar?.title = it.listName
            mainViewModel.setSelectedList(it.listName)
            drawerLayout.closeDrawers()
        }

        navigationListView.adapter = navAdapter
    }

    private fun displayNavMenuItems(menuItems: List<ListName>?) {
        if (menuItems == null || menuItems.isEmpty()) {

            navigationListView.visibility = View.GONE
            noShopsView.visibility = View.VISIBLE

        } else {

            navAdapter.menuItems = menuItems
            navigationListView.visibility = View.VISIBLE
            noShopsView.visibility = View.GONE

        }
    }

    fun newShopClick(view: View?) {
        val shopView = LayoutInflater.from(this).inflate(R.layout.shop_name, null)
        val shopNameView = shopView.findViewById<EditText>(R.id.shopName)

        val alert = AlertDialog.Builder(this)
                .setPositiveButton(android.R.string.ok)
                { _, _ ->
                    if (shopNameView.text.trim().isEmpty()) {

                        toast(R.string.empty_list_name)

                    } else {

                        mainViewModel.addListName(shopNameView.text.trim().toString())

                    }
                }
                .setNegativeButton(android.R.string.cancel)
                { dialog, _ ->
                    dialog.dismiss()
                }
                .setView(shopView).create()

        alert.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)

        alert.show()

/*
        alert {
            positiveButton(android.R.string.ok) {

            }
            negativeButton(android.R.string.cancel) {
                it.dismiss()
            }
            customView = shopView

        }.show()*/

        drawerLayout.closeDrawers()
    }

    fun addNewItemClick(view: View) {
        addNewItem(newItemView.text.trim().toString())
    }

    private fun addNewItem(itemName: String) {
        if (mainViewModel.noListSelected()) {
            newShopClick(null)
            return
        }

        if (itemName.isNotEmpty()) {
            //TODO add to history
            mainViewModel.addNewItem(itemName)
            newItemView.setText("")
        }
    }

    override fun onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers()

            return
        }

        super.onBackPressed()
    }
}
