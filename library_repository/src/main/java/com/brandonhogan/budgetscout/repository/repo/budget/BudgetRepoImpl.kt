package com.brandonhogan.budgetscout.repository.repo.budget

import com.brandonhogan.budgetscout.repository.dao.BudgetDao
import com.brandonhogan.budgetscout.repository.dao.EnvelopeDao
import com.brandonhogan.budgetscout.repository.dao.GroupDao
import com.brandonhogan.budgetscout.repository.entity.Budget
import com.brandonhogan.budgetscout.repository.entity.Envelope
import com.brandonhogan.budgetscout.repository.entity.Group
import com.brandonhogan.budgetscout.repository.entity.relations.BudgetWithGroupsAndEnvelopes
import com.brandonhogan.budgetscout.repository.repo.budget.BudgetRepo
import org.koin.core.KoinComponent


class BudgetRepoImpl(private val budgetDao: BudgetDao, private val groupDao: GroupDao, private val envelopeDao: EnvelopeDao): BudgetRepo, KoinComponent {

    override suspend fun getAll(): List<Budget> {
        return budgetDao.getAll()
    }

    override suspend fun get(id: Long): Budget {
        return budgetDao.get(id)
    }

    override suspend fun insert(budget: Budget): List<Long> {
        return budgetDao.insert(budget)
    }

    override suspend fun getWithGroupsAndEnvelopes(id: Long): BudgetWithGroupsAndEnvelopes {
        return budgetDao.getWithGroupsAndEnvelopes(id)
    }

    override suspend fun insertEnvelope(envelope: Envelope): List<Long> {
        return envelopeDao.insert(envelope)
    }

    override suspend fun insertGroup(group: Group): List<Long> {
        return groupDao.insert(group)
    }
}