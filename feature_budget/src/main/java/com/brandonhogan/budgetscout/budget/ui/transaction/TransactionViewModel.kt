package com.brandonhogan.budgetscout.budget.ui.transaction

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.brandonhogan.budgetscout.core.bases.BaseViewModel
import com.brandonhogan.budgetscout.repository.TransactionType
import com.brandonhogan.budgetscout.repository.entity.Envelope
import com.brandonhogan.budgetscout.repository.entity.relations.BudgetWithGroupsAndEnvelopes
import com.brandonhogan.budgetscout.repository.repo.budget.BudgetRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class TransactionViewModel(private val budgetRepo: BudgetRepo) : BaseViewModel() {

    /**
     * Observable by its view
     */
    val budgetId: MutableLiveData<Long> by lazy {
        MutableLiveData<Long>()
    }

    val groupId: MutableLiveData<Long> by lazy {
        MutableLiveData<Long>()
    }

    val envelopeId: MutableLiveData<Long> by lazy {
        MutableLiveData<Long>()
    }

    val transactionId: MutableLiveData<Long> by lazy {
        MutableLiveData<Long>()
    }

    val date: MutableLiveData<Calendar> by lazy {
        MutableLiveData<Calendar>()
    }

    val envelopes: MutableLiveData<List<Envelope>> by lazy {
        MutableLiveData<List<Envelope>>()
    }

    val transactionType: MutableLiveData<TransactionType> by lazy {
        MutableLiveData<TransactionType>()
    }

    /**
     * Loads all envelopes from the database that can be used
     */
    fun setup() {
        viewModelScope.launch(exceptionHandler) {
            // sets initial date as today
            date.postValue(Calendar.getInstance())
            loadEnvelopes()
        }
    }

    suspend fun loadEnvelopes() = withContext(Dispatchers.IO) {

        budgetId.value?.let { budgetId ->

            // loads all envelopes for the given budget id
            val allEnvelopes = budgetRepo.getEnvelopes(budgetId)

            // sets all the envelopes
            envelopes.postValue(allEnvelopes)
        }
    }

    fun onDateClick() {

    }

    /**
     * Will do quick validation to make sure the transaction is valid,
     * then update the database with the new or edited transaction
     */
    fun onSave() {


    }
}
