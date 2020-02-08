package com.brandonhogan.budgetscout.repository.testing

import com.brandonhogan.budgetscout.repository.entity.Budget
import com.brandonhogan.budgetscout.repository.entity.Envelope
import com.brandonhogan.budgetscout.repository.entity.Group
import com.brandonhogan.budgetscout.repository.entity.Transaction
import com.brandonhogan.budgetscout.repository.repo.budget.BudgetRepo
import com.brandonhogan.budgetscout.repository.repo.envelope.EnvelopeRepo
import com.brandonhogan.budgetscout.repository.repo.group.GroupRepo
import com.brandonhogan.budgetscout.repository.repo.transaction.TransactionRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

/**
 * @Creator         Brandon Hogan
 * @Date            2020-01-08
 * @File            BudgetCreator
 * @Description     Creator used to generator some basic budget data for testing
 */

class BudgetCreator(private val budgetRepo: BudgetRepo, private val groupRepo: GroupRepo, private val envelopeRepo: EnvelopeRepo, private val transactionRepo: TransactionRepo) {

    suspend fun createBasicBudget(deleteAll: Boolean = false, calendar: Calendar = Calendar.getInstance()): Long {

        // deletes all currently existing budgets if set true
        if (deleteAll) {
            clearBudget()
        }

        //creates the budget
        val budgetId = setBudget(Budget(name = "First Budget", month = calendar.get(Calendar.MONTH), year = calendar.get(Calendar.YEAR)))

        // creates group 1
        val groupIncomeId = setGroup(Group(name = "Income", colour = 0, isIncome = true, budgetId = budgetId))
        setEnvelope(Envelope(name = "Paycheck One", colour = 1, total = 2010.21, isCarryforward = false, groupId = groupIncomeId, note = ""))
        setEnvelope(Envelope(name = "Paycheck Two", colour = 1, total = 1995.92, isCarryforward = false, groupId = groupIncomeId, note = ""))
        setEnvelope(Envelope(name = "Birthday Gift", colour = 1, total = 200.0, isCarryforward = false, groupId = groupIncomeId, note = "Birthday gift"))

        // creates group 1
        val groupId = setGroup(Group(name = "Savings", colour = 0, budgetId = budgetId))
        val envelopeId1 = setEnvelope(Envelope(name = "RRSP", colour = 1, total = 30050.0, isCarryforward = false, groupId = groupId, note = ""))
        setEnvelope(Envelope(name = "TFSA", colour = 1, total = 250.0, isCarryforward = false, groupId = groupId, note = ""))
        setEnvelope(Envelope(name = "Emergency Fund", colour = 1, total = 100.0, isCarryforward = false, groupId = groupId, note = ""))
        setEnvelope(Envelope(name = "Fence Fund", colour = 1, total = 50.0, isCarryforward = false, groupId = groupId, note = "for the backyard fence"))

        // creates group 2
        val groupId2 = setGroup(Group(name = "Home", colour = 1, budgetId = budgetId))
        val envelopeId2 = setEnvelope(Envelope(name = "Mortgage", colour = 1, total = 1800.0, isCarryforward = false, groupId = groupId2, note = ""))
        val envelopeId3 = setEnvelope(Envelope(name = "Hydro", colour = 1, total = 120.0, isCarryforward = false, groupId = groupId2, note = ""))
        setEnvelope(Envelope(name = "Gas", colour = 1, total = 70.0, isCarryforward = false, groupId = groupId2, note = ""))
        setEnvelope(Envelope(name = "Insurance", colour = 1, total = 120.0, isCarryforward = false, groupId = groupId2, note = ""))

        // creates group 3
        val groupId3 = setGroup(Group(name = "Personal", colour = 2, budgetId = budgetId))
        setEnvelope(Envelope(name = "Hair Cut", colour = 1, total = 30.0, isCarryforward = false, groupId = groupId3, note = ""))
        setEnvelope(Envelope(name = "Fun Money", colour = 1, total = 225.0, isCarryforward = true, groupId = groupId3, note = ""))
        setEnvelope(Envelope(name = "Cat Food", colour = 1, total = 80.0, isCarryforward = false, groupId = groupId3, note = ""))
        setEnvelope(Envelope(name = "Phone Bill", colour = 1, total = 90.0, isCarryforward = false, groupId = groupId3, note = ""))

        // creates group 3
        val groupId4 = setGroup(Group(name = "Subscriptions", colour = 3, budgetId = budgetId))
        setEnvelope(Envelope(name = "Netflix", colour = 1, total = 13.99, isCarryforward = false, groupId = groupId4, note = ""))
        setEnvelope(Envelope(name = "Spotify", colour = 1, total = 9.99, isCarryforward = true, groupId = groupId4, note = ""))
        setEnvelope(Envelope(name = "MMO Subscription", colour = 1, total = 19.99, isCarryforward = false, groupId = groupId4, note = ""))

        setTransaction(Transaction(envelopeId = envelopeId1, amount = -19999.99, note = "This is a test note to see what it looks like"))
        setTransaction(Transaction(envelopeId = envelopeId1, amount = -50.00))
        setTransaction(Transaction(envelopeId = envelopeId1, amount = 25.99))
        setTransaction(Transaction(envelopeId = envelopeId1, amount = -10.00, note = "This is another test note to see what it looks like"))

        //transfer example
        setTransfer(Transaction(envelopeId = envelopeId2, amount = -5.00), Transaction(envelopeId = envelopeId1, amount = 5.00))

        setTransaction(Transaction(envelopeId = envelopeId2, amount = 1000.00))
        setTransaction(Transaction(envelopeId = envelopeId2, amount = 50.99))
        setTransaction(Transaction(envelopeId = envelopeId3, amount = 69.00))

        return budgetId
    }


    private suspend fun clearBudget() = withContext(Dispatchers.IO) {
        budgetRepo.deleteAll()
    }

    /**
     * Loads the budget from the repository, in the background thread
     */
    private suspend fun setBudget(budget: Budget): Long = withContext(Dispatchers.IO) {
        budgetRepo.insert(budget)
    }

    /**
     * Inserts the group into the database
     */
    private suspend fun setGroup(group: Group): Long = withContext(Dispatchers.IO) {
        groupRepo.insert(group)
    }

    /**
     * Inserts the envelope into the database
     */
    private suspend fun setEnvelope(envelope: Envelope): Long = withContext(Dispatchers.IO) {
        envelopeRepo.insert(envelope)
    }

    /**
     * Inserts the transaction into the database
     */
    private suspend fun setTransaction(transaction: Transaction): Long = withContext(Dispatchers.IO) {
        transactionRepo.insert(transaction)
    }

    private suspend fun setTransfer(from: Transaction, to: Transaction): List<Long> = withContext(Dispatchers.IO) {
        transactionRepo.transfer(from, to)
    }
}