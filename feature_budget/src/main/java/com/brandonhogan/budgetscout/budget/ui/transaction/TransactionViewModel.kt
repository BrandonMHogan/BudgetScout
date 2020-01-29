package com.brandonhogan.budgetscout.budget.ui.transaction

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.brandonhogan.budgetscout.core.R
import com.brandonhogan.budgetscout.core.bases.BaseViewModel
import com.brandonhogan.budgetscout.repository.TransactionType
import com.brandonhogan.budgetscout.repository.entity.Envelope
import com.brandonhogan.budgetscout.repository.entity.relations.GroupWithEnvelopes
import com.brandonhogan.budgetscout.repository.repo.budget.BudgetRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

class TransactionViewModel(private val budgetRepo: BudgetRepo) : BaseViewModel() {

    private val uiModel: TransactionUIModel by lazy {
        TransactionUIModel()
    }

    private var data: TransactionData = TransactionData()

    val ui: MutableLiveData<TransactionUIModel> by lazy {
        MutableLiveData<TransactionUIModel>()
    }

    // used to display messages to the view
    var displayMessage: MutableLiveData<List<Int>> = MutableLiveData(listOf())

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
     * returns the budget id
     */
    fun getBudget(): Long {
        return data.budgetId ?: 0
    }

    /**
     * Returns a list of the groups and their envelopes
     */
    fun getGroupsWithEnvelopes(): List<GroupWithEnvelopes> {
        return data.groupsWithEnvelopes ?: listOf()
    }

    /**
     * Sets the model values passed to the fragment, then rns the init
     * setup required by the view model
     */
    fun setData(transactionData: TransactionData) {
        data = transactionData

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

        data.budgetId?.let { budgetId ->
            val budget = budgetRepo.getWithGroupsAndEnvelopes(budgetId)
            data.groupsWithEnvelopes = budget.groups
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
