package com.brandonhogan.budgetscout.budget.ui


import androidx.lifecycle.*
import com.brandonhogan.budgetscout.core.bases.BaseViewModel
import com.brandonhogan.budgetscout.core.services.Log
import com.brandonhogan.budgetscout.repository.entity.Budget
import com.brandonhogan.budgetscout.repository.entity.relations.BudgetWithGroupsAndEnvelopes
import com.brandonhogan.budgetscout.repository.repo.budget.BudgetRepo
import kotlinx.coroutines.*

class BudgetViewModel(private val budgetRepo: BudgetRepo) : BaseViewModel() {

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
            //setBudget(Budget(name = "Second Budget"))
            // then load the active budget
            budget.postValue(loadActiveBudget())
        }
    }

    /**
     * Loads the budget from the repository, in the background thread
     */
    private suspend fun loadActiveBudget(): BudgetWithGroupsAndEnvelopes = withContext(Dispatchers.IO) {
        budgetRepo.getWithGroupsAndEnvelopes(1)
    }

    /**
     * Loads the budget from the repository, in the background thread
     */
    private suspend fun setBudget(budget: Budget): List<Long> = withContext(Dispatchers.IO) {
        budgetRepo.insert(budget)
    }
}