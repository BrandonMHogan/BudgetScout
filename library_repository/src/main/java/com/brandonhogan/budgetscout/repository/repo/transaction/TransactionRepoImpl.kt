package com.brandonhogan.budgetscout.repository.repo.transaction

import com.brandonhogan.budgetscout.repository.dao.OperationDao
import com.brandonhogan.budgetscout.repository.dao.TransactionDao
import com.brandonhogan.budgetscout.repository.entity.Operation
import com.brandonhogan.budgetscout.repository.entity.Transaction
import org.koin.core.KoinComponent

/**
 * @Creator         Brandon Hogan
 * @Date            2020-02-04
 * @File            TransactionRepoImpl
 * @Description     Handles all interaction with the transaction dao
 */

class TransactionRepoImpl(private val transactionDao: TransactionDao, private val operationDao: OperationDao): TransactionRepo,
    KoinComponent {

    /**
     * Insert or Update Transaction
     */
    override suspend fun insert(transaction: Transaction): Long {
        return  transactionDao.insert(transaction)
    }


    @androidx.room.Transaction
    override suspend fun transfer(from: Transaction, to: Transaction): List<Long> {
        // creates the operation header and get its id
        val operationId = operationDao.insert(Operation())
        // sets the operation id for the from and to transactions
        from.operationId = operationId
        to.operationId = operationId
        // inserts them, and returns the list of new transaction ids
        return listOf(insert(from), insert(to))
    }

    /**
     * Gets the transaction based on the id
     */
    override suspend fun get(id: Long): Transaction {
        return transactionDao.get(id)
    }
}