package com.brandonhogan.budgetscout.budget.ui.envelope

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs

import com.brandonhogan.budgetscout.budget.R
import com.brandonhogan.budgetscout.budget.ui.list.BudgetAdapter
import com.brandonhogan.budgetscout.core.services.Log
import com.brandonhogan.budgetscout.repository.entity.Envelope
import com.brandonhogan.budgetscout.repository.entity.relations.BudgetWithGroupsAndEnvelopes
import org.koin.androidx.viewmodel.ext.android.viewModel

class EnvelopeDetailFragment : Fragment() {

    companion object {
        fun newInstance() = EnvelopeDetailFragment()
    }

    private val model: EnvelopeDetailViewModel by viewModel()
    val args: EnvelopeDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.envelope_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.getEnvelope(args.envelopeId)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // observes the envelope for changes
        val envelopeObserver = Observer<Envelope> { envelope ->

            val testLabel: TextView? = view?.findViewById(R.id.test_envelope_name)
            testLabel?.text = envelope.name

        }

        // sets the envelope observer, bound to the fragments lifecycle
        model.envelope.observe(this, envelopeObserver)
    }

}
