package com.brandonhogan.budgetscout.repository.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * @Creator         Brandon Hogan
 * @Date            2019-12-19
 * @File            Budget
 * @Description     A budget is a collection of groups and their envelopes for a given month.
 * A new budget is created for each month, often copying over the previous month
 *
 * Budget contains a name, a created date
 */

@Entity
data class Budget (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = PROPERTY_ID) var id: Long = 0,
    // name of the budget
    @ColumnInfo(name = PROPERTY_NAME) var name: String,
    // calendar month and year associated for this budget
    @ColumnInfo(name = PROPERTY_CALENDAR) var calendar: Calendar = Calendar.getInstance(),
    // created date
    @ColumnInfo(name = PROPERTY_CREATED) var created: Calendar = Calendar.getInstance(),
    // last updated date
    @ColumnInfo(name = PROPERTY_UPDATED) var updated: Calendar = Calendar.getInstance()
)
{
    companion object {
        const val NAME = "Budget"
        const val PROPERTY_NAME = "name"
        const val PROPERTY_ID = "id"
        const val PROPERTY_CALENDAR = "calendar"
        const val PROPERTY_CREATED = "created"
        const val PROPERTY_UPDATED = "updated"
    }
}