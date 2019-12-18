package com.brandonhogan.library_actions

import com.brandonhogan.budgetscout.actions.Activities
import com.brandonhogan.budgetscout.actions.PACKAGE_NAME
import org.junit.Test

import org.junit.Assert.*

/**
 * Testing for the Activites class names
 *
 * If a path ever changes, it needs to be reflected here manually.
 * This is important to make sure the path is valid, as it will not fail until
 * runtime if the path is changed
*/
class ActivityClassNamesUnitTests {

    // package name
    private val testPackageName = "com.brandonhogan.budgetscout"

    // activity class names
    private val testAboutActivityClassName = "$PACKAGE_NAME.about.AboutActivity"
    private val testBudgetActivityClassName = "$PACKAGE_NAME.budget.BudgetActivity"


    @Test
    fun action_PackageName() {
        assertEquals(testPackageName, PACKAGE_NAME)
    }

    @Test
    fun action_About_ClassName() {
        assertEquals(testAboutActivityClassName, Activities.About.className)
    }

    @Test
    fun action_Budget_ClassName() {
        assertEquals(testBudgetActivityClassName, Activities.Budget.className)
    }
}
