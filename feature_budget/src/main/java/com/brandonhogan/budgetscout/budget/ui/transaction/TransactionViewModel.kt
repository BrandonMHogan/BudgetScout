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

            // if transactions are empty, set a default transaction
            if(data.transactions.isNullOrEmpty()) {
                data.transactions = listOf(Transaction.newInstance())
            }

            // if this is an edit, we will be locking down most of the ui
            isTransactionEdit.postValue(data.isEdit)
            // gets the operation transactions if any exist
            initializeOperationTransactions()
            // initializes the ui model
            initializeUI()
        }
    }

    /**
     * If the operation id is set on the initial transaction, it will attempt to
     * load all transactions associated to this operation id, and store
     * them into the transaction list
     */
    private suspend fun initializeOperationTransactions() = withContext(Dispatchers.IO) {
        data.transactions.first().operationId?.let { operationId ->
            budgetService.getEnvelopesByOperation(operationId)?.let { transactions ->
                data.transactions = transactions
            }
        }
    }

    /**
     * initializes the ui model, and posts back to the observer
     */
    private suspend fun initializeUI() = withContext(Dispatchers.IO) {

        // sets the transaction type based on the operation id, or amount value
        if(data.transactions.first().operationId != null) {
            transactionType.postValue(TransactionType.Transfer)
            // sets the from envelope name, as this is a transfer type
            uiModel.fromEnvelopName = budgetService.getEnvelopeName(getFromTransaction(data.transactions)?.envelopeId)
        }
        else if (data.transactions.first().amount > 0) {
            transactionType.postValue(TransactionType.Income)
        }
        else {
            transactionType.postValue(TransactionType.Expense)
        }

        // sets initial date based on the date of the transaction
        uiModel.date = data.transactions.first().date
        uiModel.amount = abs(data.transactions.first().amount) // stores it as an absolute
        uiModel.note = data.transactions.first().note

        // sets the to envelope name
        uiModel.toEnvelopeName = budgetService.getEnvelopeName(getToTransaction(data.transactions).envelopeId)

        ui.postValue(uiModel)
    }

    /**
     * Helper function go get a transaction based on its id
     */
    private suspend fun getTransaction(id: Long?, transactions: List<Transaction>): Transaction = withContext(Dispatchers.IO) {
        transactions.first { transaction -> transaction.id == id }
    }

    /**
     * Helper function to get the `to` Transaction.
     *
     * The `to` Transaction should be the only transaction with a positive amount
     */
    private suspend fun getToTransaction(transactions: List<Transaction>): Transaction = withContext(Dispatchers.IO) {
        transactions.first { transaction -> transaction.amount >= 0 }
    }

    /**
     * Helper function to get the `from` Transaction.
     *
     * The `from` Transaction will have a negative amount. If none exists, returns null.
     * Down the road, we may want to have multiple from transactions. So this might need
     * to be updated later to return an list
     */
    private suspend fun getFromTransaction(transactions: List<Transaction>): Transaction? = withContext(Dispatchers.IO) {
        val transaction = transactions.firstOrNull { transaction -> transaction.amount < 0 }
        if(transaction != null) {
            transaction
        }
        else {
            transactions.firstOrNull { transaction -> transaction.id != data.transactionId }
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

                var fromTransaction = getFromTransaction(data.transactions)



                data.fromEnvelopeId = envelope.id
                uiModel.fromEnvelopName = envelope.name

                getFromTransaction(data.transactions)?.let { transaction ->
                    transaction.envelopeId = envelope.id
                    uiModel.fromEnvelopName = envelope.name
                }
            }
            else {
                getToTransaction(data.transactions).envelopeId = envelope.id
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
            if(getToTransaction(data.transactions).envelopeId == -1L) {
                errorIds.add(R.string.transaction_validation_error_to_envelope)
            }

            // if its a transfer type, and no valid from envelope selected
            if(transactionType.value == TransactionType.Transfer && ) {
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

        if (isExpense) {
            // we want to make sure the amount is a negative value
            data.transaction.amount = abs(data.transaction.amount) *-1
        }
        else {
            // we want to make sure the amount is a positive value
            data.transaction.amount = abs(data.transaction.amount)
        }

        budgetService.setTransaction(data.transaction)
    }

    /**
     * Will make a set transaction call to try and insert or update the transaction object.
     * If it returns anything but -1, assume success
     */
    private suspend fun saveTransfer(): Any? = withContext(Dispatchers.IO) {

        // makes sure the amount is absolute for the to transaction
        data.transaction.amount = abs(data.transaction.amount)
        // copies the to transaction to the from transaction
        val from = data.transaction.copy()
        // sets the transaction id for the from envelope
        from.id = data.fromTransactionId!!
        // sets the from envelope id
        from.envelopeId = data.fromEnvelopeId!!
        // makes sure the amount is a negative for the from transaction
        from.amount = data.transaction.amount * -1



        budgetService.setTransfer(from, data.transaction)
    }
}
