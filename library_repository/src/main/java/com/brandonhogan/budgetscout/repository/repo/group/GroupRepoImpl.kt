package com.brandonhogan.budgetscout.repository.repo.group

import com.brandonhogan.budgetscout.repository.dao.BudgetDao
import com.brandonhogan.budgetscout.repository.dao.GroupDao
import com.brandonhogan.budgetscout.repository.entity.Envelope
import com.brandonhogan.budgetscout.repository.entity.Group
import com.brandonhogan.budgetscout.repository.entity.relations.GroupWithEnvelopes
import com.brandonhogan.budgetscout.repository.repo.envelope.EnvelopeRepo
import org.koin.core.KoinComponent

/**
 * @Creator         Brandon Hogan
 * @Date            2020-02-04
 * @File            GroupRepoImpl
 * @Description     {{ foo }}
 */

class GroupRepoImpl(private val budgetDao: BudgetDao, private val groupDao: GroupDao): GroupRepo,
    KoinComponent {

    /**
     * Insert Group
     */
    override suspend fun insert(group: Group): Long {
        return groupDao.insert(group)
    }

    /**
     * Gets the group based on the id
     */
    override suspend fun get(id: Long): Group {
        return groupDao.get(id)
    }

    /**
     * Gets all groups with their envelopes for a given budget id
     */
    override suspend fun getByBudget(budgetId: Long): List<GroupWithEnvelopes> {
        val budget = budgetDao.getWithGroupsAndEnvelopes(budgetId)
        return budget.groups
    }
}