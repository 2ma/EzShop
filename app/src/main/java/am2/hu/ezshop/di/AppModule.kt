package am2.hu.ezshop.di

import am2.hu.ezshop.persistance.dao.HistoryDao
import am2.hu.ezshop.persistance.dao.ItemDao
import am2.hu.ezshop.persistance.dao.ShopDao
import am2.hu.ezshop.persistance.db.EzDatabase
import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {
    @Provides
    @Singleton
    fun providesEzDatabase(): EzDatabase =

            Room.databaseBuilder(application, EzDatabase::class.java, "ez_shop.db")
                    .build()


    @Provides
    @Singleton
    fun providesContext(): Context = application

    @Provides
    @Singleton
    fun providesShopDao(database: EzDatabase): ShopDao = database.shopDao()

    @Provides
    @Singleton
    fun providesItemDao(database: EzDatabase): ItemDao = database.itemDao()

    @Provides
    @Singleton
    fun providesHistorypDao(database: EzDatabase): HistoryDao = database.historyDao()
}