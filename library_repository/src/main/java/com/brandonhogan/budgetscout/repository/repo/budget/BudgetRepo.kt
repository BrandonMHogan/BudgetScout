package com.brandonhogan.budgetscout.repository.repo.budget

import androidx.annotation.WorkerThread
import com.brandonhogan.budgetscout.repository.entity.Budget
import com.brandonhogan.budgetscout.repository.entity.Envelope
import com.brandonhogan.budgetscout.repository.entity.Group
import com.brandonhogan.budgetscout.repository.entity.relations.BudgetWithGroupsAndEnvelopes
import java.util.*

@WorkerThread
interface BudgetRepo {

    // should only be used for debugging
    suspend fun deleteAll()

    suspend fun getAll(): List<Budget>
    suspend fun get(id: Long): Budget
    suspend fun insert(budget: Budget): List<Long>
    suspend fun getWithGroupsAndEnvelopes(id: Long): BudgetWithGroupsAndEnvelopes
    suspend fun getWithGroupsAndEnvelopes(calendar: Calendar): BudgetWithGroupsAndEnvelopes

    suspend fun insertGroup(groups: Group) : List<Long>
    suspend fun insertEnvelope(envelopes: Envelope) : List<Long>

    suspend fun getEnvelope(id: Long): Envelope
    suspend fun getEnvelopes(byBudgetId: Long): List<Envelope>
}