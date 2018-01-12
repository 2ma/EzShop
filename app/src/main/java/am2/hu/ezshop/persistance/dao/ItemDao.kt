package am2.hu.ezshop.persistance.dao

import am2.hu.ezshop.persistance.entity.Item
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*


@Dao
interface ItemDao {
    @Query("SELECT * FROM items WHERE listName = :listName")
    fun getItemsForShop(listName: String): LiveData<List<Item>>

    @Query("DELETE FROM items WHERE listName = :listName AND completed = 1")
    fun deleteAllCompleted(listName: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addItem(item: Item)

    @Delete
    fun deleteItem(item: Item)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateItem(item: Item)


}