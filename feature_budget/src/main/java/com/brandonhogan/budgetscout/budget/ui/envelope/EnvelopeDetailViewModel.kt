package com.brandonhogan.budgetscout.budget.ui.envelope

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brandonhogan.budgetscout.budget.services.BudgetService
import com.brandonhogan.budgetscout.core.bases.BaseViewModel
import com.brandonhogan.budgetscout.repository.entity.Envelope
import com.brandonhogan.budgetscout.repository.repo.budget.BudgetRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EnvelopeDetailViewModel(private val budgetService: BudgetService) : BaseViewModel() {

    /**
     * Observable by its view
     */
    val envelope: MutableLiveData<Envelope> by lazy {
        MutableLiveData<Envelope>()
    }

    /**
     * Loads the envelope object based on the id, then updates the mutable live data
     */
    fun getEnvelope(envelopeId: Long) {
        viewModelScope.launch(exceptionHandler) {
            envelope.postValue(loadEnvelope(envelopeId))
        }
    }

    /**
     * Loads the envelope from the repository, in a background thread
     */
    private suspend fun loadEnvelope(envelopeId: Long = 1): Envelope = withContext(Dispatchers.IO) {
        budgetService.getEnvelope(envelopeId)
    }
}
