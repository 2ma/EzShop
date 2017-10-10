package am2.hu.ezshop.persistance.dao

import am2.hu.ezshop.persistance.entity.History
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*


@Dao
interface HistoryDao {
    @Query("SELECT * FROM History WHERE itemName LIKE '%' || :query || '%'")
    fun queryHistory(query: String): List<History>

    @Query("SELECT * FROM History")
    fun getFullHistory(): LiveData<List<History>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addHistoryItem(history: History)

    @Delete
    fun deleteHistoryItem(history: History)
}