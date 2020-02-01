package com.brandonhogan.budgetscout.budget.ui.envelope.picker

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brandonhogan.budgetscout.budget.services.BudgetService
import com.brandonhogan.budgetscout.repository.entity.Budget
import com.brandonhogan.budgetscout.repository.entity.Envelope
import com.brandonhogan.budgetscout.repository.entity.relations.BudgetWithGroupsAndEnvelopes

class EnvelopePickerViewModel(private val budgetService: BudgetService) : ViewModel() {

    val selectedEnvelope: MutableLiveData<Envelope> by lazy {
        MutableLiveData<Envelope>()
    }

    val budget: MutableLiveData<BudgetWithGroupsAndEnvelopes> = budgetService.budget

    /**
     * Keeps track of which envelope is selected when setting the envelope
     * with the picker.
     *
     * If false, the envelope is the to envelope.
     * If true, this is the from envelope.
     *
     * Generally it should be false, unless we are trying to pick a from envelope.
     *
     * This doesn't directly affect the picker. Its for anything using the picker to
     * keep track of which envelope they are selecting for
     */
    var isFromEnvelope: Boolean = false

}
