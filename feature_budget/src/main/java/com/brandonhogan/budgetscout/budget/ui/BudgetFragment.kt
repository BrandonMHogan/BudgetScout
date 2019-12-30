package com.brandonhogan.budgetscout.budget.ui

import android.app.ActivityOptions
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import com.brandonhogan.budgetscout.actions.Activities
import com.brandonhogan.budgetscout.actions.intentTo
import com.brandonhogan.budgetscout.budget.R
import com.brandonhogan.budgetscout.core.services.Log
import com.brandonhogan.budgetscout.repository.database.AppDatabase
import com.brandonhogan.budgetscout.repository.entity.Budget
import com.brandonhogan.budgetscout.repository.repo.BudgetRepo
import kotlinx.android.synthetic.main.budget_fragment.*
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class BudgetFragment : Fragment() {

    companion object {
        fun newInstance() = BudgetFragment()
    }

    private val model: BudgetViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.budget_fragment, container, false)

        val button: Button = view.findViewById(R.id.budget_button)

        button.setOnClickListener {

            startActivity(
                intentTo(Activities.About),
                ActivityOptions.makeSceneTransitionAnimation(requireActivity()).toBundle()
            )
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val budgetObserver = Observer<List<Budget>> { budgets ->

            for (budget in budgets) {
                Log.debug("Got Budget!!! ${budget.name}")
            }
        }

        model.budget.observe(this, budgetObserver)

    }
}
