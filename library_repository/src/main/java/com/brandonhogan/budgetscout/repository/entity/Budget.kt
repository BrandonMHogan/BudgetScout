package com.brandonhogan.budgetscout.repository.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.text.DecimalFormat
import java.time.Month
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

@Entity(tableName = Budget.NAME)
data class Budget (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = PROPERTY_ID) var id: Long = 0,
    // name of the budget
    @ColumnInfo(name = PROPERTY_NAME) var name: String,
    // calendar month associated for this budget
    @ColumnInfo(name = PROPERTY_MONTH) var month: Int = Calendar.getInstance().get(Calendar.MONTH),
    // calendar year associated for this budget
    @ColumnInfo(name = PROPERTY_YEAR) var year: Int = Calendar.getInstance().get(Calendar.YEAR),
    // created date
    @ColumnInfo(name = PROPERTY_CREATED) var created: Calendar = Calendar.getInstance(),
    // last updated date
    @ColumnInfo(name = PROPERTY_UPDATED) var updated: Calendar = Calendar.getInstance()
)
{

    // Ignored property. Current is a calculated value. It needs to be updated each time a
    // transaction happens, or whenever the budget is loaded
    @Ignore var current: Double = 0.0

    // Ignored property. Total is a calculated value. It needs to be updated each time a
    // transaction happens, or whenever the budget is loaded
    @Ignore var expenses: Double = 0.0

    // Ignored property. Total is a calculated value. It needs to be updated each time a
    // transaction happens, or whenever the budget is loaded
    @Ignore var income: Double = 0.0

    /**
     * Helper function to display the budgets month as a string
     */
    fun getDisplayMonth(): String {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.YEAR, year)

        return calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) ?: ""
    }

    /**
     * Helper function for returning the expense as an easy to read string
     */
    fun getDisplayExpenses(): String {
        val dec = DecimalFormat("#,###.00")
        return "$ ${dec.format(expenses)}"
    }

    /**
     * Helper function for returning the income as an easy to read string
     */
    fun getDisplayIncome(): String {
        val dec = DecimalFormat("#,###.00")
        return "$ ${dec.format(income)}"
    }

    companion object {
        const val NAME = "Budget"
        const val PROPERTY_NAME = "name"
        const val PROPERTY_ID = "id"
        const val PROPERTY_MONTH = "month"
        const val PROPERTY_YEAR = "year"
        const val PROPERTY_CREATED = "created"
        const val PROPERTY_UPDATED = "updated"
    }
}