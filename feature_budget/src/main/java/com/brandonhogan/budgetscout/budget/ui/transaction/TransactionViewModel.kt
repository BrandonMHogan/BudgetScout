package com.brandonhogan.budgetscout.budget.ui.transaction

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.brandonhogan.budgetscout.core.bases.BaseViewModel
import com.brandonhogan.budgetscout.repository.TransactionType
import com.brandonhogan.budgetscout.repository.entity.Envelope
import com.brandonhogan.budgetscout.repository.entity.Transaction
import com.brandonhogan.budgetscout.repository.repo.budget.BudgetRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class TransactionViewModel(private val budgetRepo: BudgetRepo) : BaseViewModel() {

    private val uiModel: TransactionUIModel by lazy {
        TransactionUIModel()
    }

    private val model: TransactionModel by lazy {
        TransactionModel()
    }

    val ui: MutableLiveData<TransactionUIModel> by lazy {
        MutableLiveData<TransactionUIModel>()
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
     * Sets the model values passed to the fragment, then rns the init
     * setup required by the view model
     */
    fun setModel(transaction: Transaction, budgetId: Long, groupId: Long) {

        model.transaction = transaction
        model.budgetId = budgetId
        model.groupId = groupId

        // posts the ui model to the ui
        ui.postValue(uiModel)
        setup()
    }

    /**
     * Loads all envelopes from the database that can be used
     */
    private fun setup() {
        viewModelScope.launch(exceptionHandler) {
            // sets initial date as today
            date.postValue(Calendar.getInstance())
            loadEnvelopes()
        }
    }

    /**
     * Loads envelopes from the database, used to populate the from and to lists
     */
    suspend fun loadEnvelopes() = withContext(Dispatchers.IO) {

        model.budgetId?.let { budgetId ->

            // loads all envelopes for the given budget id
            model.envelopes = budgetRepo.getEnvelopes(budgetId)

            // sets all the envelopes
            envelopes.postValue(model.envelopes)
        }
    }

    fun onDateClick() {

    }

    /**
     * If type is not transfer, the from envelope is hidden, so it should be cleared
     */
    fun setTransactionType(type: TransactionType) {
        transactionType.postValue(type)

        if(type != TransactionType.Transfer) {
            model.transaction.fromEnvelopeId = null
        }
    }

    /**
     * Sets the amount entered, making sure its a double
     */
    fun onAmountChanged(amount: String) {
        // TODO: put a check to make sure amount comes in as a double
        model.transaction.amount = amount.toDouble()
    }

    /**
     * Sets the from envelope selected
     */
    fun fromEnvelopeItemClicked(position: Int) {
        envelopes.value?.let { model.transaction.fromEnvelopeId = it[position].id }
    }

    /**
     * Sets the to envelope selected
     */
    fun toEnvelopeItemClicked(position: Int) {
        envelopes.value?.let { model.transaction.envelopeId = it[position].id }
    }

    fun toEnvelopeTextChanged(text: String) {
        val count = model.envelopes?.count { envelope -> envelope.name.contains(text) }

        if(count == 1) {
            model.envelopes?.find { envelope -> envelope.name.contains(text) }?.let {
                model.transaction.envelopeId = it.id
            }
        }
    }

    fun fromEnvelopeTextChanged(text: String) {
        val count = model.envelopes?.count { envelope -> envelope.name.toLowerCase().contains(text.toLowerCase()) }

        if(count == 1) {
            model.envelopes?.find { envelope -> envelope.name.toLowerCase().contains(text.toLowerCase()) }?.let {
                model.transaction.fromEnvelopeId = it.id
            }
        }
    }

    /**
     * Will do quick validation to make sure the transaction is valid,
     * then update the database with the new or edited transaction
     */
    fun onSave() {

        // if amount or fromEnvelope is null, or if type is Transfer and toEnvelope is null, display error
        if(model.transaction.amount <= 0 || model.transaction.fromEnvelopeId == null ||
            (model.transaction.type == TransactionType.Transfer && model.transaction.envelopeId == null)) {
            uiModel.displayError = true
        }

        // if an error needs to be displayed, display it reset it, and return
        if(uiModel.displayError) {
            ui.postValue(uiModel)
            uiModel.displayError = false
            return
        }




    }
}
