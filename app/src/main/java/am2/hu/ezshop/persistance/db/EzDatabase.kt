package am2.hu.ezshop.persistance.db

import am2.hu.ezshop.persistance.dao.HistoryDao
import am2.hu.ezshop.persistance.dao.ItemDao
import am2.hu.ezshop.persistance.dao.ListNameDao
import am2.hu.ezshop.persistance.entity.History
import am2.hu.ezshop.persistance.entity.Item
import am2.hu.ezshop.persistance.entity.ListName
import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase


@Database(entities = arrayOf(ListName::class, History::class, Item::class), version = 1)
abstract class EzDatabase : RoomDatabase() {
    abstract fun listNameDao(): ListNameDao
    abstract fun historyDao(): HistoryDao
    abstract fun itemDao(): ItemDao


}