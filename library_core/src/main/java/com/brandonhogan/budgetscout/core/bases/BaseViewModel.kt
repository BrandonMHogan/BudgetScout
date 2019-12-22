package com.brandonhogan.budgetscout.core.bases

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob


/**
 * Base View Model
 *
 * Used to setup view model scoped coroutines for safer threading,
 * and setting up other shared functionality
 */
abstract class BaseViewModel: ViewModel() {
    /**
     * This is the job for all coroutines started by this viewModel.
     * Cancelling this job will cancel all coroutines started by this viewModel.
     */
    private val viewModelJob = SupervisorJob()

    /**
     * This is the main scope for all coroutines launched by this viewModel.
     * Passing viewModelJob allows for cancelling all coroutines launched by the uiScope
     * when calling viewModelJob.cancel()
     */
    protected val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}