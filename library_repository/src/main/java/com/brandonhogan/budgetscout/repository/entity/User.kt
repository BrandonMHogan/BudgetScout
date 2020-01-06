package com.brandonhogan.budgetscout.repository.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @Creator         Brandon Hogan
 * @Date            2019-12-19
 * @File            User
 * @Description     User object used to track the logged in user and their preferences
 */

@Entity(tableName = User.NAME)
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