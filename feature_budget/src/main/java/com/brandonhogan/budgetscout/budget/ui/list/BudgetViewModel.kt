package com.brandonhogan.budgetscout.budget.ui.list


import androidx.lifecycle.*
import com.brandonhogan.budgetscout.core.bases.BaseViewModel
import com.brandonhogan.budgetscout.repository.entity.Budget
import com.brandonhogan.budgetscout.repository.entity.Envelope
import com.brandonhogan.budgetscout.repository.entity.Group
import com.brandonhogan.budgetscout.repository.entity.relations.BudgetWithGroupsAndEnvelopes
import com.brandonhogan.budgetscout.repository.repo.budget.BudgetRepo
import com.brandonhogan.budgetscout.repository.testing.BudgetCreator
import kotlinx.coroutines.*
import java.util.*

class BudgetViewModel(private val budgetRepo: BudgetRepo, private val budgetCreator: BudgetCreator) : BaseViewModel() {

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

            // gets todays date
            val calendar = Calendar.getInstance()

            // attempts to load that budget
            val loadedBudget = loadBudget(calendar)
            if (loadedBudget != null) {
                budget.postValue(loadedBudget)
            }
            //TODO: instead of just making a budget, it should redirect to the
            // budget creator screen
            else {
                budgetCreator.createBasicBudget(true, calendar = calendar)
                budget.postValue(loadBudget(calendar))
            }
        }
    }


    /**
     * Loads the budget from the repository, in the background thread
     */
    private suspend fun loadBudget(calendar: Calendar): BudgetWithGroupsAndEnvelopes? = withContext(Dispatchers.IO) {
        budgetRepo.getWithGroupsAndEnvelopes(calendar)
    }

}