package com.brandonhogan.budgetscout.budget.ui.transaction

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.brandonhogan.budgetscout.budget.services.BudgetService
import com.brandonhogan.budgetscout.core.R
import com.brandonhogan.budgetscout.core.bases.BaseViewModel
import com.brandonhogan.budgetscout.core.services.Log
import com.brandonhogan.budgetscout.repository.TransactionType
import com.brandonhogan.budgetscout.repository.entity.Envelope
import com.brandonhogan.budgetscout.repository.entity.Transaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs

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

    val isTransactionEdit: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    /**
     * Takes the data object, and updates the ui model
     */
    init {
        viewModelScope.launch(exceptionHandler) {
            // gets the operation transactions if any exist
            initializeOperationTransactions()
            // if this is an edit, we will be locking down most of the ui
            isTransactionEdit.postValue(data.isEdit)
            // initializes the ui model
            initializeUI()
        }
    }

    /**
     * If the operation id is set on the data object, it will attempt to
     * load all transactions associated to this operation id, and store
     * them into the correct transaction objects based on their values
     */
    private suspend fun initializeOperationTransactions() = withContext(Dispatchers.IO) {

        data.operationId?.let { operationId ->
            budgetService.getEnvelopesByOperation(operationId)?.let { transactions ->

                // If we have less then 2 transactions, something is wrong
                if(transactions.size <= 1) {
                    Log.error("initializeOperationTransactions() returned only 1 transaction for operation id $operationId.")

                    // try and recover. But this shouldnt be happening.
                    if (transactions.size == 1) {
                        data.toTransaction = transactions.first()
                    }
                }
                else if(transactions.size == 2) {

                    // basically, it will find which transaction has the negative value,
                    // and set it as the from, and the other one as the to transaction

                    if (transactions.first().amount < 0) {
                        data.fromTransaction = transactions.first()
                        data.toTransaction = transactions.last()
                    }
                    else {
                        data.fromTransaction = transactions.first()
                        data.toTransaction = transactions.last()
                    }

                }
                else {
                    // This should never happen...
                    Log.error("initializeOperationTransactions() returned more then 2 transaction for operation id $operationId.")
                    throw java.lang.Exception()
                }
            }
        }
    }

    /**
     * initializes the ui model, and posts back to the observer
     */
    private suspend fun initializeUI() = withContext(Dispatchers.IO) {

        // defaults transaction type to expense
        uiModel.transactionType = TransactionType.Expense

        // if a to transaction object is set, apply its values to the ui model
        data.toTransaction?.let {toTransaction ->
            uiModel.toEnvelopeName = budgetService.getEnvelopeName(toTransaction.envelopeId)

            // if the to transaction has an amount greater then 0, its considered income
            if(toTransaction.amount > 0) {
                uiModel.transactionType = TransactionType.Income
            }

            uiModel.date = toTransaction.date
            uiModel.amount = abs(toTransaction.amount) // stores it as an absolute
            uiModel.note = toTransaction.note
        }

        // if the from transaction has been set, then apply its values to the ui model
        data.fromTransaction?.let {fromTransaction ->
            uiModel.fromEnvelopName = budgetService.getEnvelopeName(fromTransaction.envelopeId)

            // if from transaction is set, then this is considered a transfer
            uiModel.transactionType = TransactionType.Transfer
        }

        ui.postValue(uiModel)
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
     * Sets the amount entered, making sure its a double
     */
    fun onAmountChanged(amount: String) {
        try {
            uiModel.amount = if (amount.isBlank()) {
                0.00
            } else {
                amount.toDouble()
            }
        }
        catch (ex: Exception) {
            uiModel.amount = 0.00
        }
    }

    /**
     * Sets the note entered
     */
    fun onNoteChanged(note: String) {
        uiModel.note = note
    }

    /**
     * Updates the ui with the correct transaction type
     */
    fun setTransactionType(type: TransactionType) {
        transactionType.postValue(type)
    }

    fun envelopeSelected(isFromEnvelope: Boolean = false, envelope: Envelope) {
        viewModelScope.launch {
            if (isFromEnvelope) {

                // if the from transaction is null, make an empty one
                if(data.fromTransaction == null) {
                    data.fromTransaction = Transaction.newInstance()
                }

                // sets the from envelope id and the ui's from envelope name
                data.fromTransaction?.envelopeId = envelope.id
                uiModel.fromEnvelopName = envelope.name
            }
            else {
                // if the to transaction is null, make an empty one
                if(data.toTransaction == null) {
                    data.toTransaction = Transaction.newInstance()
                }

                // sets the to envelope id and the ui's to envelope name
                data.toTransaction?.envelopeId = envelope.id
                uiModel.toEnvelopeName = envelope.name
            }

            ui.postValue(uiModel)
        }
    }

    /**
     * Will do quick validation to make sure the transaction is valid,
     * then update the database with the new or edited transaction
     */
    fun onSave() {

        viewModelScope.launch {

            // keeps a running list of errors
            val errorIds: ArrayList<Int> = arrayListOf()

            // if the amount is 0 or less
            if (uiModel.amount <= 0) {
                errorIds.add(R.string.transaction_validation_error_amount_empty)
            }

            // if no valid to envelope was selected
            if(data.toTransaction == null
                || data.toTransaction?.envelopeId == null
                || data.toTransaction?.envelopeId == -1L) {
                errorIds.add(R.string.transaction_validation_error_to_envelope)
            }

            // if its a transfer type, and no valid from envelope selected
            if(transactionType.value == TransactionType.Transfer && data.fromTransaction?.envelopeId == -1L) {
                errorIds.add(R.string.transaction_validation_error_from_envelope)
            }

            // if there are any errors, we want to return to the ui
            if (errorIds.isNotEmpty()) {
                displayMessage.postValue(errorIds)
            }
            else {

                val result = if (transactionType.value == TransactionType.Transfer) {
                    saveTransfer()
                } else {
                    saveTransaction(transactionType.value == TransactionType.Expense)
                }

                if (result == null || result == -1L) {
                    displayMessage.postValue(listOf(R.string.transaction_save_fail_message))
                } else {
                    displayMessage.postValue(listOf(R.string.transaction_save_success))
                }
            }
        }
    }

    /**
     * Will make a set transaction call to try and insert or update the transaction object.
     * If it returns anything but -1, assume success
     */
    private suspend fun saveTransaction(isExpense: Boolean): Any? = withContext(Dispatchers.IO) {

        val toTransaction = data.toTransaction!!
        toTransaction.amount = abs(uiModel.amount)
        toTransaction.note = uiModel.note
        toTransaction.date = uiModel.date

        // if expense, make sure the value is a negative
        if (isExpense) {
            toTransaction.amount = toTransaction.amount  *-1
        }

        budgetService.setTransaction(toTransaction)
    }

    /**
     * Will make a set transaction call to try and insert or update the transaction object.
     * If it returns anything but -1, assume success
     */
    private suspend fun saveTransfer(): Any? = withContext(Dispatchers.IO) {

        val toTransaction = data.toTransaction!!
        val fromTransaction = data.fromTransaction!!

        // makes sure the amount is absolute for the to transaction
        toTransaction.amount = abs(uiModel.amount)
        toTransaction.note = uiModel.note
        toTransaction.date = uiModel.date

        // sets the from transaction to match the to, except it inverts the amount
        fromTransaction.amount = toTransaction.amount * -1
        fromTransaction.note = toTransaction.note
        fromTransaction.date = toTransaction.date


        budgetService.setTransfer(fromTransaction, toTransaction)
    }
}
