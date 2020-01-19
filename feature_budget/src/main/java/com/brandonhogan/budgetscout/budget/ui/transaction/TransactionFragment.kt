package com.brandonhogan.budgetscout.budget.ui.transaction

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.brandonhogan.budgetscout.budget.R
import com.google.android.material.tabs.TabLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class TransactionFragment : Fragment() {

    companion object {
        fun newInstance() = TransactionFragment()
    }

    private val model: TransactionViewModel by viewModel()
    private lateinit var dateLabel: TextView
    private lateinit var tablayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.transaction_fragment, container, false)

        dateLabel = view.findViewById(R.id.date_label)
        tablayout = view.findViewById(R.id.tab_layout)

        val day = Calendar.getInstance().getDisplayName(Calendar.DAY_OF_MONTH, Calendar.LONG, Locale.ENGLISH)
        val month = Calendar.getInstance().getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH)

        dateLabel.text = "${day}\n${month}"

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}
