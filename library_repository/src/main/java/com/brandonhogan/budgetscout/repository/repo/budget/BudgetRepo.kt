package com.brandonhogan.budgetscout.repository.repo.budget

import androidx.annotation.WorkerThread
import com.brandonhogan.budgetscout.repository.entity.Budget
import com.brandonhogan.budgetscout.repository.entity.Envelope
import com.brandonhogan.budgetscout.repository.entity.Group
import com.brandonhogan.budgetscout.repository.entity.Transaction
import com.brandonhogan.budgetscout.repository.entity.relations.BudgetWithGroupsAndEnvelopes
import java.util.*

/**
 * @Creator         Brandon Hogan
 * @Date            2020-01-25
 * @File            EnvelopeRepo
 * @Description     Handles all interaction with the budget repository
 */

@WorkerThread
interface BudgetRepo {

    // should only be used for debugging
    suspend fun deleteAll()

    suspend fun insert(budget: Budget): Long

    suspend fun get(id: Long): Budget
    suspend fun getAll(): List<Budget>
    suspend fun getWithGroupsAndEnvelopes(id: Long): BudgetWithGroupsAndEnvelopes
    suspend fun getWithGroupsAndEnvelopes(calendar: Calendar): BudgetWithGroupsAndEnvelopes
}