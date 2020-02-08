package com.brandonhogan.budgetscout.core.utils

import java.text.DecimalFormat

class CurrencyUtils {
    companion object {
        /**
         * Helper function for returning the amount as an easy to read string
         */
        fun displayAsCurrency(amount: Double): String {
            val dec = DecimalFormat("#,###.00")
            return "$${dec.format(amount)}"
        }
    }
}