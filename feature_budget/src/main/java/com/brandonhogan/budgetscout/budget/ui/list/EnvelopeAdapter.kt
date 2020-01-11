package com.brandonhogan.budgetscout.budget.ui.list

import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.brandonhogan.budgetscout.budget.R
import com.brandonhogan.budgetscout.core.extensions.inflate
import com.brandonhogan.budgetscout.core.services.Log
import com.brandonhogan.budgetscout.repository.entity.Envelope
import com.brandonhogan.budgetscout.repository.entity.Group

class EnvelopeAdapter(private val group: Group, private val envelopes: List<Envelope>, private val onClickListener: (View, Group, Envelope) -> Unit) : RecyclerView.Adapter<EnvelopeAdapter.EnvelopeHolder>()  {

    override fun getItemCount(): Int = envelopes.size

    override fun onBindViewHolder(holder: EnvelopeHolder, position: Int) {
        holder.bind(group, envelopes[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EnvelopeHolder {
        val inflatedView = parent.inflate(R.layout.budget_envelope_item, false)
        return EnvelopeHolder(
            inflatedView,
            onClickListener
        )
    }

    class EnvelopeHolder(v: View, private val onClickListener: (View, Group, Envelope) -> Unit): RecyclerView.ViewHolder(v), View.OnClickListener {

        private var view: View = v
        private var group: Group? = null
        private var envelope: Envelope? = null
        val title: TextView = v.findViewById(R.id.envelope_title)
        val current: TextView = v.findViewById(R.id.envelope_current)
        val total: TextView = v.findViewById(R.id.envelope_total)
        //val note: TextView = v.findViewById(R.id.envelope_note)
        val bar: ProgressBar = v.findViewById(R.id.envelope_bar)

        init {
            v.setOnClickListener(this)
        }

        fun bind(group: Group, envelope: Envelope) {
            this.envelope = envelope
            this.group = group

            title.text = envelope.name
            current.text = envelope.current.toString()
            total.text = envelope.total.toString()
            //note.text = envelope.note

            bar.max = envelope.total.toInt()
            bar.progress = envelope.current.toInt()
        }

        override fun onClick(v: View?) {
            v?.let { view ->
                envelope?.let { envelope ->
                    group?.let { group ->
                        onClickListener(view, group, envelope)
                    }
                }
            }
        }
    }
}