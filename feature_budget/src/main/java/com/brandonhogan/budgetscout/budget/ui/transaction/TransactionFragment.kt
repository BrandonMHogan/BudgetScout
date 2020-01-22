package com.brandonhogan.budgetscout.budget.ui.transaction

import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.brandonhogan.budgetscout.budget.R
import com.brandonhogan.budgetscout.core.services.Log
import com.brandonhogan.budgetscout.core.utils.DateUtils
import com.brandonhogan.budgetscout.core.utils.DecimalDigitsInputFilter
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class TransactionFragment : Fragment() {

    companion object {
        fun newInstance() = TransactionFragment()
    }

    private val model: TransactionViewModel by viewModel()
    private lateinit var dateLabel: TextView
    private lateinit var fromGroupTextView: AutoCompleteTextView
    private lateinit var toGroupTextView: AutoCompleteTextView
    private lateinit var toGroupTextLayout: TextInputLayout

    private lateinit var expenseButton: MaterialButton
    private lateinit var incomeButton: MaterialButton
    private lateinit var transferButton: MaterialButton

    private lateinit var amountEditText: TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.transaction_fragment, container, false)

        dateLabel = view.findViewById(R.id.date_label)
        expenseButton = view.findViewById(R.id.expense_button)
        incomeButton = view.findViewById(R.id.income_button)
        transferButton = view.findViewById(R.id.transfer_button)

        expenseButton.setOnClickListener { onExpenseClicked() }
        incomeButton.setOnClickListener { onIncomeClicked() }
        transferButton.setOnClickListener { onTransferClicked() }

        amountEditText = view.findViewById(R.id.transaction_amount_edit_text)
        amountEditText.filters = arrayOf<InputFilter>(DecimalDigitsInputFilter(7, 2))

        val day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        val month = Calendar.getInstance().getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH)
        val date = "$month $day${DateUtils.suffixes[day]}"
        dateLabel.text = date


        val items = arrayOf("RRSP", "TFSA", "Insurance", "Netflix")
        val fromGroupAdapter = ArrayAdapter<Any?>(requireContext(), R.layout.dropdown_menu_group_item,
            items
        )

        fromGroupTextView = view.findViewById(R.id.from_envelope_text_view)
        fromGroupTextView.setAdapter(fromGroupAdapter)

        toGroupTextLayout = view.findViewById(R.id.to_envelope_input_layout)
        toGroupTextView = view.findViewById(R.id.to_envelope_text_view)
        toGroupTextView.setAdapter(fromGroupAdapter)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        expenseButton.performClick()
    }

    private fun onExpenseClicked() {
        Log.debug("Expense Clicked")
        toEnvelopeVisibility(false)
    }

    private fun onIncomeClicked() {
        Log.debug("Income Clicked")
        toEnvelopeVisibility(false)
    }

    private fun onTransferClicked() {
        Log.debug("Transfer Clicked")
        toEnvelopeVisibility(true)
    }

    private fun toEnvelopeVisibility(isVisible: Boolean) {
        toGroupTextLayout.visibility = if (isVisible) { View.VISIBLE } else { View.GONE }
    }
}
