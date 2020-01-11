package com.brandonhogan.budgetscout.budget.ui.group

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.brandonhogan.budgetscout.budget.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class GroupDetailFragment : Fragment() {

    companion object {
        fun newInstance() = GroupDetailFragment()
    }

    private val model: GroupDetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.budget_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}
