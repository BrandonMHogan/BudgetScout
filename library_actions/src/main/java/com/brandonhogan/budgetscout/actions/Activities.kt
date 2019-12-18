package com.brandonhogan.budgetscout.actions

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
        override val className = "$PACKAGE_NAME.about.AboutActivity"
    }

    /**
     * BudgetActivity
     */
    object Budget : AddressableActivity {
        override val className = "$PACKAGE_NAME.budget.BudgetActivity"
    }

}