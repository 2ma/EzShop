package am2.hu.ezshop.persistance.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "items", foreignKeys = arrayOf(ForeignKey(entity = ListName::class, parentColumns = arrayOf("listName"), childColumns = arrayOf("listName"), onDelete = CASCADE)))
data class Item(
        val itemName: String,
        val completed: Boolean,
        val listName: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}