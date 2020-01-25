package com.brandonhogan.budgetscout.budget.ui.transaction

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.brandonhogan.budgetscout.budget.R
import com.brandonhogan.budgetscout.core.utils.DateUtils
import com.brandonhogan.budgetscout.core.utils.DecimalDigitsInputFilter
import com.brandonhogan.budgetscout.repository.TransactionType
import com.brandonhogan.budgetscout.repository.entity.Envelope
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class TransactionFragment : Fragment() {

    companion object {
        fun newInstance() = TransactionFragment()
    }

    private val model: TransactionViewModel by viewModel()
    val arguments: TransactionFragmentArgs by navArgs()


    private lateinit var dateLabel: TextView
    private lateinit var fromEnvelopeTextView: AutoCompleteTextView
    private lateinit var toEnvelopeTextView: AutoCompleteTextView
    private lateinit var toEnvelopeTextLayout: TextInputLayout

    private lateinit var expenseButton: MaterialButton
    private lateinit var incomeButton: MaterialButton
    private lateinit var transferButton: MaterialButton

    private lateinit var amountEditText: TextInputEditText

    private lateinit var saveButton: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.transaction_fragment, container, false)

        // date label
        dateLabel = view.findViewById(R.id.date_label)

        // toggle buttons
        expenseButton = view.findViewById(R.id.expense_button)
        incomeButton = view.findViewById(R.id.income_button)
        transferButton = view.findViewById(R.id.transfer_button)

        // from and to group text views
        fromEnvelopeTextView = view.findViewById(R.id.from_envelope_text_view)
        toEnvelopeTextLayout = view.findViewById(R.id.to_envelope_input_layout)
        toEnvelopeTextView = view.findViewById(R.id.to_envelope_text_view)

        // amount edit text
        amountEditText = view.findViewById(R.id.transaction_amount_edit_text)

        saveButton = view.findViewById(R.id.transaction_save_button)

        // sets the model from the arguments
        model.setModel(arguments.transaction, arguments.budgetId, arguments.groupId)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setObservers()
        setListeners()
        expenseButton.performClick()
    }

    /**
     * Sets the different listeners from the UI, passing back to the view model
     */
    private fun setListeners() {
        // date picker
        dateLabel.setOnClickListener { model.onDateClick() }

        // toggle button click listeners
        expenseButton.setOnClickListener { model.setTransactionType(TransactionType.Expense) }
        incomeButton.setOnClickListener { model.setTransactionType(TransactionType.Income) }
        transferButton.setOnClickListener { model.setTransactionType(TransactionType.Transfer) }
        // save button
        saveButton.setOnClickListener { model.onSave() }

        // sets a filter for the amount edit text
        amountEditText.filters = arrayOf<InputFilter>(DecimalDigitsInputFilter(7, 2))

        fromEnvelopeTextView.setOnItemClickListener{ adapter, view, position, id ->
            model.fromEnvelopeItemClicked(position)
        }

        toEnvelopeTextView.setOnItemClickListener{ _, _, position, _ ->
            model.toEnvelopeItemClicked(position)
        }

        toEnvelopeTextView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                model.toEnvelopeTextChanged(toEnvelopeTextView.text.toString())
            }
        })

        fromEnvelopeTextView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                model.fromEnvelopeTextChanged(p0.toString())
            }
        })

//        fromEnvelopeTextView.onFocusChangeListener(object: View.OnFocusChangeListener{
//            override fun onFocusChange(v: View?, hasFocus: Boolean) {
//            }
//        })

        amountEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                model.onAmountChanged(amountEditText.text.toString())
            }
        })
    }

    /**
     * Sets the observables from the model here
     */
    private fun setObservers() {
        model.ui.observe(this, Observer { uiModel ->
            if(uiModel.displayError) {
                MaterialAlertDialogBuilder(context)
                    .setTitle(R.string.transaction_validation_title)
                    .setMessage(R.string.transaction_validation_message)
                    .setPositiveButton(R.string.transaction_validation_button, null)
                    .show()
            }
        })
        model.date.observe(this, Observer { calendar ->
            onDateChange(calendar)
        })

        model.envelopes.observe(this, Observer { envelopes ->
            onEnvelopeListChange(envelopes)
        })

        model.transactionType.observe(this, Observer { transactionType ->
            onTransactionTypeChange(transactionType)
        })
    }

    /**
     * Updates the UI to match the new date
     */
    private fun onDateChange(calendar: Calendar) {
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH)
        val date = "$month $day${DateUtils.suffixes[day]}"
        dateLabel.text = date
    }

    /**
     * Opens a date picker, using the current date as the start point
     */
    private fun onDateClicked() {

        model.date.value?.let { date ->
            val currentYear = date.get(Calendar.YEAR)
            val currentMonth = date.get(Calendar.MONTH)
            val currentDay = date.get(Calendar.DAY_OF_MONTH)

            val minDate = Calendar.getInstance()
            minDate.set(Calendar.YEAR, currentYear)
            minDate.set(Calendar.MONTH, currentMonth)
            minDate.set(Calendar.DAY_OF_MONTH, 1)

            val maxDate = Calendar.getInstance()
            maxDate.set(Calendar.YEAR, currentYear)
            maxDate.set(Calendar.MONTH, currentMonth)
            maxDate.set(Calendar.DAY_OF_MONTH, 1)
            maxDate.add(Calendar.MONTH, 1)
            maxDate.add(Calendar.DAY_OF_MONTH, -1)

            val datePicker = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->

                date.set(Calendar.YEAR, year)
                date.set(Calendar.MONTH, monthOfYear)
                date.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                model.date.postValue(date)

            }, currentYear, currentMonth, currentDay).apply {
                datePicker.minDate = minDate.timeInMillis
                datePicker.maxDate = maxDate.timeInMillis
            }

            datePicker.show()
        }
    }

    /**
     * Called whenever the list of available envelopes changes
     */
    private fun onEnvelopeListChange(envelopes: List<Envelope>) {
        val envelopeAdapter = ArrayAdapter<Any?>(requireContext(), R.layout.dropdown_menu_group_item,
            envelopes.map { envelope -> envelope.name }
        )
        fromEnvelopeTextView.setAdapter(envelopeAdapter)
        toEnvelopeTextView.setAdapter(envelopeAdapter)
    }

    /**
     * On Transaction Type change, it will hide or show the group text layout,
     * and update the UI
     */
    private fun onTransactionTypeChange(transactionType: TransactionType) {

        when (transactionType) {
            TransactionType.Income -> {
                toEnvelopeTextLayout.visibility = View.GONE
            }
            TransactionType.Expense -> {
                toEnvelopeTextLayout.visibility = View.GONE
            }
            TransactionType.Transfer -> {
                toEnvelopeTextLayout.visibility = View.VISIBLE
            }
        }


    }
}
