package com.brandonhogan.budgetscout.actions

import android.content.Intent

/**
 * Global constants and functions used in performing Activity Actions
 */

/// the application package name
const val PACKAGE_NAME = "com.brandonhogan.budgetscout"

/**
 * The core function used to navigate implicitly between activities from different modules.
 * Example of using it from an activity:
 *
startActivity(
    intentTo(Activities.About),
    ActivityOptions.makeSceneTransitionAnimation(requireActivity()).toBundle()
)
 */
fun intentTo(addressableActivity: AddressableActivity): Intent {
    return Intent(Intent.ACTION_VIEW).setClassName(
        PACKAGE_NAME,
        addressableActivity.className)
}