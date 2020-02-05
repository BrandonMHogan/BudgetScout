package com.brandonhogan.budgetscout.repository.entity.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.brandonhogan.budgetscout.repository.entity.Envelope
import com.brandonhogan.budgetscout.repository.entity.Transaction

/**
 * @Creator         Brandon Hogan
 * @Date            2019-12-31
 * @File            GroupWithEnvelopes
 * @Description     Relational data class used to get a group and its envelopes
 */
data class EnvelopesWithTransactions(
    @Embedded val envelope: Envelope,
    @Relation(
        parentColumn = Envelope.PROPERTY_ID,
        entityColumn = Transaction.PROPERTY_ENVELOPE_ID,
        entity = Envelope::class
    )
    val envelopes: List<Envelope>
)