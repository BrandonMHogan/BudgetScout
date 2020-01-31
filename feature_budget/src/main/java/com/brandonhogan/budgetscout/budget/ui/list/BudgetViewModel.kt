package com.brandonhogan.budgetscout.budget.ui.list


import androidx.lifecycle.*
import com.brandonhogan.budgetscout.core.bases.BaseViewModel
import kotlinx.coroutines.*

class BudgetViewModel : BaseViewModel() {

    init {
        viewModelScope.launch(exceptionHandler) {
        }
    }
}