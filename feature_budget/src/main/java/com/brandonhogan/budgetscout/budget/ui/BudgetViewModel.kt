package com.brandonhogan.budgetscout.budget.ui


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.brandonhogan.budgetscout.core.bases.BaseViewModel
import com.brandonhogan.budgetscout.core.services.Log
import com.brandonhogan.budgetscout.repository.entity.Budget
import com.brandonhogan.budgetscout.repository.repo.BudgetRepo
import kotlinx.coroutines.*

class BudgetViewModel(val budgetRepo: BudgetRepo) : BaseViewModel() {

    var budget = MutableLiveData<Budget>()

    fun getBudget() {
        uiScope.launch {
            val response = loadBudget()
            Log.debug(response.value?.name ?: "None Found")
            budget.value = response.value
        }
    }

    suspend fun loadBudget(): LiveData<Budget> = withContext(Dispatchers.Default) {
        val newBudget = Budget(0, "First Item")
        budgetRepo.insert(newBudget)
        budgetRepo.get(newBudget.id)
    }


}