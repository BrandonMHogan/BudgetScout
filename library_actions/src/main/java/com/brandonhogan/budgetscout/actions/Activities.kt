package com.brandonhogan.budgetscout.actions

import android.content.Intent

/**
 * Is used to define actions when implicit intents are needed
 */

    const val PACKAGE_NAME = "com.brandonhogan.budgetscout"

    /**
     * An [android.app.Activity] that can be addressed by an intent.
     */
    interface AddressableActivity {
        /**
         * The activity class name.
         */
        val className: String
    }


    fun intentTo(addressableActivity: AddressableActivity): Intent {
        return Intent(Intent.ACTION_VIEW).setClassName(
            PACKAGE_NAME,
            addressableActivity.className)
    }

/**
 * All addressable activities.
 *
 * Can contain intent extra names or functions associated with the activity creation.
 */
object Activities {

    /**
     * AboutActivity
     */
    object About : AddressableActivity {
        override val className = "$PACKAGE_NAME.about.ui.AboutActivity"
    }

}