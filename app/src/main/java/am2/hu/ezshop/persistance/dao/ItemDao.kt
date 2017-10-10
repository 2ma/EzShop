package am2.hu.ezshop.persistance.dao

import am2.hu.ezshop.persistance.entity.Item
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*


@Dao
interface ItemDao {
    @Query("SELECT * FROM items WHERE shop = :shopName")
    fun getItemsForShop(shopName: String): LiveData<List<Item>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addItem(item: Item)

    @Delete
    fun deleteItem(item: Item)


}