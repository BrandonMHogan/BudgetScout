package com.brandonhogan.budgetscout.budget.ui.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.brandonhogan.budgetscout.budget.R
import com.brandonhogan.budgetscout.core.utils.DateUtils
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
    private lateinit var fromGroupTextView: AutoCompleteTextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.transaction_fragment, container, false)

        dateLabel = view.findViewById(R.id.date_label)
        tablayout = view.findViewById(R.id.tab_layout)

        val day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        val month = Calendar.getInstance().getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH)
        val date = "$month $day${DateUtils.suffixes[day]}"
        dateLabel.text = date


        val COUNTRIES = arrayOf("Item 1", "Item 2", "Item 3", "Item 4")
        val fromGroupAdapter = ArrayAdapter<Any?>(requireContext(), R.layout.dropdown_menu_group_item,
            COUNTRIES
        )

        fromGroupTextView = view.findViewById(R.id.from_group_text_view)
        fromGroupTextView.setAdapter(fromGroupAdapter)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}
