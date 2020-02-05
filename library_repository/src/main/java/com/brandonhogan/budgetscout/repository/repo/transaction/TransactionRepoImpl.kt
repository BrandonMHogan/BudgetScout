package com.brandonhogan.budgetscout.repository.repo.transaction

import com.brandonhogan.budgetscout.repository.dao.EnvelopeDao
import com.brandonhogan.budgetscout.repository.dao.TransactionDao
import com.brandonhogan.budgetscout.repository.entity.Transaction
import org.koin.core.KoinComponent

/**
 * @Creator         Brandon Hogan
 * @Date            2020-02-04
 * @File            TransactionRepoImpl
 * @Description     Handles all interaction with the transaction dao
 */

class TransactionRepoImpl(private val transactionDao: TransactionDao, private val envelopeDao: EnvelopeDao): TransactionRepo,
    KoinComponent {

    /**
     * Insert or Update Transaction
     */
    override suspend fun insert(transaction: Transaction): Long {
        return  transactionDao.insert(transaction)
    }

    /**
     * Gets the transaction based on the id
     */
    override suspend fun get(id: Long): Transaction {
        return transactionDao.get(id)
    }
}