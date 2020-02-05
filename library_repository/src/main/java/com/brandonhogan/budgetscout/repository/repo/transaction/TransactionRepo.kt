package com.brandonhogan.budgetscout.repository.repo.transaction

import androidx.annotation.WorkerThread
import com.brandonhogan.budgetscout.repository.entity.Transaction

/**
 * @Creator         Brandon Hogan
 * @Date            2020-02-04
 * @File            TransactionRepo
 * @Description     Handles all interaction with the transaction repository
 */

@WorkerThread
interface TransactionRepo {

    suspend fun insert(transaction: Transaction): Long
    suspend fun transfer(from: Transaction, to: Transaction): List<Long>
    suspend fun get(id: Long): Transaction
}