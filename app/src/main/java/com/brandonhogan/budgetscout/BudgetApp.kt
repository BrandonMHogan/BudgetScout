package com.brandonhogan.budgetscout

import android.app.Application
import com.brandonhogan.budgetscout.core.services.Log
import com.brandonhogan.budgetscout.repository.module.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

open class BudgetApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@BudgetApp)
            modules(repositoryModule)
        }

        setupLogging()
    }

    /**
     * Enables Logging within the application
     */
    private fun setupLogging() {
        Log.setup()
    }

}