package com.brandonhogan.budgetscout.budget.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brandonhogan.budgetscout.budget.R
import com.brandonhogan.budgetscout.budget.extensions.MotionLayoutWithState
import com.brandonhogan.budgetscout.budget.services.BudgetService
import com.brandonhogan.budgetscout.budget.ui.transaction.TransactionData
import com.brandonhogan.budgetscout.core.services.Log
import com.brandonhogan.budgetscout.repository.entity.Envelope
import com.brandonhogan.budgetscout.repository.entity.Group
import com.brandonhogan.budgetscout.repository.entity.Transaction
import com.brandonhogan.budgetscout.repository.entity.relations.BudgetWithGroupsAndEnvelopes
import com.github.mikephil.charting.charts.PieChart
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class BudgetFragment : Fragment() {

    companion object {
        fun newInstance() =
            BudgetFragment()
    }

    private val model: BudgetViewModel by viewModel()

    private lateinit var motionLayout: MotionLayoutWithState
    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var pieChart: PieChart
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var actionButton: FloatingActionButton
    private var adapter: GroupAdapter<GroupieViewHolder>? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        val view = inflater.inflate(R.layout.budget_fragment, container, false)

        // finds the views from the fragment
        motionLayout = view.findViewById(R.id.motion_layout)
        toolbar = view.findViewById(R.id.toolbar)
        toolbar.title = " "
        recyclerView = view.findViewById(R.id.recyclerView)
        actionButton = view.findViewById(R.id.floating_action_button)

        // sets the toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        // sets the layout manager
        linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager

        actionButton.setOnClickListener { onAddTransaction() }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setObservers()
    }

    /**
     * Sets the observers for the view
     */
    private fun setObservers() {

        // budget observer that will set the adapter if first load, or
        // updates the data otherwise
        val budgetObserver = Observer<BudgetWithGroupsAndEnvelopes> { budget ->

            if (budget != null && adapter == null) {
                adapter = GroupAdapter()


                // for each group, will add a new expandable group.
                // each expandable group is a GroupItem.
                // It then adds a section to the Group item, and populates it with
                // EnvelopeItems
                budget.groups.forEach { group ->

                    adapter?.add(ExpandableGroup(GroupItem(
                        group.group,
                        onLongClickListener = {onGroupLongClick(group.group)}
                    ), true).apply {

                        add(Section(
                            group.envelopes.map { envelope ->
                                EnvelopeItem(
                                    envelope,
                                    onClickListener = {onEnvelopeClick(group.group, envelope)},
                                    onLongClickListener = {onEnvelopeLongClick(group.group, envelope)})
                            }
                        ))
                    })
                }
            }
            recyclerView.adapter = adapter
        }

        // observes the shared budget object
        model.budget.observe(this, budgetObserver)
    }

    /**
     * Will navigate to the transaction fragment as long as we have a budget id
     */
    private fun onAddTransaction(groupId: Long? = null, transaction: Transaction = Transaction.newInstance()) {
        val transactionData = TransactionData(groupId = groupId, transaction = transaction)
        val action = BudgetFragmentDirections.actionBudgetFragmentToTransactionFragment(transactionData = transactionData)
        findNavController(this).navigate(action)
    }

    private fun onGroupLongClick(group: Group) {
        Log.debug("WOOOO, Group long clicked! ${group.name}")
    }

    private fun onEnvelopeClick(group: Group, envelope: Envelope) {
        Log.debug("WOOOO, Envelope clicked! ${group.name} : ${envelope.name}")

        val action = BudgetFragmentDirections.actionBudgetFragmentToEnvelopeDetailFragment(envelope.id)
        findNavController(this).navigate(action)
    }

    private fun onEnvelopeLongClick(group: Group, envelope: Envelope) {
        Log.debug("WOOOO, Envelope long clicked! ${group.name} : ${envelope.name}")
    }
}
