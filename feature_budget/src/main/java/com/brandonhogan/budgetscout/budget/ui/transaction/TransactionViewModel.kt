package com.brandonhogan.budgetscout.budget.ui.transaction

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.brandonhogan.budgetscout.budget.services.BudgetService
import com.brandonhogan.budgetscout.core.R
import com.brandonhogan.budgetscout.core.bases.BaseViewModel
import com.brandonhogan.budgetscout.repository.TransactionType
import com.brandonhogan.budgetscout.repository.entity.Envelope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class TransactionViewModel(private val data: TransactionData, private val budgetService: BudgetService) : BaseViewModel() {

    val ui: MutableLiveData<TransactionUIModel> by lazy {
        MutableLiveData<TransactionUIModel>()
    }

    private val uiModel: TransactionUIModel by lazy {
        TransactionUIModel()
    }

    // used to display messages to the view
    var displayMessage: MutableLiveData<List<Int>> = MutableLiveData(listOf())

    val envelopes: MutableLiveData<List<Envelope>> by lazy {
        MutableLiveData<List<Envelope>>()
    }

    val transactionType: MutableLiveData<TransactionType> by lazy {
        MutableLiveData<TransactionType>()
    }

    /**
     * Takes the data object, and updates the ui model
     */
    init {
        viewModelScope.launch(exceptionHandler) {
            // sets initial date based on the date of the transaction
            uiModel.date = data.transaction.date
            // sets the from and to envelope names
            uiModel.fromEnvelopName = budgetService.getEnvelopeName(data.transaction.fromEnvelopeId)
            uiModel.toEnvelopeName = budgetService.getEnvelopeName(data.transaction.envelopeId)
            // posts the ui model to the ui
            ui.postValue(uiModel)
        }
    }

    /**
     * When the date is changed, update the ui model and push it to the ui
     */
    fun onDateChanged(date: Calendar) {
        uiModel.date = date
        ui.postValue(uiModel)
    }

    /**
     * If type is not transfer, the from envelope is hidden, so it should be cleared
     */
    fun setTransactionType(type: TransactionType) {
        transactionType.postValue(type)

        if(type != TransactionType.Transfer) {
            data.transaction.fromEnvelopeId = null
        }
    }

    /**
     * Sets the amount entered, making sure its a double
     */
    fun onAmountChanged(amount: String) {
        try {
            data.transaction.amount = if (amount.isBlank()) {
                0.00
            } else {
                amount.toDouble()
            }
        }
        catch (ex: Exception) {
            data.transaction.amount = 0.00
        }
    }

    fun envelopeSelected(isFromEnvelope: Boolean = false, envelope: Envelope) {
        if (isFromEnvelope) {
            data.transaction.fromEnvelopeId = envelope.id
            uiModel.fromEnvelopName = envelope.name
        }
        else {
            data.transaction.envelopeId = envelope.id
            uiModel.toEnvelopeName = envelope.name
        }

        ui.postValue(uiModel)
    }

    /**
     * Will do quick validation to make sure the transaction is valid,
     * then update the database with the new or edited transaction
     */
    fun onSave() {

        // keeps a running list of errors
        val errorIds: ArrayList<Int> = arrayListOf()

        // if the amount is 0 or less
        if (data.transaction.amount <= 0) {
            errorIds.add(R.string.transaction_validation_error_amount_empty)
        }

        // if no valid to envelope was selected
        if(data.transaction.envelopeId == -1L) {
            errorIds.add(R.string.transaction_validation_error_to_envelope)
        }

        // if its a transfer type, and no valid from envelope selected
        if(data.transaction.type == TransactionType.Transfer && (data.transaction.fromEnvelopeId == null || data.transaction.fromEnvelopeId == -1L)) {
            errorIds.add(R.string.transaction_validation_error_from_envelope)
        }

        // if there are any errors, we want to return to the ui
        if (errorIds.isNotEmpty()) {
            displayMessage.postValue(errorIds)
            return
        }



        displayMessage.postValue(listOf(R.string.transaction_save_success))

    }
}
