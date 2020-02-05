package com.brandonhogan.budgetscout.repository.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * @Creator         Brandon Hogan
 * @Date            2020-02-05
 * @File            Operation
 * @Description     Operation Header Table. Primarily used to associated transactions to a single
 * operation.
 * Example:
 *      If user transfers 100 from envelope A to envelope B.
 *      They both would have the same operation id, so we know they are connected transactions.
 *      If a transaction has no operation id, then its not a transfer of funds.
 */

@Parcelize
@Entity(tableName = Transaction.NAME)
data class Operation (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = PROPERTY_ID) var id: Long = -1,
    // created date
    @ColumnInfo(name = PROPERTY_CREATED) var created: Calendar = Calendar.getInstance(),
    // last updated date
    @ColumnInfo(name = PROPERTY_UPDATED) var updated: Calendar = Calendar.getInstance()): Parcelable
{
    companion object {
        // This needed to be something other then 'transaction' as that is a keyword in room. was
        // causing issues.
        const val NAME = "Operation"
        const val PROPERTY_ID = "id"
        const val PROPERTY_CREATED = "created"
        const val PROPERTY_UPDATED = "updated"
    }
}