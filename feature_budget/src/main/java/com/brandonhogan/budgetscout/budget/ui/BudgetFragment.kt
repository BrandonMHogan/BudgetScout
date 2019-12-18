package com.brandonhogan.budgetscout.budget.ui

import android.app.ActivityOptions
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.brandonhogan.budgetscout.actions.Activities
import com.brandonhogan.budgetscout.actions.intentTo
import com.brandonhogan.budgetscout.budget.R
import kotlinx.android.synthetic.main.budget_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class BudgetFragment : Fragment() {

    companion object {
        fun newInstance() = BudgetFragment()
    }

    val model: BudgetViewModel by viewModel()



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
        //viewModel = ViewModelProviders.of(this).get(BudgetViewModel::class.java)
        // TODO: Use the ViewModel

        message.text = model.sayHello()
    }

}
