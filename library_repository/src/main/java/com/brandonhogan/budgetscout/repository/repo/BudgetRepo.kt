package com.brandonhogan.budgetscout.repository.repo

import androidx.lifecycle.LiveData
import com.brandonhogan.budgetscout.repository.entity.Budget

interface BudgetRepo {
    suspend fun get(id: Int): LiveData<Budget>
    suspend fun insert(budget: Budget)
}