package com.brandonhogan.budgetscout.about

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.brandonhogan.budgetscout.about.ui.AboutFragment

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container,
                    AboutFragment.newInstance()
                )
                .commitNow()
        }
    }

}