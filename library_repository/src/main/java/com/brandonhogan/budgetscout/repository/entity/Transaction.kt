package com.brandonhogan.budgetscout.repository.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.text.DecimalFormat
import java.util.*

/**
 * @Creator         Brandon Hogan
 * @Date            2019-12-31
 * @File            Transaction
 * @Description     Transactions are created every time an entry is added to an envelope or a
 * transfer between two envelopes happens.
 *
 * If a transaction is a transfer of funds between multiple envelopes, then an operation id
 * is required. Operation header is used to associated transactions
 */

@Parcelize
@Entity(tableName = Transaction.NAME)
data class Transaction (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = PROPERTY_ID) var id: Long = 0,
    // Main envelope id affected
    @ColumnInfo(name = PROPERTY_OPERATION_ID) var operationId: Long? = null,
    // Main envelope id affected
    @ColumnInfo(name = PROPERTY_ENVELOPE_ID) var envelopeId: Long,
    // Amount either transferred, added or edited by
    @ColumnInfo(name = PROPERTY_AMOUNT) var amount: Double = 0.0,
    // Note for the given transaction
    @ColumnInfo(name = PROPERTY_NOTE) var note: String = "",
    // date of the transaction
    @ColumnInfo(name = PROPERTY_DATE) var date: Calendar = Calendar.getInstance(),
    // created date
    @ColumnInfo(name = PROPERTY_CREATED) var created: Calendar = Calendar.getInstance(),
    // last updated date
    @ColumnInfo(name = PROPERTY_UPDATED) var updated: Calendar = Calendar.getInstance()): Parcelable
{
    companion object {
        // This needed to be something other then 'transaction' as that is a keyword in room. was
        // causing issues.
        const val NAME = "Transaction"
        const val PROPERTY_ENVELOPE_ID = "envelopeId"
        const val PROPERTY_OPERATION_ID = "operationId"
        const val PROPERTY_AMOUNT = "amount"
        const val PROPERTY_NOTE = "note"
        const val PROPERTY_ID = "id"
        const val PROPERTY_DATE = "date"
        const val PROPERTY_CREATED = "created"
        const val PROPERTY_UPDATED = "updated"

        fun newInstance(): Transaction {
            return Transaction(envelopeId = -1)
        }
    }
}