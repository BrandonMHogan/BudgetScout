package com.brandonhogan.budgetscout.budget.ui.list


import androidx.lifecycle.*
import com.brandonhogan.budgetscout.core.bases.BaseViewModel
import com.brandonhogan.budgetscout.repository.entity.Budget
import com.brandonhogan.budgetscout.repository.entity.Envelope
import com.brandonhogan.budgetscout.repository.entity.Group
import com.brandonhogan.budgetscout.repository.entity.relations.BudgetWithGroupsAndEnvelopes
import com.brandonhogan.budgetscout.repository.repo.budget.BudgetRepo
import com.brandonhogan.budgetscout.repository.testing.BudgetCreator
import kotlinx.coroutines.*

class BudgetViewModel(private val budgetRepo: BudgetRepo, private val budgetCreator: BudgetCreator) : BaseViewModel() {

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
        viewModelScope.launch(exceptionHandler) {
            // will add a new budget first
            //val budgetId = budgetCreator.createBasicBudget(true)

            // then load the active budget
            budget.postValue(loadActiveBudget(20))
        }
    }

    /**
     * Loads the budget from the repository, in the background thread
     */
    private suspend fun loadActiveBudget(budgetId: Long = 1): BudgetWithGroupsAndEnvelopes = withContext(Dispatchers.IO) {
        budgetRepo.getWithGroupsAndEnvelopes(budgetId)
    }
}