package com.brandonhogan.budgetscout.budget.ui.envelope

import com.brandonhogan.budgetscout.budget.R
import com.brandonhogan.budgetscout.repository.entity.Envelope
import com.brandonhogan.budgetscout.repository.entity.Transaction
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

class EnvelopeDetailTransactionItem(private val transaction: Transaction, private val onClickListener: () -> Unit, private val onLongClickListener: () -> Unit) : Item() {

    override fun getLayout() = R.layout.envelope_detail_item

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.transaction_date.text = transaction.getDisplayDate()
        viewHolder.transaction_note.text = transaction.note
        viewHolder.transaction_amount.text = transaction.getDisplayAmount()

        viewHolder.itemView.setOnClickListener { onClickListener() }

        viewHolder.itemView.setOnLongClickListener {
            onLongClickListener()
            return@setOnLongClickListener true
        }
    }
}