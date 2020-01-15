package com.brandonhogan.budgetscout.budget.ui.list

import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brandonhogan.budgetscout.budget.R
import com.brandonhogan.budgetscout.core.extensions.inflate
import com.brandonhogan.budgetscout.core.services.Log
import com.brandonhogan.budgetscout.repository.entity.Envelope
import com.brandonhogan.budgetscout.repository.entity.Group
import com.brandonhogan.budgetscout.repository.entity.relations.GroupWithEnvelopes

class BudgetAdapter(private val groups: List<GroupWithEnvelopes>, private val onGroupClickListener: (View, Group) -> Unit, private val onEnvelopeClickListener: (View, Group, Envelope) -> Unit) : RecyclerView.Adapter<BudgetAdapter.GroupHolder>()  {

    override fun getItemCount(): Int = groups.size

    override fun onBindViewHolder(holder: GroupHolder, position: Int) {
        holder.bind(groups[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupHolder {
        val inflatedView = parent.inflate(R.layout.budget_group_item, false)
        return GroupHolder(
            inflatedView,
            onGroupClickListener,
            onEnvelopeClickListener
        )
    }

    class GroupHolder(private val v: View, private val onGroupClickListener: (View, Group) -> Unit, private val onEnvelopeClickListener: (View, Group, Envelope) -> Unit): RecyclerView.ViewHolder(v), View.OnClickListener {

//        private val viewPool = RecyclerView.RecycledViewPool()
//        private var view: View = v
//        private var group: GroupWithEnvelopes? = null
//        val title: TextView = v.findViewById(R.id.group_title)
//        val recyclerView: RecyclerView = v.findViewById(R.id.group_recyclerView)

        init {
            v.setOnClickListener(this)
        }

        fun bind(group: GroupWithEnvelopes) {
//            this.group = group
//            title.text = this.group?.group?.name
//
//            val childLayoutManager = LinearLayoutManager(this.recyclerView.context, RecyclerView.VERTICAL, false)
//
//            this.recyclerView.apply {
//                layoutManager = childLayoutManager
//                adapter =
//                    EnvelopeAdapter(
//                        group.group,
//                        group.envelopes,
//                        onEnvelopeClickListener
//                    )
//                setRecycledViewPool(viewPool)
//                isNestedScrollingEnabled = false
//            }

        }

        override fun onClick(v: View?) {
//            Log.debug("Item Clicked")
//            group?.group?.let { group -> onGroupClickListener(v!!, group) }
        }
    }
}