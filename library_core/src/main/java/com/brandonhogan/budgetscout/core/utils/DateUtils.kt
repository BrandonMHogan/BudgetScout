package com.brandonhogan.budgetscout.core.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DateUtils {
    companion object {
        val suffixes = arrayOf(
            "th",
            "st",
            "nd",
            "rd",
            "th",
            "th",
            "th",
            "th",
            "th",
            "th",
            "th",
            "th",
            "th",
            "th",
            "th",
            "th",
            "th",
            "th",
            "th",
            "th",
            "th",
            "st",
            "nd",
            "rd",
            "th",
            "th",
            "th",
            "th",
            "th",
            "th",
            "th",
            "st"
        )

        /**
         * Helper function to display the budgets month as a string
         */
        fun getSimpleDayOfMonth(date: Calendar): String {
            val dateFormat: DateFormat = SimpleDateFormat("EEEE dd", Locale.ENGLISH)
            val day = date.get(Calendar.DAY_OF_MONTH)
            return dateFormat.format(date.time) + suffixes[day]
        }
    }
}