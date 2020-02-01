package com.brandonhogan.budgetscout.budget.ui.envelope.picker

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brandonhogan.budgetscout.repository.entity.Envelope

class EnvelopePickerViewModel : ViewModel() {

    val selectedEnvelope: MutableLiveData<Envelope> by lazy {
        MutableLiveData<Envelope>()
    }

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
