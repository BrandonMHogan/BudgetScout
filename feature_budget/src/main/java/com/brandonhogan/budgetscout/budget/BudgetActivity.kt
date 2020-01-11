package com.brandonhogan.budgetscout.budget

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.brandonhogan.budgetscout.budget.modules.budgetModule
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class BudgetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadModules()
        setContentView(R.layout.budget_activity)
    }

    /**
     * Will display the general menu, which contains things like profile and settings
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.general_menu, menu)
        return true
    }

    /**
     * Handles click events from the menu options
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_profile -> {
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    /**
     * Unload budget modules
     */
    override fun onStop() {
        super.onStop()
        unloadKoinModules(budgetModule)
    }

    /**
     * Load budget modules
     */
    private fun loadModules() {
        loadKoinModules(budgetModule)
    }

}
