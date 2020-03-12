package com.brandonhogan.budgetscout.budget.ui.envelope

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.brandonhogan.budgetscout.budget.services.BudgetService
import com.brandonhogan.budgetscout.core.bases.BaseViewModel
import com.brandonhogan.budgetscout.repository.entity.Envelope
import com.brandonhogan.budgetscout.repository.entity.Transaction
import com.brandonhogan.budgetscout.repository.entity.relations.EnvelopeWithTransactions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EnvelopeDetailViewModel(private val model: EnvelopeDetailModel, private val budgetService: BudgetService) : BaseViewModel() {

    /**
     * Passes the selected envelope from the budget server to the model
     */
    val selectedEnvelope: MutableLiveData<EnvelopeWithTransactions> = budgetService.selectedEnvelope

    init {
        viewModelScope.launch(exceptionHandler) {
            budgetService.selectEnvelope(model.envelopeId)
        }
    }

    fun getGroupId(): Long = model.groupId

    /**
     * Loads the envelope from the repository, in a background thread
     */
    private suspend fun loadEnvelope(envelopeId: Long = 1): Envelope = withContext(Dispatchers.IO) {
        budgetService.getEnvelope(envelopeId)
    }

    /**
     * Loads the transactions from the repository, in a background thread
     */
    private suspend fun loadTransactions(envelopeId: Long): List<Transaction> = withContext(Dispatchers.IO) {
        budgetService.getTransactionsForEnvelope(envelopeId)
    }
}
