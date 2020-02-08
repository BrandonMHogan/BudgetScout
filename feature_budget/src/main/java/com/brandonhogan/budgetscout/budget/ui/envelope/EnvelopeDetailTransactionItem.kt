package com.brandonhogan.budgetscout.budget.ui.envelope

import android.content.Context
import android.view.View
import com.brandonhogan.budgetscout.budget.R
import com.brandonhogan.budgetscout.core.utils.CurrencyUtils
import com.brandonhogan.budgetscout.core.utils.DateUtils
import com.brandonhogan.budgetscout.repository.entity.Envelope
import com.brandonhogan.budgetscout.repository.entity.Transaction
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.budget_envelope_item.*
import kotlinx.android.synthetic.main.envelope_detail_item.*

/**
 * @Creator         Brandon Hogan
 * @Date            2020-02-07
 * @File            EnvelopeDetailItem
 * @Description     {{ foo }}
 */

/**
 * @Creator         Brandon Hogan
 * @Date            2020-01-14
 * @File            GroupItem
 * @Description     Groupie item for group
 *
 */

class EnvelopeDetailTransactionItem(private val context: Context?, private val transaction: Transaction, private val onClickListener: () -> Unit, private val onLongClickListener: () -> Unit) : Item() {

    override fun getLayout() = R.layout.envelope_detail_item

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        viewHolder.transaction_note_button.visibility = if(transaction.note.isNotEmpty()) { View.VISIBLE } else {View.INVISIBLE }
        viewHolder.transaction_note_button.setOnClickListener { openNote() }
        viewHolder.transaction_date.text = DateUtils.getSimpleDayOfMonth(transaction.date)
        viewHolder.transaction_amount.text = CurrencyUtils.displayAsCurrency(transaction.amount)

        if(transaction.operationId != null) {
            viewHolder.transaction_type_icon.setImageResource(R.drawable.ic_transfer_white_24dp)
        }
        else if(transaction.amount > 0) {
            viewHolder.transaction_type_icon.setImageResource(R.drawable.ic_add_box_white_24dp)
        }
        else {
            viewHolder.transaction_type_icon.setImageResource(R.drawable.ic_minus_box_white_24dp)
        }

        viewHolder.itemView.setOnClickListener { onClickListener() }

        viewHolder.itemView.setOnLongClickListener {
            onLongClickListener()
            return@setOnLongClickListener true
        }
    }

    private fun openNote() {
        context?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle(R.string.transaction_note_title)
                .setMessage(transaction.note)
                .setPositiveButton(R.string.transaction_note_button, null)
                .show()
        }

    }
}