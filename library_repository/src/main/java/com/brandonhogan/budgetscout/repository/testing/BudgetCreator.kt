package com.brandonhogan.budgetscout.repository.testing

import com.brandonhogan.budgetscout.repository.entity.Budget
import com.brandonhogan.budgetscout.repository.entity.Envelope
import com.brandonhogan.budgetscout.repository.entity.Group
import com.brandonhogan.budgetscout.repository.repo.budget.BudgetRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @Creator         Brandon Hogan
 * @Date            2020-01-08
 * @File            BudgetCreator
 * @Description     Creator used to generator some basic budget data for testing
 */

class BudgetCreator(private val budgetRepo: BudgetRepo) {

    suspend fun createBasicBudget(deleteAll: Boolean = false): Long {

        // deletes all currently existing budgets if set true
        if (deleteAll) {
            clearBudget()
        }

        //creates the budget
        val budgetId = setBudget(Budget(name = "First Budget")).first()

        // creates group 1
        val groupId = setGroup(Group(name = "Savings", colour = 0, budgetId = budgetId)).first()
        setEnvelope(Envelope(name = "RRSP", colour = 1, total = 250.0, current = 100.0, isCarryforward = false, groupId = groupId, note = ""))
        setEnvelope(Envelope(name = "TFSA", colour = 1, total = 250.0, current = 25.0, isCarryforward = false, groupId = groupId, note = ""))
        setEnvelope(Envelope(name = "Emergency Fund", colour = 1, total = 100.0, current = 10.0, isCarryforward = false, groupId = groupId, note = ""))
        setEnvelope(Envelope(name = "Fence Fund", colour = 1, total = 50.0, current = 50.0, isCarryforward = false, groupId = groupId, note = "for the backyard fence"))

        // creates group 2
        val groupId2 = setGroup(Group(name = "Home", colour = 1, budgetId = budgetId)).first()
        setEnvelope(Envelope(name = "Mortgage", colour = 1, total = 1800.0, current = 960.0, isCarryforward = false, groupId = groupId2, note = ""))
        setEnvelope(Envelope(name = "Hydro", colour = 1, total = 120.0, current = 0.0, isCarryforward = false, groupId = groupId2, note = ""))
        setEnvelope(Envelope(name = "Gas", colour = 1, total = 70.0, current = 70.0, isCarryforward = false, groupId = groupId2, note = ""))
        setEnvelope(Envelope(name = "Insurance", colour = 1, total = 120.0, current = 100.0, isCarryforward = false, groupId = groupId2, note = ""))

        // creates group 3
        val groupId3 = setGroup(Group(name = "Personal", colour = 2, budgetId = budgetId)).first()
        setEnvelope(Envelope(name = "Hair Cut", colour = 1, total = 30.0, current = 0.0, isCarryforward = false, groupId = groupId3, note = ""))
        setEnvelope(Envelope(name = "Fun Money", colour = 1, total = 225.0, current = 45.22, isCarryforward = true, groupId = groupId3, note = ""))
        setEnvelope(Envelope(name = "Cat Food", colour = 1, total = 80.0, current = 22.45, isCarryforward = false, groupId = groupId3, note = ""))
        setEnvelope(Envelope(name = "Phone Bill", colour = 1, total = 90.0, current = 90.0, isCarryforward = false, groupId = groupId3, note = ""))

        // creates group 3
        val groupId4 = setGroup(Group(name = "Subscriptions", colour = 3, budgetId = budgetId)).first()
        setEnvelope(Envelope(name = "Netflix", colour = 1, total = 13.99, current = 0.0, isCarryforward = false, groupId = groupId4, note = ""))
        setEnvelope(Envelope(name = "Spotify", colour = 1, total = 9.99, current = 0.0, isCarryforward = true, groupId = groupId4, note = ""))
        setEnvelope(Envelope(name = "MMO Subscription", colour = 1, total = 19.99, current = 19.99, isCarryforward = false, groupId = groupId4, note = ""))

        return budgetId
    }


    private suspend fun clearBudget() = withContext(Dispatchers.IO) {
        budgetRepo.deleteAll()
    }

    /**
     * Loads the budget from the repository, in the background thread
     */
    private suspend fun setBudget(budget: Budget): List<Long> = withContext(Dispatchers.IO) {
        budgetRepo.insert(budget)
    }

    /**
     * Inserts the group into the database
     */
    private suspend fun setGroup(group: Group): List<Long> = withContext(Dispatchers.IO) {
        budgetRepo.insertGroup(group)
    }

    /**
     * Inserts the envelope into the database
     */
    private suspend fun setEnvelope(envelope: Envelope): List<Long> = withContext(Dispatchers.IO) {
        budgetRepo.insertEnvelope(envelope)
    }
}