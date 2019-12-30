package com.brandonhogan.budgetscout.repository.repo.budget

import androidx.annotation.WorkerThread
import com.brandonhogan.budgetscout.repository.entity.Budget

@WorkerThread
interface BudgetRepo {
    suspend fun getAll(): List<Budget>
    suspend fun get(id: Long): Budget
    suspend fun insert(budget: Budget): List<Long>
}