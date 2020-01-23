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
import java.util.*


class BudgetRepoImpl(private val budgetDao: BudgetDao, private val groupDao: GroupDao, private val envelopeDao: EnvelopeDao): BudgetRepo, KoinComponent {

    override suspend fun deleteAll() {
        return budgetDao.deleteAll()
    }

    override suspend fun getAll(): List<Budget> {
        return budgetDao.getAll()
    }

    override suspend fun get(id: Long): Budget {
        return budgetDao.get(id)
    }

    override suspend fun insert(budget: Budget): List<Long> {
        return budgetDao.insert(budget)
    }

    /**
     * Gets the budget for the given budget id
     */
    override suspend fun getWithGroupsAndEnvelopes(id: Long): BudgetWithGroupsAndEnvelopes {
        return budgetDao.getWithGroupsAndEnvelopes(id)
    }

    /**
     * Gets the budget for the given calendar date
     */
    override suspend fun getWithGroupsAndEnvelopes(calendar: Calendar): BudgetWithGroupsAndEnvelopes {
        return budgetDao.getWithGroupsAndEnvelopes(calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR))
    }

    /**
     * Groups
     ***********************************************************************/

    /**
     * Inserts groups
     */
    override suspend fun insertGroup(groups: Group): List<Long> {
        return groupDao.insert(groups)
    }


    /**
     * Envelopes
     ***********************************************************************/

    /**
     * Insert envelopes
     */
    override suspend fun insertEnvelope(envelopes: Envelope): List<Long> {
        return envelopeDao.insert(envelopes)
    }

    /**
     * Gets the envelope based on the id
     */
    override suspend fun getEnvelope(id: Long): Envelope {
        return envelopeDao.get(id)
    }

    /**
     * Gets all envelopes for a given budget id
     */
    override suspend fun getEnvelopes(byBudgetId: Long): List<Envelope> {
        val budget = budgetDao.getWithGroupsAndEnvelopes(byBudgetId)

        // flattens all group envelopes into a single list
        return budget.groups.map { groupWithEnvelopes ->
            groupWithEnvelopes.envelopes
        }.flatten()
    }
}