package com.brandonhogan.budgetscout.about.ui

import android.app.ActivityOptions
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.brandonhogan.budgetscout.about.R
import com.brandonhogan.budgetscout.actions.Activities
import com.brandonhogan.budgetscout.actions.intentTo

class AboutFragment : Fragment() {

    companion object {
        fun newInstance() = AboutFragment()
    }

    private lateinit var viewModel: AboutViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.about_fragment, container, false)

        val button: Button = view.findViewById(R.id.about_button)

        button.setOnClickListener {

            startActivity(
                intentTo(Activities.Budget),
                ActivityOptions.makeSceneTransitionAnimation(requireActivity()).toBundle()
            )
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AboutViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
