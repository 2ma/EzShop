package am2.hu.ezshop.persistance.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity
data class History(
        @PrimaryKey val itemName: String)