package com.brandonhogan.budgetscout.repository.repo.envelope

import androidx.annotation.WorkerThread
import com.brandonhogan.budgetscout.repository.entity.Envelope
import com.brandonhogan.budgetscout.repository.entity.relations.EnvelopeWithTransactions

/**
 * @Creator         Brandon Hogan
 * @Date            2020-02-04
 * @File            EnvelopeRepo
 * @Description     Handles all interaction with the envelope repository
 */

@WorkerThread
interface EnvelopeRepo {

    suspend fun insert(envelope: Envelope): Long

    suspend fun get(id: Long): Envelope
    suspend fun getByBudget(budgetId: Long): List<Envelope>
    suspend fun getEnvelopeWithTransactions(envelopeId: Long): EnvelopeWithTransactions
}