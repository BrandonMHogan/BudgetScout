package com.brandonhogan.budgetscout.budget.ui.list


import androidx.lifecycle.*
import com.brandonhogan.budgetscout.budget.services.BudgetService
import com.brandonhogan.budgetscout.core.bases.BaseViewModel
import com.brandonhogan.budgetscout.repository.entity.relations.BudgetWithGroupsAndEnvelopes
import kotlinx.coroutines.*

class BudgetViewModel(private val budgetService: BudgetService) : BaseViewModel() {

    init {
        viewModelScope.launch(exceptionHandler) {
        }
    }

    val budget: MutableLiveData<BudgetWithGroupsAndEnvelopes> = budgetService.budget
}