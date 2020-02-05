package com.brandonhogan.budgetscout.repository.repo.group

import androidx.annotation.WorkerThread
import com.brandonhogan.budgetscout.repository.entity.Group
import com.brandonhogan.budgetscout.repository.entity.relations.GroupWithEnvelopes

/**
 * @Creator         Brandon Hogan
 * @Date            2020-02-04
 * @File            GroupRepo
 * @Description     Handles all interaction with the group repository
 */

@WorkerThread
interface GroupRepo {

    suspend fun insert(group: Group): Long

    suspend fun get(id: Long): Group
    suspend fun getByBudget(budgetId: Long): List<GroupWithEnvelopes>
}