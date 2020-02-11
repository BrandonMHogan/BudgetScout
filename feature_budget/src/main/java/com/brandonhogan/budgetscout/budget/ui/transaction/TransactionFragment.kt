package com.brandonhogan.budgetscout.budget.ui.transaction

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.navArgs
import com.brandonhogan.budgetscout.budget.R
import com.brandonhogan.budgetscout.budget.ui.envelope.picker.EnvelopePickerViewModel
import com.brandonhogan.budgetscout.core.utils.DateUtils
import com.brandonhogan.budgetscout.core.utils.DecimalDigitsInputFilter
import com.brandonhogan.budgetscout.repository.TransactionType
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.util.*


class TransactionFragment : Fragment() {

    companion object {
        fun newInstance() = TransactionFragment()
    }

    val arguments: TransactionFragmentArgs by navArgs()
    private val envelopePickerModel: EnvelopePickerViewModel by sharedViewModel()
    private val model: TransactionViewModel by viewModel { parametersOf(arguments.transactionData) }


    private lateinit var dateLabel: TextView
    private lateinit var toEnvelopeButton: MaterialButton
    private lateinit var fromEnvelopeButton: MaterialButton

    private lateinit var expenseButton: MaterialButton
    private lateinit var incomeButton: MaterialButton
    private lateinit var transferButton: MaterialButton

    private lateinit var amountEditText: TextInputEditText
    private lateinit var noteEditText: TextInputEditText

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
        toEnvelopeButton = view.findViewById(R.id.to_envelope_button)
        fromEnvelopeButton = view.findViewById(R.id.from_envelope_button)

        // amount edit text
        amountEditText = view.findViewById(R.id.transaction_amount_edit_text)

        // note edit text
        noteEditText = view.findViewById(R.id.transaction_note_edit_text)

        saveButton = view.findViewById(R.id.transaction_save_button)

        model
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
        dateLabel.setOnClickListener { onDateClicked() }

        // toggle button click listeners
        expenseButton.setOnClickListener { model.setTransactionType(TransactionType.Expense) }
        incomeButton.setOnClickListener { model.setTransactionType(TransactionType.Income) }
        transferButton.setOnClickListener { model.setTransactionType(TransactionType.Transfer) }
        // save button
        saveButton.setOnClickListener { model.onSave() }

        toEnvelopeButton.setOnClickListener { displayEnvelopePicker(false) }
        fromEnvelopeButton.setOnClickListener { displayEnvelopePicker(true) }

        // sets a filter for the amount edit text
        amountEditText.filters = arrayOf<InputFilter>(DecimalDigitsInputFilter(7, 2))

        amountEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                model.onAmountChanged(amountEditText.text.toString())
            }
        })

        noteEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                model.onNoteChanged(noteEditText.text.toString())
            }
        })
    }

    private fun displayEnvelopePicker(isFromEnvelope: Boolean = false) {
        envelopePickerModel.isFromEnvelope = isFromEnvelope
        val action = TransactionFragmentDirections.actionTransactionFragmentToEnvelopePickerFragment()
        findNavController(this).navigate(action)
    }

    /**
     * Sets the observables from the model here
     */
    private fun setObservers() {

        model.ui.observe(this, Observer { model ->

            fromEnvelopeButton.text = if(model.fromEnvelopName.isEmpty()) { getString(R.string.from_envelope) } else { model.fromEnvelopName }
            toEnvelopeButton.text = if(model.toEnvelopeName.isEmpty()) { getString(R.string.to_envelope) } else { model.toEnvelopeName }

            if(amountEditText.text.isNullOrBlank()) {
                amountEditText.setText(model.amount.toString())
            }

            if(noteEditText.text.isNullOrBlank()) {
                noteEditText.setText(model.note)
            }

            onDateChange(model.date)
        })

        model.displayMessage.observe(this, Observer { displayMessage ->

            if(displayMessage.isNotEmpty()) {
                val message = displayMessage.joinToString(separator = "\n") { message -> getString(message) }

                MaterialAlertDialogBuilder(context)
                    .setMessage(message)
                    .setPositiveButton(R.string.transaction_validation_button) { _, _ ->
                        if(displayMessage.contains(R.string.transaction_save_success)) {
                            this.goBack()
                        }
                    }
                    .show()

                // clears the display message after displaying it
                model.displayMessage.value = listOf()
            }
        })

        model.envelopes.observe(this, Observer { envelopes ->
           // setEnvelopeButtons(envelopes)
        })

        model.transactionType.observe(this, Observer { transactionType ->
            onTransactionTypeChange(transactionType)
        })

        envelopePickerModel.selectedEnvelope.observe(this, Observer {envelope ->
            model.envelopeSelected(envelopePickerModel.isFromEnvelope, envelope)
        })
    }

    private fun goBack() {
        findNavController(this).popBackStack()
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

        model.ui.value?.let { model ->
            val currentYear = model.date.get(Calendar.YEAR)
            val currentMonth = model.date.get(Calendar.MONTH)
            val currentDay = model.date.get(Calendar.DAY_OF_MONTH)

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

                val newDate = Calendar.getInstance()
                newDate.set(Calendar.YEAR, year)
                newDate.set(Calendar.MONTH, monthOfYear)
                newDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                this.model.onDateChanged(newDate)

            }, currentYear, currentMonth, currentDay).apply {
                datePicker.minDate = minDate.timeInMillis
                datePicker.maxDate = maxDate.timeInMillis
            }

            datePicker.show()
        }
    }

    /**
     * On Transaction Type change, it will hide or show the group text layout,
     * and update the UI
     */
    private fun onTransactionTypeChange(transactionType: TransactionType) {

        when (transactionType) {
            TransactionType.Income -> {
                fromEnvelopeButton.visibility = View.GONE
            }
            TransactionType.Expense -> {
                fromEnvelopeButton.visibility = View.GONE
            }
            TransactionType.Transfer -> {
                fromEnvelopeButton.visibility = View.VISIBLE
            }
        }


    }
}
