package am2.hu.ezshop.persistance.dao

import am2.hu.ezshop.persistance.entity.ListName
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*


@Dao
interface ListNameDao {
    @Query("SELECT * FROM listNames")
    fun getAllListNames(): LiveData<List<ListName>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addListName(listName: ListName)

    @Delete
    fun deleteListName(listName: ListName)
}