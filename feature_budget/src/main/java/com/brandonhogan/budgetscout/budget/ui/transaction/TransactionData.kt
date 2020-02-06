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
    // the transaction object
    var transaction: Transaction = Transaction.newInstance(),
    // if its a transfer, we would have a from envelope id set
    var fromEnvelopeId: Long? = null,
    // the group that the transaction is apart of
    var groupId: Long? = null): Parcelable