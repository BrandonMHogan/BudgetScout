package com.brandonhogan.budgetscout.budget.ui.transaction

import android.os.Parcelable
import com.brandonhogan.budgetscout.repository.TransactionType
import com.brandonhogan.budgetscout.repository.entity.Envelope
import com.brandonhogan.budgetscout.repository.entity.Transaction
import com.brandonhogan.budgetscout.repository.entity.relations.GroupWithEnvelopes
import kotlinx.android.parcel.Parcelize

/**
 * @Creator         Brandon Hogan
 * @Date            2020-01-23
 * @File            TransactionModel
 * @Description     {{ foo }}
 */

@Parcelize
data class TransactionData constructor(
    // a flag use to understand if this is an edit or not
    var isEdit: Boolean = false,
    // if this transaction is apart of an operation, then this will not be null
    var operationId: Long? = null,
    // every transaction requires a to transaction. Its where the expense or income is going to
    var toTransaction: Transaction? = null,
    // if operation id is set, then a to transaction will be required. Its where the transfer comes from
    var fromTransaction: Transaction? = null): Parcelable