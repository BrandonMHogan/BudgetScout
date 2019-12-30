package com.brandonhogan.budgetscout.repository.repo.budget

import com.brandonhogan.budgetscout.repository.dao.BudgetDao
import com.brandonhogan.budgetscout.repository.entity.Budget
import com.brandonhogan.budgetscout.repository.repo.budget.BudgetRepo
import org.koin.core.KoinComponent


class BudgetRepoImpl(private val budgetDao: BudgetDao): BudgetRepo, KoinComponent {

    override suspend fun getAll(): List<Budget> {
        return budgetDao.getAll()
    }

    override suspend fun get(id: Long): Budget {
        return budgetDao.findById(id)
    }

    override suspend fun insert(budget: Budget): List<Long> {
        return budgetDao.insert(budget)
    }
}