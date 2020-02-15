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
    // contains the transaction objects. If there is an operation, its more then one
    var transactions: List<Transaction> = listOf()): Parcelable