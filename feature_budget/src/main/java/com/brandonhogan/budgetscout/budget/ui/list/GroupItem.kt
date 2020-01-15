package com.brandonhogan.budgetscout.budget.ui.list

import com.brandonhogan.budgetscout.budget.R
import com.brandonhogan.budgetscout.repository.entity.Group
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.ExpandableItem
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.budget_group_item.*

/**
 * @Creator         Brandon Hogan
 * @Date            2020-01-14
 * @File            GroupItem
 * @Description     Groupie item for group
 *
 */

class GroupItem(private val group: Group, private val onLongClickListener: () -> Unit) : Item(), ExpandableItem {

    var clickListener: ((GroupItem) -> Unit)? = null
    private lateinit var expandableGroup: ExpandableGroup

    override fun setExpandableGroup(onToggleListener: ExpandableGroup) {
        this.expandableGroup = onToggleListener
    }

    override fun getLayout() = R.layout.budget_group_item

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.group_title.text = group.name

        viewHolder.itemView.setOnClickListener {
            clickListener?.invoke(this)
            expandableGroup.onToggleExpanded()
        }

        viewHolder.itemView.setOnLongClickListener {
            onLongClickListener()
            return@setOnLongClickListener true
        }
    }
}