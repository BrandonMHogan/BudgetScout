package com.brandonhogan.budgetscout.budget

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.brandonhogan.budgetscout.budget.modules.budgetModule
import com.brandonhogan.budgetscout.budget.ui.BudgetFragment
import org.koin.core.context.loadKoinModules

class BudgetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadModules()

        setContentView(R.layout.budget_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, BudgetFragment.newInstance())
                .commitNow()
        }
    }

    /// Loads the modules for the about feature
    private fun loadModules() {
        loadKoinModules(budgetModule)
    }

}
