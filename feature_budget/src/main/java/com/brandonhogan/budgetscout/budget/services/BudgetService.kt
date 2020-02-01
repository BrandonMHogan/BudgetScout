package com.brandonhogan.budgetscout.budget.services

import androidx.lifecycle.MutableLiveData
import com.brandonhogan.budgetscout.core.bases.BaseService
import com.brandonhogan.budgetscout.repository.entity.Envelope
import com.brandonhogan.budgetscout.repository.entity.relations.BudgetWithGroupsAndEnvelopes
import com.brandonhogan.budgetscout.repository.repo.budget.BudgetRepo
import com.brandonhogan.budgetscout.repository.testing.BudgetCreator
import kotlinx.coroutines.*
import java.util.*


/**
 * Budget Service
 *
 * Maintains the active running budget and any data refresh or changes required
 */
class BudgetService(private val budgetRepo: BudgetRepo, private val budgetCreator: BudgetCreator): BaseService() {

    /**
     * Observable by its view
     */
    val budget: MutableLiveData<BudgetWithGroupsAndEnvelopes> by lazy {
        MutableLiveData<BudgetWithGroupsAndEnvelopes>()
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
     * Flattens all the groups, making a single list of all the envelopes.
     * Useful for quickly loading and validating envelopes available to the budget
     */
    fun getEnvelopes(): List<Envelope> {
        return budget.value!!.groups.map { group -> group.envelopes }.flatten()
    }

    /**
     * Returns the envelope name based on the passed in id
     */
    fun getEnvelopeName(id: Long?): String {
        if(id == null || id == -1L) { return "" }
        return getEnvelopes().first { envelope -> envelope.id == id }.name
    }
}