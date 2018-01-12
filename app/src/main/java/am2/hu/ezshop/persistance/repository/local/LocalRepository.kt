package am2.hu.ezshop.persistance.repository.local

import am2.hu.ezshop.AppExecutors
import am2.hu.ezshop.extensions.getValue
import am2.hu.ezshop.extensions.putValue
import am2.hu.ezshop.persistance.dao.HistoryDao
import am2.hu.ezshop.persistance.dao.ItemDao
import am2.hu.ezshop.persistance.dao.ListNameDao
import am2.hu.ezshop.persistance.entity.History
import am2.hu.ezshop.persistance.entity.Item
import am2.hu.ezshop.persistance.entity.ListName
import android.arch.lifecycle.LiveData
import android.content.SharedPreferences
import android.support.annotation.WorkerThread
import javax.inject.Inject


class LocalRepository @Inject constructor(private val listNameDao: ListNameDao, private val itemDao: ItemDao, private val historyDao: HistoryDao, private val appExecutors: AppExecutors, val sharedPreferences: SharedPreferences) {

    companion object {
        val SELECTED_LIST = "selected_list"
    }

    fun getSelectedList(): String = sharedPreferences.getValue(SELECTED_LIST, "")

    fun setSelectedShop(shopName: String) {
        sharedPreferences.putValue(SELECTED_LIST, shopName)
    }

    fun getAllListNames(): LiveData<List<ListName>> = listNameDao.getAllListNames()

    fun addListName(listName: ListName) {
        appExecutors.diskIO.execute { listNameDao.addListName(listName) }
    }

    fun deleteListName(listName: ListName) {
        appExecutors.diskIO.execute { listNameDao.deleteListName(listName) }
    }

    fun deleteAllCompleted(listName: String) {
        appExecutors.diskIO.execute { itemDao.deleteAllCompleted(listName) }
    }

    fun getItemsForList(shopName: String) = itemDao.getItemsForShop(shopName)

    fun addItem(item: Item) {
        appExecutors.diskIO.execute {
            itemDao.addItem(item)
        }
    }

    fun deleteItem(item: Item) {
        appExecutors.diskIO.execute { itemDao.deleteItem(item) }
    }

    fun updateItem(item: Item) {
        appExecutors.diskIO.execute { itemDao.updateItem(item) }
    }

    @WorkerThread
    fun queryHistory(query: String) = historyDao.queryHistory(query)

    fun getFullHistory() = historyDao.getFullHistory()

    fun addItemToHistory(history: History) {
        appExecutors.diskIO.execute { historyDao.addHistoryItem(history) }
    }

    fun deleteItemFromHistory(history: History) {
        appExecutors.diskIO.execute { historyDao.deleteHistoryItem(history) }
    }

}