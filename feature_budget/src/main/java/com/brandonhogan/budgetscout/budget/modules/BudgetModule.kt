package com.brandonhogan.budgetscout.budget.modules

import com.brandonhogan.budgetscout.budget.HelloRepository
import com.brandonhogan.budgetscout.budget.HelloRepositoryImpl
import com.brandonhogan.budgetscout.budget.ui.BudgetViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val budgetModule = module {

    // single instance of HelloRepository
    single<HelloRepository> { HelloRepositoryImpl() }

    // MyViewModel ViewModel
    viewModel { BudgetViewModel(get()) }
}