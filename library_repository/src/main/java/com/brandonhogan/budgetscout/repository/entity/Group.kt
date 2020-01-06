package com.brandonhogan.budgetscout.repository.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * @Creator         Brandon Hogan
 * @Date            2019-12-31
 * @File            Group
 * @Description     Groups are containers for your Envelopes so they can be grouped together.
 * Example: House, Transportation, Food, etc.
 */

@Entity(tableName = Group.NAME)
data class Group (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = PROPERTY_ID) var id: Long = 0,
    // Category name
    @ColumnInfo(name = PROPERTY_NAME) var name: String,
    // Colour associated to the category
    @ColumnInfo(name = PROPERTY_COLOUR) var colour: Int = 0,
    // Foreign key reference to its budget
    @ColumnInfo(name = PROPERTY_BUDGET_ID) var budgetId: Long
)
{
    companion object {
        const val NAME = "Group"
        const val PROPERTY_NAME = "name"
        const val PROPERTY_COLOUR = "colour"
        const val PROPERTY_ID = "id"
        const val PROPERTY_BUDGET_ID = "budgetId"
    }
}