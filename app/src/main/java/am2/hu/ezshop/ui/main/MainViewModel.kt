package am2.hu.ezshop.ui.main

import am2.hu.ezshop.AppExecutors
import am2.hu.ezshop.persistance.entity.History
import am2.hu.ezshop.persistance.entity.Item
import am2.hu.ezshop.persistance.entity.ListName
import am2.hu.ezshop.persistance.repository.local.LocalRepository
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import javax.inject.Inject


class MainViewModel @Inject constructor(val localRepository: LocalRepository, val appExecutors: AppExecutors) : ViewModel() {

    private val navMenuItems = localRepository.getAllListNames()
    private val selectedList: MutableLiveData<String> = MutableLiveData()
    private val itemList: LiveData<List<Item>> = Transformations.switchMap(selectedList) {
        localRepository.getItemsForList(it)
    }


    private val history: LiveData<List<History>> = localRepository.getFullHistory()

    init {
        selectedList.value = localRepository.getSelectedList()
    }

    fun getNavMenuItems(): LiveData<List<ListName>> = navMenuItems

    fun addListName(name: String) {
        setSelectedList(name)
        localRepository.addListName(ListName(name))
    }

    fun deleteList() {
        val listName = selectedList.value
        listName?.let {
            localRepository.deleteListName(ListName(listName))
            val navItems = navMenuItems.value
            if (navItems != null && navItems.size > 1) {
                setSelectedList(if (navItems[0].listName == listName) navItems[1].listName else navItems[0].listName)
            } else {
                setSelectedList("")
            }
        }
    }

    fun deleteAllCompleted() {
        val listName = selectedList.value
        listName?.let {
            localRepository.deleteAllCompleted(listName)
        }
    }

    fun setSelectedList(listName: String) {
        localRepository.setSelectedShop(listName)
        selectedList.value = listName
    }

    fun getSelectedList() = selectedList

    fun getItems() = itemList

    fun addNewItem(itemName: String) {
        //TODO add listName if no shops available from ui
        val shopName = selectedList.value
        if (shopName != null && shopName.isNotEmpty()) {
            localRepository.addItem(Item(itemName, false, shopName))
            localRepository.addItemToHistory(History(itemName))
        }
    }

    fun updateItem(item: Item) {
        localRepository.updateItem(item)
    }

    fun getHistory(): LiveData<List<History>> = history

    fun noListSelected(): Boolean {
        return selectedList.value.isNullOrEmpty()
    }


}