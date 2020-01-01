package com.brandonhogan.budgetscout.repository.entity.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.brandonhogan.budgetscout.repository.entity.Budget
import com.brandonhogan.budgetscout.repository.entity.Group

/**
 * @Creator         Brandon Hogan
 * @Date            2019-12-31
 * @File            BudgetWithGroupsAndEnvelopes
 * @Description     Relational data class used to get a budget with its groups and envelopes.
 */
data class BudgetWithGroupsAndEnvelopes(
    @Embedded val budget: Budget,
    @Relation(
        parentColumn = Budget.PROPERTY_ID,
        entityColumn = Group.PROPERTY_BUDGET_ID,
        entity = Group::class
    )
    val groups: List<GroupWithEnvelopes>
)