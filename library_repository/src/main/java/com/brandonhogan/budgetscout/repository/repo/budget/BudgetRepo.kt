package com.brandonhogan.budgetscout.repository.repo.budget

import androidx.annotation.WorkerThread
import com.brandonhogan.budgetscout.repository.entity.Budget
import com.brandonhogan.budgetscout.repository.entity.relations.BudgetWithGroupsAndEnvelopes

@WorkerThread
interface BudgetRepo {
    suspend fun getAll(): List<Budget>
    suspend fun get(id: Long): Budget
    suspend fun insert(budget: Budget): List<Long>
    suspend fun getWithGroupsAndEnvelopes(id: Long): BudgetWithGroupsAndEnvelopes
}