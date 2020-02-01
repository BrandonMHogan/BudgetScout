package com.brandonhogan.budgetscout.core.bases

import androidx.lifecycle.ViewModel
import com.brandonhogan.budgetscout.core.BuildConfig
import com.brandonhogan.budgetscout.core.services.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

/**
 * Base Service
 *
 * Used to setup service scoped coroutines for safer threading,
 * and setting up other shared functionality
 */
abstract class BaseService {

    private val serviceJob = Job()
    protected val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)

    /**
     * This is the coroutine exception handler. Used to handle any errors that bubble up to
     * the top level of the coroutine, instead of try catching errors in lower levels.
     */
    protected val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        handleErrors(throwable)
    }

    private fun handleErrors(throwable: Throwable) {

        Log.error(throwable,"Service Coroutine exception error")

        // if we are in a debug environment, throw the exception so it can be
        // fixed
        if(BuildConfig.DEBUG) {
            throw(throwable)
        }
    }

    /**
     * Used to clear the service of all running jobs
     */
    fun onCleared() {
        serviceJob.cancel()
    }
}