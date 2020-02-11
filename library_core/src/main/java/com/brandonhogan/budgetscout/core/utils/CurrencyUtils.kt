package com.brandonhogan.budgetscout.core.utils

import java.text.DecimalFormat
import kotlin.math.abs

class CurrencyUtils {
    companion object {
        /**
         * Helper function for returning the amount as an easy to read string
         */
        fun displayAsCurrency(amount: Double): String {
            val isNegative = amount < 0
            val dec = DecimalFormat("#,###.00")
            val value = dec.format(abs(amount))

            return if (isNegative) { "-$$value" } else { "$$value" }
        }
    }
}