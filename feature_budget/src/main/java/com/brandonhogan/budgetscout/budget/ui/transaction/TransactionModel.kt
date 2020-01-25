package com.brandonhogan.budgetscout.budget.ui.transaction

import com.brandonhogan.budgetscout.repository.TransactionType
import com.brandonhogan.budgetscout.repository.entity.Envelope
import com.brandonhogan.budgetscout.repository.entity.Transaction

/**
 * @Creator         Brandon Hogan
 * @Date            2020-01-23
 * @File            TransactionModel
 * @Description     {{ foo }}
 */

class TransactionModel {

    // the transaction object
    var transaction: Transaction = Transaction(type = TransactionType.Expense, envelopeId = -1L)
    // the budget that the transaction is apart of
    var budgetId: Long? = null
    // the group that the transaction is apart of
    var groupId: Long? = null

    // list of all envelopes that could be used
    var envelopes: List<Envelope>? = null
}