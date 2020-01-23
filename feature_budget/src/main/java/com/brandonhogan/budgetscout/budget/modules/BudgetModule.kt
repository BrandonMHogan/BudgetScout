package com.brandonhogan.budgetscout.budget.modules

import com.brandonhogan.budgetscout.budget.ui.envelope.EnvelopeDetailViewModel
import com.brandonhogan.budgetscout.budget.ui.group.GroupDetailViewModel
import com.brandonhogan.budgetscout.budget.ui.list.BudgetViewModel
import com.brandonhogan.budgetscout.budget.ui.transaction.TransactionViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val budgetModule = module {

    viewModel { BudgetViewModel(get(), get()) }
    viewModel { GroupDetailViewModel() }
    viewModel { EnvelopeDetailViewModel(get()) }
    viewModel { TransactionViewModel(get()) }
}