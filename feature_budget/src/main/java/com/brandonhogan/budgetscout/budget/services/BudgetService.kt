package com.brandonhogan.budgetscout.budget.services

import androidx.lifecycle.MutableLiveData
import com.brandonhogan.budgetscout.core.bases.BaseService
import com.brandonhogan.budgetscout.repository.entity.Envelope
import com.brandonhogan.budgetscout.repository.entity.Transaction
import com.brandonhogan.budgetscout.repository.entity.relations.BudgetWithGroupsAndEnvelopes
import com.brandonhogan.budgetscout.repository.entity.relations.EnvelopeWithTransactions
import com.brandonhogan.budgetscout.repository.repo.budget.BudgetRepo
import com.brandonhogan.budgetscout.repository.repo.envelope.EnvelopeRepo
import com.brandonhogan.budgetscout.repository.repo.transaction.TransactionRepo
import com.brandonhogan.budgetscout.repository.testing.BudgetCreator
import kotlinx.coroutines.*
import java.util.*


/**
 * Budget Service
 *
 * Maintains the active running budget and any data refresh or changes required
 */
class BudgetService(private val budgetRepo: BudgetRepo, private val envelopeRepo: EnvelopeRepo, private val transactionRepo: TransactionRepo, private val budgetCreator: BudgetCreator): BaseService() {

    /**
     * Observable by its view
     */
    val budget: MutableLiveData<BudgetWithGroupsAndEnvelopes> by lazy {
        MutableLiveData<BudgetWithGroupsAndEnvelopes>()
    }

    /**
     * When an envelope is selected, store it here, so that its data can be shared
     * between all sub views.
     *
     * Should be cleared once an envelope is unselected
     */
    val selectedEnvelope: MutableLiveData<EnvelopeWithTransactions> by lazy {
        MutableLiveData<EnvelopeWithTransactions>()
    }



    /**
     * On init, will load the active budget and post it
     */
    init {
        serviceScope.launch(exceptionHandler) {
            loadBudget()
        }
    }

    /**
     * Loads the budget from the repository, in the background thread
     */
    private suspend fun loadBudget(calendar: Calendar = Calendar.getInstance()) = withContext(
        Dispatchers.IO) {

        val loadedBudget = budgetRepo.getWithGroupsAndEnvelopes(calendar)

        if (loadedBudget != null) {
            budget.postValue(loadedBudget)
        }
        else {
            //TODO: This shouldn't be done here. Instead, navigate to the new budget screen to make a new budget
            budgetCreator.createBasicBudget(true, calendar = calendar)
            budget.postValue(budgetRepo.getWithGroupsAndEnvelopes(calendar))
        }
    }

    /**
     * Will mark an envelope as selected, storing it locally in the service, so that
     * any model listening to it can share the data and changes.
     */
    suspend fun selectEnvelope(id: Long) = withContext(Dispatchers.IO) {
        selectedEnvelope.postValue(envelopeRepo.getEnvelopeWithTransactions(id))
    }

    /**
     * Gets all transactions for an envelope
     */
    suspend fun getTransactionsForEnvelope(id: Long) = withContext(Dispatchers.IO) {
        transactionRepo.getByEnvelope(id)
    }

    /**
     * Updates the transaction object in realm, and adjusts
     */
    suspend fun setTransaction(transaction: Transaction) = withContext(Dispatchers.IO) {
        transactionRepo.insert(transaction)
    }

    /**
     * Updates the transaction object in realm, and adjusts
     */
    suspend fun setTransfer(from: Transaction, to: Transaction) = withContext(Dispatchers.IO) {
        transactionRepo.transfer(from, to)
    }

    /**
     * Will get all transactions associated to an operation
     */
    suspend fun getEnvelopesByOperation(id: Long): List<Transaction>? {
        return transactionRepo.getByOperation(id)
    }

    /**
     * Flattens all the groups, making a single list of all the envelopes.
     * Useful for quickly loading and validating envelopes available to the budget
     */
    fun getEnvelopes(): List<Envelope> {
        return budget.value!!.groups.map { group -> group.envelopes }.flatten()
    }

    /**
     * Gets an envelope by its id
     */
    fun getEnvelope(id: Long): Envelope {
        return getEnvelopes().first { envelope -> envelope.id == id }
    }

    /**
     * Returns the envelope name based on the passed in id
     */
    fun getEnvelopeName(id: Long?): String {
        if(id == null || id == -1L) { return "" }
        return getEnvelopes().first { envelope -> envelope.id == id }.name
    }
}