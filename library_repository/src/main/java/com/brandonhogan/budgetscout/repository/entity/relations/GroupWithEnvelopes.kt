package com.brandonhogan.budgetscout.repository.entity.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.brandonhogan.budgetscout.repository.entity.Envelope
import com.brandonhogan.budgetscout.repository.entity.Group

/**
 * @Creator         Brandon Hogan
 * @Date            2019-12-31
 * @File            GroupWithEnvelopes
 * @Description     Relational data class used to get a group and its envelopes
 */
data class GroupWithEnvelopes(
    @Embedded val group: Group,
    @Relation(
        parentColumn = Group.PROPERTY_ID,
        entityColumn = Envelope.PROPERTY_GROUP_ID,
        entity = Envelope::class
    )
    val envelopes: List<Envelope>
)