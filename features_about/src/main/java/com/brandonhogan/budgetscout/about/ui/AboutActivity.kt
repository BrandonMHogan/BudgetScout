package com.brandonhogan.budgetscout.about.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.brandonhogan.budgetscout.about.R
import com.brandonhogan.budgetscout.about.ui.about.AboutFragment

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, AboutFragment.newInstance())
                .commitNow()
        }
    }

}
