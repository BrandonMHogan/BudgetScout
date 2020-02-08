package com.brandonhogan.budgetscout.budget.modules

import com.brandonhogan.budgetscout.budget.services.BudgetService
import com.brandonhogan.budgetscout.budget.ui.envelope.EnvelopeDetailModel
import com.brandonhogan.budgetscout.budget.ui.envelope.EnvelopeDetailViewModel
import com.brandonhogan.budgetscout.budget.ui.envelope.picker.EnvelopePickerViewModel
import com.brandonhogan.budgetscout.budget.ui.group.GroupDetailViewModel
import com.brandonhogan.budgetscout.budget.ui.list.BudgetViewModel
import com.brandonhogan.budgetscout.budget.ui.transaction.TransactionData
import com.brandonhogan.budgetscout.budget.ui.transaction.TransactionViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val budgetModule = module {

    single { BudgetService(get(), get(), get()) }
    viewModel { BudgetViewModel(get()) }
    viewModel { GroupDetailViewModel() }
    viewModel { (envelopeDetailModel: EnvelopeDetailModel) -> EnvelopeDetailViewModel(envelopeDetailModel, get()) }
    viewModel { (transactionData: TransactionData) -> TransactionViewModel(transactionData, get()) }
    viewModel { EnvelopePickerViewModel(get()) }
}