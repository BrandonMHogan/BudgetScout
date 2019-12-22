package com.brandonhogan.budgetscout.budget.modules

import com.brandonhogan.budgetscout.budget.ui.BudgetViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val budgetModule = module {

    // MyViewModel ViewModel
    viewModel { BudgetViewModel(get()) }
}