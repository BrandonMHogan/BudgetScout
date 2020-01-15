package com.brandonhogan.budgetscout.budget.ui.list

/**
 * @Creator         Brandon Hogan
 * @Date            2020-01-14
 * @File            EnvelopeItem
 * @Description     {{ foo }}
 */

import android.view.View
import com.brandonhogan.budgetscout.budget.R
import com.brandonhogan.budgetscout.repository.entity.Envelope
import com.brandonhogan.budgetscout.repository.entity.Group
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.budget_envelope_item.*
import kotlinx.android.synthetic.main.budget_group_item.*

/**
 * @Creator         Brandon Hogan
 * @Date            2020-01-14
 * @File            GroupItem
 * @Description     Groupie item for group
 *
 */

class EnvelopeItem(private val envelope: Envelope, private val onClickListener: () -> Unit, private val onLongClickListener: () -> Unit) : Item() {

    override fun getLayout() = R.layout.budget_envelope_item

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.envelope_title.text = envelope.name
        viewHolder.envelope_current.text = envelope.current.toString()
        viewHolder.envelope_total.text = envelope.total.toString()
        viewHolder.envelope_bar.max = envelope.total.toInt()
        viewHolder.envelope_bar.progress = envelope.current.toInt()

        viewHolder.itemView.setOnClickListener { onClickListener() }

        viewHolder.itemView.setOnLongClickListener {
            onLongClickListener()
            return@setOnLongClickListener true
        }
    }
}