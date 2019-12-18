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
import kotlinx.android.synthetic.main.about_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AboutFragment : Fragment() {

    companion object {
        fun newInstance() = AboutFragment()
    }

    val model: AboutViewModel by viewModel()

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
        message.text = model.sayHello()
    }

}
