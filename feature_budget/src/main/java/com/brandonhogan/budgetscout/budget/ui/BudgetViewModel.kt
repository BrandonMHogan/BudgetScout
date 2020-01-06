package com.brandonhogan.budgetscout.budget.ui


import androidx.lifecycle.*
import com.brandonhogan.budgetscout.core.bases.BaseViewModel
import com.brandonhogan.budgetscout.core.services.Log
import com.brandonhogan.budgetscout.repository.entity.Budget
import com.brandonhogan.budgetscout.repository.entity.Envelope
import com.brandonhogan.budgetscout.repository.entity.Group
import com.brandonhogan.budgetscout.repository.entity.relations.BudgetWithGroupsAndEnvelopes
import com.brandonhogan.budgetscout.repository.repo.budget.BudgetRepo
import kotlinx.coroutines.*

class BudgetViewModel(private val budgetRepo: BudgetRepo) : BaseViewModel() {

    /**
     * Observable by its view
     */
    val budget: MutableLiveData<BudgetWithGroupsAndEnvelopes> by lazy {
        MutableLiveData<BudgetWithGroupsAndEnvelopes>()
    }

    /**
     * On init, will load the active budget and post it
     */
    init {
        viewModelScope.launch(exceptionHandler) {
            // will add a new budget first
            val budgetId = setBudget(Budget(name = "First Budget")).first()

            val groupId = setGroup(Group(name = "Savings", colour = 0, budgetId = budgetId)).first()
            setEnvelope(Envelope(name = "RRSP", colour = 1, total = 250.0, current = 100.0, isCarryforward = false, groupId = groupId, note = ""))
            setEnvelope(Envelope(name = "TFSA", colour = 1, total = 250.0, current = 25.0, isCarryforward = false, groupId = groupId, note = ""))
            setEnvelope(Envelope(name = "Emergency Fund", colour = 1, total = 100.0, current = 10.0, isCarryforward = false, groupId = groupId, note = ""))
            setEnvelope(Envelope(name = "Fence Fund", colour = 1, total = 50.0, current = 50.0, isCarryforward = false, groupId = groupId, note = "for the backyard fence"))


            val groupId2 = setGroup(Group(name = "Home", colour = 1, budgetId = budgetId)).first()
            setEnvelope(Envelope(name = "Mortgage", colour = 1, total = 1800.0, current = 960.0, isCarryforward = false, groupId = groupId2, note = ""))
            setEnvelope(Envelope(name = "Hydro", colour = 1, total = 120.0, current = 0.0, isCarryforward = false, groupId = groupId2, note = ""))
            setEnvelope(Envelope(name = "Gas", colour = 1, total = 70.0, current = 70.0, isCarryforward = false, groupId = groupId2, note = ""))
            setEnvelope(Envelope(name = "Insurance", colour = 1, total = 120.0, current = 100.0, isCarryforward = false, groupId = groupId2, note = ""))



            val groupId3 = setGroup(Group(name = "Personal", colour = 2, budgetId = budgetId)).first()
            setEnvelope(Envelope(name = "Hair Cut", colour = 1, total = 30.0, current = 0.0, isCarryforward = false, groupId = groupId3, note = ""))
            setEnvelope(Envelope(name = "Fun Money", colour = 1, total = 225.0, current = 45.22, isCarryforward = true, groupId = groupId3, note = ""))
            setEnvelope(Envelope(name = "Cat Food", colour = 1, total = 80.0, current = 22.45, isCarryforward = false, groupId = groupId3, note = ""))
            setEnvelope(Envelope(name = "Phone Bill", colour = 1, total = 90.0, current = 90.0, isCarryforward = false, groupId = groupId3, note = ""))

            // then load the active budget
            budget.postValue(loadActiveBudget())
        }
    }

    /**
     * Loads the budget from the repository, in the background thread
     */
    private suspend fun loadActiveBudget(): BudgetWithGroupsAndEnvelopes = withContext(Dispatchers.IO) {
        budgetRepo.getWithGroupsAndEnvelopes(1)
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