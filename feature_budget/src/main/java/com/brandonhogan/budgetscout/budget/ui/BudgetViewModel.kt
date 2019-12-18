package com.brandonhogan.budgetscout.budget.ui

import androidx.lifecycle.ViewModel
import com.brandonhogan.budgetscout.budget.HelloRepository

class BudgetViewModel(val repo : HelloRepository) : ViewModel() {
    fun sayHello() = "${repo.giveHello()}"
}