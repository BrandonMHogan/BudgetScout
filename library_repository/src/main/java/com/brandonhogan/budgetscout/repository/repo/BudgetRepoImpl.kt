package com.brandonhogan.budgetscout.repository.repo

import com.brandonhogan.budgetscout.repository.dao.BudgetDao
import com.brandonhogan.budgetscout.repository.entity.Budget
import org.koin.core.KoinComponent


class BudgetRepoImpl(private val budgetDao: BudgetDao): BudgetRepo, KoinComponent {

    override fun getAll(): List<Budget> {
        return budgetDao.getAll()
    }

    override fun get(id: Long): Budget {
        return budgetDao.findById(id)
    }

    override suspend fun insert(budget: Budget): List<Long> {
        return budgetDao.insert(budget)
    }
}