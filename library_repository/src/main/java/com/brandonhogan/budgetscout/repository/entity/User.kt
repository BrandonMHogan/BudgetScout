package com.brandonhogan.budgetscout.repository.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @Creator         Brandon Hogan
 * @Date            2019-12-19
 * @File            Budget
 * @Description     {{ foo }}
 */

@Entity

data class User (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = PROPERTY_ID) var id: Long = 0,
    @ColumnInfo(name = PROPERTY_NAME) var name: String
)
{
    companion object {
        const val NAME = "User"
        const val PROPERTY_NAME = "name"
        const val PROPERTY_ID = "id"
    }
}