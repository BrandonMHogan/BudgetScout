package com.brandonhogan.budgetscout.budget.ui


import androidx.lifecycle.*
import com.brandonhogan.budgetscout.core.bases.BaseViewModel
import com.brandonhogan.budgetscout.core.services.Log
import com.brandonhogan.budgetscout.repository.entity.Budget
import com.brandonhogan.budgetscout.repository.repo.budget.BudgetRepo
import kotlinx.coroutines.*

class BudgetViewModel(private val budgetRepo: BudgetRepo) : BaseViewModel() {

    /**
     * Observable by its view
     */
    val budget: MutableLiveData<List<Budget>> by lazy {
        MutableLiveData<List<Budget>>()
    }

    /**
     * On init, will load the active budget and post it
     */
    init {
        viewModelScope.launch(exceptionHandler) {
            // will add a new budget first
            setBudget(Budget(name = "Second Budget"))
            // then load the active budget
            budget.postValue(loadActiveBudget())
        }
    }

    /**
     * Loads the budget from the repository, in the background thread
     */
    private suspend fun loadActiveBudget(): List<Budget> = withContext(Dispatchers.IO) {
        Log.debug("Inside loadActiveBudget")
        budgetRepo.getAll()
    }

    /**
     * Loads the budget from the repository, in the background thread
     */
    private suspend fun setBudget(budget: Budget): List<Long> = withContext(Dispatchers.IO) {
        Log.debug("Inside setBudget")
        budgetRepo.insert(budget)
    }
}