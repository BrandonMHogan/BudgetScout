package com.brandonhogan.budgetscout.repository.repo

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.brandonhogan.budgetscout.repository.dao.BudgetDao

import com.brandonhogan.budgetscout.repository.entity.Budget
import org.koin.core.KoinComponent


class BudgetRepoImpl(private val budgetDao: BudgetDao): BudgetRepo, KoinComponent {

    override suspend fun get(id: Int): LiveData<Budget> {
        return budgetDao.findById(id)
    }

    @WorkerThread
    override suspend fun insert(budget: Budget){
        budgetDao.insert(budget)
    }
}