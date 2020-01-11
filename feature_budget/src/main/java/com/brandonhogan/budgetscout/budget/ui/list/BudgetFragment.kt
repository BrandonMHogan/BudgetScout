package com.brandonhogan.budgetscout.budget.ui.list


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brandonhogan.budgetscout.budget.R
import com.brandonhogan.budgetscout.core.services.Log
import com.brandonhogan.budgetscout.repository.entity.Envelope
import com.brandonhogan.budgetscout.repository.entity.Group
import com.brandonhogan.budgetscout.repository.entity.relations.BudgetWithGroupsAndEnvelopes
import org.koin.androidx.viewmodel.ext.android.viewModel

class BudgetFragment : Fragment() {

    companion object {
        fun newInstance() =
            BudgetFragment()
    }

    private val model: BudgetViewModel by viewModel()
    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: BudgetAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        val view = inflater.inflate(R.layout.budget_fragment, container, false)

        // finds the views from the fragment
        toolbar = view.findViewById(R.id.toolbar)
        recyclerView = view.findViewById(R.id.recyclerView)

        // sets the toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        // sets the layout manager
        linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val budgetObserver = Observer<BudgetWithGroupsAndEnvelopes> { budget ->

            if (budget != null) {
                Log.debug("Got Budget!!! ${budget.budget.name}")
                adapter =
                    BudgetAdapter(
                        budget.groups,
                        onGroupClickListener = { view, group -> onGroupClick(view, group) },
                        onEnvelopeClickListener = { view, group, envelope -> onEnvelopeClick(view, group, envelope)}
                    )
                recyclerView.adapter = adapter
            }
        }

        model.budget.observe(this, budgetObserver)
    }



    private fun onGroupClick(view: View, group: Group) {
        Log.debug("WOOOO, Group clicked! ${group.name}")
    }

    private fun onEnvelopeClick(view: View, group: Group, envelope: Envelope) {
        Log.debug("WOOOO, Envelope clicked! ${group.name} : ${envelope.name}")
    }
}
