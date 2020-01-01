package com.brandonhogan.budgetscout.repository.converter

import androidx.room.TypeConverter
import com.brandonhogan.budgetscout.repository.TransactionType
import java.util.*


object Converters {

    /**
     * Converts a long to a calendar object
     */
    @TypeConverter
    @JvmStatic
    fun fromTimestamp(value: Long?): Calendar? = value?.let { calendarValue ->
        GregorianCalendar().also { calendar ->
            calendar.timeInMillis = calendarValue
        }
    }

    /**
     * Converts a calendar object to a long timestamp
     */
    @TypeConverter
    @JvmStatic
    fun toTimestamp(timestamp: Calendar?): Long? = timestamp?.timeInMillis


    /**
     * Converts a string to a TransactionType
     */
    @TypeConverter
    fun fromStringToTransactionType(transactionType: String): TransactionType = TransactionType.valueOf(transactionType)

    /**
     * Converts a TransactionType to a string
     */
    @TypeConverter
    fun fromTransactionTypeToString(transactionType: TransactionType): String = transactionType.value
}