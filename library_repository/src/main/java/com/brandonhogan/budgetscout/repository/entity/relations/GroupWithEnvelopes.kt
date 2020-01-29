package com.brandonhogan.budgetscout.repository.entity.relations

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import com.brandonhogan.budgetscout.repository.entity.Envelope
import com.brandonhogan.budgetscout.repository.entity.Group
import kotlinx.android.parcel.Parcelize

/**
 * @Creator         Brandon Hogan
 * @Date            2019-12-31
 * @File            GroupWithEnvelopes
 * @Description     Relational data class used to get a group and its envelopes
 */
@Parcelize
data class GroupWithEnvelopes(
    @Embedded val group: Group,
    @Relation(
        parentColumn = Group.PROPERTY_ID,
        entityColumn = Envelope.PROPERTY_GROUP_ID,
        entity = Envelope::class
    )
    val envelopes: List<Envelope>
): Parcelable