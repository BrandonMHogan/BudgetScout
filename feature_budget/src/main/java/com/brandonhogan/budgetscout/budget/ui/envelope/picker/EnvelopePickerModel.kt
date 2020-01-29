package com.brandonhogan.budgetscout.budget.ui.envelope.picker

import android.os.Parcelable
import com.brandonhogan.budgetscout.repository.entity.relations.GroupWithEnvelopes
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EnvelopePickerModel constructor(
    // the budget that the groups are apart of
    var budgetId: Long? = null,
    // the transaction object
    var groupWithEnvelopes: List<GroupWithEnvelopes>): Parcelable