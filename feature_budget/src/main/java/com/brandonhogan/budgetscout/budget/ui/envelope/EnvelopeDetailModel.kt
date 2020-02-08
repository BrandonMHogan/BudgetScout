package com.brandonhogan.budgetscout.budget.ui.envelope

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @Creator         Brandon Hogan
 * @Date            2020-02-07
 * @File            EnvelopeDetailModel
 * @Description     {{ foo }}
 */

@Parcelize
data class EnvelopeDetailModel constructor(
    // the envelope id
    var envelopeId: Long,
    // the group that the envelope is apart of
    var groupId: Long): Parcelable