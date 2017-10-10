package am2.hu.ezshop.persistance.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "shops")
data class Shop(
        @PrimaryKey val shopName: String)