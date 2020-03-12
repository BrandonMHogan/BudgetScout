package com.brandonhogan.budgetscout.repository.repo.envelope

import com.brandonhogan.budgetscout.repository.dao.BudgetDao
import com.brandonhogan.budgetscout.repository.dao.EnvelopeDao
import com.brandonhogan.budgetscout.repository.entity.Envelope
import com.brandonhogan.budgetscout.repository.entity.relations.EnvelopeWithTransactions
import org.koin.core.KoinComponent

/**
 * @Creator         Brandon Hogan
 * @Date            2020-02-04
 * @File            EnvelopeRepoImpl
 * @Description     {{ foo }}
 */


class EnvelopeRepoImpl(private val budgetDao: BudgetDao, private val envelopeDao: EnvelopeDao): EnvelopeRepo,
    KoinComponent {

    /**
     * Insert envelopes
     */
    override suspend fun insert(envelope: Envelope): Long {
        return envelopeDao.insert(envelope)
    }

    /**
     * Gets the envelope based on the id
     */
    override suspend fun get(id: Long): Envelope {
        return envelopeDao.get(id)
    }

    /**
     * Gets all envelopes for a given budget id
     */
    override suspend fun getByBudget(budgetId: Long): List<Envelope> {
        val budget = budgetDao.getWithGroupsAndEnvelopes(budgetId)

        // flattens all group envelopes into a single list
        return budget.groups.map { groupWithEnvelopes ->
            groupWithEnvelopes.envelopes
        }.flatten()
    }


    /**
     * Gets an envelope with all of its transactions
     */
    override suspend fun getEnvelopeWithTransactions(envelopeId: Long): EnvelopeWithTransactions {
        return envelopeDao.getWithTransactions(envelopeId)
    }
}