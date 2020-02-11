package com.brandonhogan.budgetscout.budget.ui.transaction

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.brandonhogan.budgetscout.budget.services.BudgetService
import com.brandonhogan.budgetscout.core.R
import com.brandonhogan.budgetscout.core.bases.BaseViewModel
import com.brandonhogan.budgetscout.repository.TransactionType
import com.brandonhogan.budgetscout.repository.entity.Envelope
import com.brandonhogan.budgetscout.repository.entity.Transaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
            initializeUI()
        }
    }

    /**
     * initializes the ui model, and posts back to the observer
     */
    private suspend fun initializeUI() = withContext(Dispatchers.IO) {
        // sets initial date based on the date of the transaction
        uiModel.date = data.transaction.date
        uiModel.toEnvelopeName = budgetService.getEnvelopeName(data.transaction.envelopeId)
        uiModel.amount = data.transaction.amount
        uiModel.note = data.transaction.note

        checkOperations()
        ui.postValue(uiModel)
    }

    /**
     * Checks if an operation id is set.
     * If set, it will load the operation and its transactions,
     * and set the from envelope as the other operation in the transfer
     */
    private suspend fun checkOperations() = withContext(Dispatchers.IO) {
        // if the operation id is not null, we need to go get the other
        // transactions associated to this one
        data.transaction.operationId?.let { operationId ->

            // if any transactions return, find the paired transaction to set as the
            // from envelope
            budgetService.getEnvelopesByOperation(operationId)?.let { transactions ->
                // the first transaction it finds that is not from data.transaction,
                // it will set that as the from envelope
                transactions.firstOrNull { transaction ->
                    transaction.envelopeId != data.transaction.envelopeId }?.let { otherTransaction ->
                    uiModel.fromEnvelopName = budgetService.getEnvelopeName(otherTransaction.envelopeId)
                }
            }
        }
    }

    /**
     * When the date is changed, update the ui model and push it to the ui
     */
    fun onDateChanged(date: Calendar) {
        //data.transaction
        uiModel.date = date
        ui.postValue(uiModel)
    }

    /**
     * If type is not transfer, the from envelope is hidden, so it should be cleared
     */
    fun setTransactionType(type: TransactionType) {
        transactionType.postValue(type)

        if(type != TransactionType.Transfer) {
            data.fromEnvelopeId = null
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
            data.fromEnvelopeId = envelope.id
            uiModel.fromEnvelopName = envelope.name
        }
        else {
            data.transaction.envelopeId = envelope.id
            uiModel.toEnvelopeName = envelope.name
        }

        ui.postValue(uiModel)
    }

    /**
     * Sets the note entered
     */
    fun onNoteChanged(note: String) {
        data.transaction.note = note
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
        if(transactionType.value == TransactionType.Transfer && (data.fromEnvelopeId == null || data.fromEnvelopeId == -1L)) {
            errorIds.add(R.string.transaction_validation_error_from_envelope)
        }

        // if there are any errors, we want to return to the ui
        if (errorIds.isNotEmpty()) {
            displayMessage.postValue(errorIds)
            return
        }

        /**
         * If the transaction is valid, we want to try and save it
         */
        viewModelScope.launch {

            val result =

            if(transactionType.value == TransactionType.Transfer) {
                saveTransfer()
            }
            else {
                saveTransaction()
            }

            if(result == null ||result == -1L) {
                displayMessage.postValue(listOf(R.string.transaction_save_fail_message))
            }
            else {
                displayMessage.postValue(listOf(R.string.transaction_save_success))
            }
        }
    }

    /**
     * Will make a set transaction call to try and insert or update the transaction object.
     * If it returns anything but -1, assume success
     */
    suspend fun saveTransaction(): Long? = withContext(Dispatchers.IO) {
        budgetService.setTransaction(data.transaction)
    }

    /**
     * Will make a set transaction call to try and insert or update the transaction object.
     * If it returns anything but -1, assume success
     */
    suspend fun saveTransfer(): List<Long>? = withContext(Dispatchers.IO) {

        val from = data.transaction.copy()
        from.envelopeId = data.fromEnvelopeId!!
        from.amount = data.transaction.amount * -1

        budgetService.setTransfer(from, data.transaction)
    }
}
