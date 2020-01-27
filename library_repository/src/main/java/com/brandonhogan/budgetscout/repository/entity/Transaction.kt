package com.brandonhogan.budgetscout.repository.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.brandonhogan.budgetscout.repository.TransactionType
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * @Creator         Brandon Hogan
 * @Date            2019-12-31
 * @File            Transaction
 * @Description     Transactions are created every time an entry is added to an envelope or a
 * transfer between two envelopes happens. This is mostly used for self auditing
 */

@Parcelize
@Entity(tableName = Transaction.NAME)
data class Transaction (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = PROPERTY_ID) var id: Long = 0,
    // Main envelope id affected
    @ColumnInfo(name = PROPERTY_ENVELOPE_ID) var envelopeId: Long,
    // If this transaction is a transfer type, the transferred from envelope id is stored
    @ColumnInfo(name = PROPERTY_FROM_ENVELOPE_ID) var fromEnvelopeId: Long? = null,
    // Transaction Type
    @ColumnInfo(name = PROPERTY_TYPE) var type: TransactionType,
    // Amount either transferred, added or edited by
    @ColumnInfo(name = PROPERTY_AMOUNT) var amount: Double = 0.0,
    // created date
    @ColumnInfo(name = PROPERTY_CREATED) var created: Calendar = Calendar.getInstance(),
    // last updated date
    @ColumnInfo(name = PROPERTY_UPDATED) var updated: Calendar = Calendar.getInstance()): Parcelable
{
    companion object {
        const val NAME = "Transaction"
        const val PROPERTY_ENVELOPE_ID = "envelopeId"
        const val PROPERTY_FROM_ENVELOPE_ID = "fromEnvelopeId"
        const val PROPERTY_TYPE = "type"
        const val PROPERTY_AMOUNT = "amount"
        const val PROPERTY_ID = "id"
        const val PROPERTY_CREATED = "created"
        const val PROPERTY_UPDATED = "updated"

        fun newInstance(): Transaction {
            return Transaction(envelopeId = -1, type = TransactionType.Expense)
        }
    }
}