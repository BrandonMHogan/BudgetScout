package com.brandonhogan.budgetscout.repository.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.brandonhogan.budgetscout.repository.dao.base.BaseDao
import com.brandonhogan.budgetscout.repository.entity.Transaction

/**
 * @Creator         Brandon Hogan
 * @Date            2020-01-02
 * @File            UserDao
 * @Description     Data Access Object for the transaction table
 */

@Dao
interface TransactionDao: BaseDao<Transaction> {

    /**
     * Will get transaction
     */
    @Query("SELECT * FROM `${Transaction.NAME}` WHERE ${Transaction.PROPERTY_ID} IS :id")
    fun get(id: Long): Transaction

    /**
     * Will get all transactions in an envelope
     */
    @Query("SELECT * FROM `${Transaction.NAME}` WHERE ${Transaction.PROPERTY_ENVELOPE_ID} IS :id")
    fun getAllForEnvelope(id: Long): List<Transaction>

    /**
     * Will get the sum total of amount for all transactions where envelope id is the passed in id
     */
    @Query("SELECT SUM(${Transaction.PROPERTY_AMOUNT}) FROM `${Transaction.NAME}` WHERE ${Transaction.PROPERTY_ENVELOPE_ID} IS :id")
    fun getEnvelopeSum(id: Long): Double

}