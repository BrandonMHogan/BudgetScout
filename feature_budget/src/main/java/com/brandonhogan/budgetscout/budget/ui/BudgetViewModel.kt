package com.brandonhogan.budgetscout.budget.ui


import androidx.lifecycle.*
import com.brandonhogan.budgetscout.core.bases.BaseViewModel
import com.brandonhogan.budgetscout.repository.entity.Budget
import com.brandonhogan.budgetscout.repository.repo.BudgetRepo
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
            budget.postValue(loadActiveBudget())
        }
    }

    /**
     * Loads the budget from the repository, in the background thread
     */
    private suspend fun loadActiveBudget(): List<Budget> = withContext(Dispatchers.IO) {
        budgetRepo.getAll()
    }
}