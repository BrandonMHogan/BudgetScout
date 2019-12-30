package com.brandonhogan.budgetscout.core.services

import com.brandonhogan.budgetscout.core.BuildConfig
import org.jetbrains.annotations.NonNls
import timber.log.Timber


class Log {
    companion object {

        /**
         * Setup for the logging service
         */
        fun setup() {
            // will log to the debug tree if in debug mode
            if (BuildConfig.DEBUG) {
                Timber.plant(Timber.DebugTree())
            }
        }

        /**
         * Debug Logging call
         */
        fun debug(@NonNls message: String, vararg args: Any) {
            Timber.d(message, args)
        }

        /**
         * Error Logging call
         */
        fun error(@NonNls message: String, vararg args: Any) {
            Timber.e(message, args)
        }

        /**
         * Error Logging with throwable
         */
        fun error(throwable: Throwable, @NonNls message: String, vararg args: Any) {
            Timber.e(throwable, message, args)
        }

        /**
         * Info Logging call
         */
        fun info(@NonNls message: String, vararg args: Any) {
            Timber.i(message, args)
        }
    }
}