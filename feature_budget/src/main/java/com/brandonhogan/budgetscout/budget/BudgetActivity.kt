package com.brandonhogan.budgetscout.budget

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.brandonhogan.budgetscout.budget.ui.BudgetFragment

class BudgetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.budget_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, BudgetFragment.newInstance())
                .commitNow()
        }

        setKoin()
    }

    private fun setKoin() {

    }

}
