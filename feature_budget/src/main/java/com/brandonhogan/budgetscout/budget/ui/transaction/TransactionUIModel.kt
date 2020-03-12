package com.brandonhogan.budgetscout.budget.ui.transaction

import com.brandonhogan.budgetscout.repository.TransactionType
import com.brandonhogan.budgetscout.repository.entity.Transaction
import java.util.*

/**
 * @Creator         Brandon Hogan
 * @Date            2020-01-23
 * @File            TransactionUIModel
 * @Description     Contains data associated directly to the UI, not necessarily
 * the same as the transaction data. Its what gets displayed to the user.
 */

class TransactionUIModel {

    var transactionType: TransactionType = TransactionType.Expense
    var date: Calendar = Calendar.getInstance()
    var fromEnvelopName: String = ""
    var toEnvelopeName: String = ""
    var amount: Double = 0.00
    var note: String = ""
}