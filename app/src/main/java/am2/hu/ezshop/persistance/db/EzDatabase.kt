package am2.hu.ezshop.persistance.db

import am2.hu.ezshop.persistance.dao.HistoryDao
import am2.hu.ezshop.persistance.dao.ItemDao
import am2.hu.ezshop.persistance.dao.ShopDao
import am2.hu.ezshop.persistance.entity.History
import am2.hu.ezshop.persistance.entity.Item
import am2.hu.ezshop.persistance.entity.Shop
import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase


@Database(entities = arrayOf(Shop::class, History::class, Item::class), version = 1)
abstract class EzDatabase : RoomDatabase() {
    abstract fun shopDao(): ShopDao
    abstract fun historyDao(): HistoryDao
    abstract fun itemDao(): ItemDao


}