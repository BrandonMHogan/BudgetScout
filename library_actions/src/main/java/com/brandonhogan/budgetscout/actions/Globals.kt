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
fun intentTo(addressableActivity: AddressableActivity, clearTop: Boolean = false): Intent {
    val intent = Intent(Intent.ACTION_VIEW).setClassName(
        PACKAGE_NAME,
        addressableActivity.className)

    // Will remove all previous activities from the stack, making this new activity the top
    // of the stack
    if(clearTop)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)

    return intent
}