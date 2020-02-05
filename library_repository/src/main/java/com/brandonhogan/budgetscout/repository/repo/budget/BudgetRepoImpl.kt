package com.brandonhogan.budgetscout.repository.repo.budget

import com.brandonhogan.budgetscout.repository.dao.BudgetDao
import com.brandonhogan.budgetscout.repository.dao.EnvelopeDao
import com.brandonhogan.budgetscout.repository.dao.GroupDao
import com.brandonhogan.budgetscout.repository.dao.TransactionDao
import com.brandonhogan.budgetscout.repository.entity.Budget
import com.brandonhogan.budgetscout.repository.entity.Envelope
import com.brandonhogan.budgetscout.repository.entity.Group
import com.brandonhogan.budgetscout.repository.entity.Transaction
import com.brandonhogan.budgetscout.repository.entity.relations.BudgetWithGroupsAndEnvelopes
import com.brandonhogan.budgetscout.repository.repo.budget.BudgetRepo
import org.koin.core.KoinComponent
import java.util.*


class BudgetRepoImpl(private val budgetDao: BudgetDao, private val groupDao: GroupDao, private val envelopeDao: EnvelopeDao, private val transactionDao: TransactionDao): BudgetRepo, KoinComponent {

    override suspend fun deleteAll() {
        return budgetDao.deleteAll()
    }

    override suspend fun getAll(): List<Budget> {
        return budgetDao.getAll()
    }

    override suspend fun get(id: Long): Budget {
        return budgetDao.get(id)
    }

    override suspend fun insert(budget: Budget): Long {
        return budgetDao.insert(budget)
    }

    /**
     * Gets the budget for the given budget id
     */
    override suspend fun getWithGroupsAndEnvelopes(id: Long): BudgetWithGroupsAndEnvelopes {
        val budget = budgetDao.getWithGroupsAndEnvelopes(id)
        budget.groups.forEach { group ->

            group.envelopes.forEach { envelope ->
                envelope.current = transactionDao.getEnvelopeSum(envelope.id)
                // totals up the envelope current values to make the groups total
                group.group.current += envelope.current
            }
        }

        return budget
    }

    /**
     * Gets the budget for the given calendar date
     */
    override suspend fun getWithGroupsAndEnvelopes(calendar: Calendar): BudgetWithGroupsAndEnvelopes {
        return budgetDao.getWithGroupsAndEnvelopes(calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR))
    }
}