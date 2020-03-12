package com.brandonhogan.budgetscout.budget.ui.envelope

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.brandonhogan.budgetscout.budget.R
import com.brandonhogan.budgetscout.budget.ui.list.BudgetFragmentDirections
import com.brandonhogan.budgetscout.budget.ui.transaction.TransactionData
import com.brandonhogan.budgetscout.repository.entity.Envelope
import com.brandonhogan.budgetscout.repository.entity.Transaction
import com.brandonhogan.budgetscout.repository.entity.relations.EnvelopeWithTransactions
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class EnvelopeDetailFragment : Fragment() {

    companion object {
        fun newInstance() = EnvelopeDetailFragment()
    }

    val args: EnvelopeDetailFragmentArgs by navArgs()
    private val model: EnvelopeDetailViewModel by viewModel { parametersOf(args.envelopeDetailModel) }


    private lateinit var envelopeNameLabel: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var adapter: GroupAdapter<GroupieViewHolder>? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.envelope_detail_fragment, container, false)

        envelopeNameLabel = view.findViewById(R.id.envelope_name)
        recyclerView = view.findViewById(R.id.envelope_transactions_recycler_view)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setObservers()
    }

    private fun setObservers() {

        // observes the envelope for changes
        val envelopeObserver = Observer<EnvelopeWithTransactions> { envelope ->
            envelopeNameLabel.text = envelope.envelope.name
            setList(envelope.transactions)
        }

        // observes the selected envelope from the budget service
        model.selectedEnvelope.observe(this, envelopeObserver)
    }


    private fun setList(transactions: List<Transaction>) {

        if (adapter == null) {
            // sets the adapter as the group adapter
            adapter = GroupAdapter()
            transactions.forEach { transaction ->

                adapter?.add(Section(EnvelopeDetailTransactionItem(
                    context,
                    transaction,
                    onClickListener = { editTransaction(transaction) },
                    onLongClickListener = {}
                )))
            }
        }

        // sets the layout manager
        linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
    }

    /**
     * Will navigate to the transaction fragment as long as we have a budget id
     */
    private fun editTransaction(transaction: Transaction) {

        // if the operation id is not null, then don't bother passing in the transaction.
        // just pass the operation id
        val transactionData = if(transaction.operationId != null)  {
            TransactionData(operationId = transaction.operationId)
        }
        else {
            TransactionData(toTransaction = transaction)
        }

        // mark this as an edit
        transactionData.isEdit = true

        val action = EnvelopeDetailFragmentDirections.actionEnvelopeDetailFragmentToTransactionFragment(transactionData = transactionData)
        NavHostFragment.findNavController(this).navigate(action)
    }

    /**
     * Navigates to the transaction fragment with an empty transaction data object
     */
    private fun addTransaction() {
        val transactionData = TransactionData()
        val action = EnvelopeDetailFragmentDirections.actionEnvelopeDetailFragmentToTransactionFragment(transactionData = transactionData)
        NavHostFragment.findNavController(this).navigate(action)
    }
}
