package com.brandonhogan.budgetscout.budget.ui

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.brandonhogan.budgetscout.budget.R
import com.brandonhogan.budgetscout.core.extensions.inflate
import com.brandonhogan.budgetscout.core.services.Log
import com.brandonhogan.budgetscout.repository.entity.Envelope

class EnvelopeAdapter(private val envelopes: List<Envelope>) : RecyclerView.Adapter<EnvelopeAdapter.EnvelopeHolder>()  {

    override fun getItemCount(): Int = envelopes.size

    override fun onBindViewHolder(holder: EnvelopeHolder, position: Int) {
        holder.bind(envelopes[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EnvelopeHolder {
        val inflatedView = parent.inflate(R.layout.budget_envelope_item, false)
        return EnvelopeHolder(inflatedView)
    }

    class EnvelopeHolder(v: View): RecyclerView.ViewHolder(v), View.OnClickListener {

        private var view: View = v
        private var envelope: Envelope? = null
        val title: TextView = v.findViewById(R.id.envelope_title)
        val current: TextView = v.findViewById(R.id.envelope_current)
        val total: TextView = v.findViewById(R.id.envelope_total)
        val note: TextView = v.findViewById(R.id.envelope_note)

        init {
            v.setOnClickListener(this)
        }

        fun bind(envelope: Envelope) {
            this.envelope = envelope
            title.text = this.envelope?.name
            current.text = this.envelope?.current.toString()
            total.text = this.envelope?.total.toString()
            note.text = this.envelope?.note
        }

        override fun onClick(v: View?) {
            Log.debug("Item Clicked")
        }
    }
}