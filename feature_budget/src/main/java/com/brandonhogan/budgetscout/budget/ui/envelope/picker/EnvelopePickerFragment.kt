package com.brandonhogan.budgetscout.budget.ui.envelope.picker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brandonhogan.budgetscout.budget.R
import com.brandonhogan.budgetscout.budget.ui.SharedBudgetViewModel
import com.brandonhogan.budgetscout.budget.ui.list.EnvelopeItem
import com.brandonhogan.budgetscout.budget.ui.list.GroupItem
import com.brandonhogan.budgetscout.repository.entity.Envelope
import com.brandonhogan.budgetscout.repository.entity.Group
import com.google.android.material.button.MaterialButton
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class EnvelopePickerFragment : DialogFragment() {

    companion object {
        fun newInstance() = EnvelopePickerFragment()
    }

    private val model: EnvelopePickerViewModel by viewModel()
    private val sharedBudgetModel: SharedBudgetViewModel by sharedViewModel()

    private lateinit var headerText: TextView
    private lateinit var selectButton: MaterialButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var adapter: GroupAdapter<GroupieViewHolder>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.envelope_picker_fragment, container, false)

        headerText = view.findViewById(R.id.envelope_picker_header)
        selectButton = view.findViewById(R.id.envelope_picker_button)
        recyclerView = view.findViewById(R.id.envelope_picker_recycler_view)

        // sets the layout manager
        linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager

        setList()

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = ViewGroup.LayoutParams.MATCH_PARENT
        dialog!!.window!!.attributes = params as WindowManager.LayoutParams
    }

    private fun setList() {
        if (adapter == null) {
            adapter = GroupAdapter()

            // for each group, will add a new expandable group.
            // each expandable group is a GroupItem.
            // It then adds a section to the Group item, and populates it with
            // EnvelopeItems
            sharedBudgetModel.budget.value?.groups?.forEach { group ->

                adapter?.add(Section(GroupItem(
                    group.group,
                    onLongClickListener = {}
                )).apply {

                    add(Section(
                        group.envelopes.map { envelope ->
                            EnvelopeItem(
                                envelope,
                                onClickListener = {onEnvelopeClick(group.group, envelope)},
                                onLongClickListener = {})
                        }
                    ))
                })
            }
        }
        recyclerView.adapter = adapter
    }

    private fun onEnvelopeClick(group: Group, envelope: Envelope) {

    }

}
