package com.brandonhogan.budgetscout.repository.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.brandonhogan.budgetscout.repository.dao.base.BaseDao
import com.brandonhogan.budgetscout.repository.entity.Envelope
import java.lang.Exception

/**
 * @Creator         Brandon Hogan
 * @Date            2019-30-19
 * @File            UserDao
 * @Description     Data Access Object for the user table
 */

@Dao
interface EnvelopeDao: BaseDao<Envelope> {

    /**
     * Will get envelope
     */
    @Query("SELECT * FROM ${Envelope.NAME} WHERE ${Envelope.PROPERTY_ID} IS :id")
    fun get(id: Long): Envelope

    /**
     * Will get all envelopes
     */
    @Query("SELECT * FROM ${Envelope.NAME}")
    fun getAll(): List<Envelope>

    /**
     * Updates just the total value of the envelope
     */
    @Query("UPDATE ${Envelope.NAME} SET ${Envelope.PROPERTY_TOTAL} = :total WHERE ${Envelope.PROPERTY_ID} = :id")
    fun updateTotal(id: Long, total: Double)

    /**
     * Updates just the current value of the envelope
     */
    @Query("UPDATE ${Envelope.NAME} SET ${Envelope.PROPERTY_CURRENT} = :current WHERE ${Envelope.PROPERTY_ID} = :id")
    fun updateCurrent(id: Long, current: Double)

    /**
     * Transfers the passed in total from one envelope to another.
     * This is useful so that the user doesn't need to rebalance, its just moving totals
     */
    @Transaction
    fun transferTotal(from: Envelope, to: Envelope, total: Double) {

        // TODO: Determine a better way for the transaction to return an error.
        // specifically if there is something that needs to be described to the user
        if (from.total < total) {
            throw Exception("Cannot transfer more then available")
        }

        // subtracts total from the `from` envelope and adds it to the `to` envelope
        updateTotal(from.id, from.total - total)
        updateTotal(to.id, to.total + total)
    }
}