package am2.hu.ezshop.persistance.dao

import am2.hu.ezshop.persistance.entity.Shop
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*


@Dao
interface ShopDao {
    @Query("SELECT * FROM shops")
    fun getAllShops(): LiveData<List<Shop>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addShop(shop: Shop)

    @Delete
    fun deleteShop(shop: Shop)
}