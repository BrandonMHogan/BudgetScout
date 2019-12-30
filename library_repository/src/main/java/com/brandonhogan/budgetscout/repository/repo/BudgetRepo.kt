package com.brandonhogan.budgetscout.repository.repo

import androidx.annotation.WorkerThread
import com.brandonhogan.budgetscout.repository.entity.Budget

@WorkerThread
interface BudgetRepo {
    fun getAll(): List<Budget>
    fun get(id: Long): Budget
    suspend fun insert(budget: Budget): List<Long>
}