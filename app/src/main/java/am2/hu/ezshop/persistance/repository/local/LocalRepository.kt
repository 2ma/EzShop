package am2.hu.ezshop.persistance.repository.local

import am2.hu.ezshop.AppExecutors
import am2.hu.ezshop.persistance.dao.HistoryDao
import am2.hu.ezshop.persistance.dao.ItemDao
import am2.hu.ezshop.persistance.dao.ShopDao
import am2.hu.ezshop.persistance.entity.History
import am2.hu.ezshop.persistance.entity.Item
import am2.hu.ezshop.persistance.entity.Shop
import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread
import javax.inject.Inject


class LocalRepository @Inject constructor(private val shopDao: ShopDao, private val itemDao: ItemDao, private val historyDao: HistoryDao, private val appExecutors: AppExecutors) {
    fun getAllShops(): LiveData<List<Shop>> = shopDao.getAllShops()

    fun addShop(shop: Shop) {
        appExecutors.run { shopDao.addShop(shop) }
    }

    fun deleteShop(shop: Shop) {
        appExecutors.run { shopDao.deleteShop(shop) }
    }

    fun getItemsForShop(shopName: String) = itemDao.getItemsForShop(shopName)

    fun addItem(item: Item) {
        appExecutors.run {
            itemDao.addItem(item)
        }
    }

    fun deleteItem(item: Item) {
        appExecutors.run { itemDao.deleteItem(item) }
    }

    @WorkerThread
    fun queryHistory(query: String) = historyDao.queryHistory(query)

    fun getFullHistory() = historyDao.getFullHistory()

    fun addItemToHistory(history: History) {
        appExecutors.run { historyDao.addHistoryItem(history) }
    }

    fun deleteItemFromHistory(history: History) {
        appExecutors.run { historyDao.deleteHistoryItem(history) }
    }

}