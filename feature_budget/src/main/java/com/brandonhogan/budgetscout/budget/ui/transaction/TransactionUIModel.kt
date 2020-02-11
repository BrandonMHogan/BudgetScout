package com.brandonhogan.budgetscout.budget.ui.transaction

import java.util.*

/**
 * @Creator         Brandon Hogan
 * @Date            2020-01-23
 * @File            TransactionUIModel
 * @Description     {{ foo }}
 */

class TransactionUIModel {

    var date: Calendar = Calendar.getInstance()
    var fromEnvelopName: String = ""
    var toEnvelopeName: String = ""
    var amount: Double = 0.00
    var note: String = ""

}