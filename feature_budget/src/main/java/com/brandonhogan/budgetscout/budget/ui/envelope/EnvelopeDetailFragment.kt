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
        val envelopeObserver = Observer<Envelope> { envelope ->
            envelopeNameLabel.text = envelope.name
        }

        // sets the envelope observer, bound to the fragments lifecycle
        model.envelope.observe(this, envelopeObserver)


        // observes the transaction list for changes
        val transactionsObserver = Observer<List<Transaction>> { transactions ->
            setList(transactions)
        }

        // sets the envelope observer, bound to the fragments lifecycle
        model.transactions.observe(this, transactionsObserver)
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
    private fun editTransaction(transaction: Transaction = Transaction.newInstance()) {
        val transactionData = TransactionData(groupId = model.getGroupId(), transaction = transaction)
        val action = EnvelopeDetailFragmentDirections.actionEnvelopeDetailFragmentToTransactionFragment(transactionData = transactionData)
        NavHostFragment.findNavController(this).navigate(action)
    }
}
