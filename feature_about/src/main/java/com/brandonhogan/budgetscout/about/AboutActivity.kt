package com.brandonhogan.budgetscout.about

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.brandonhogan.budgetscout.about.modules.aboutModule
import com.brandonhogan.budgetscout.about.ui.AboutFragment
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class AboutActivity : AppCompatActivity() {

    private val loadFeature by lazy { loadKoinModules(aboutModule) }
    private fun injectFeature() = loadFeature

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectFeature()

        setContentView(R.layout.about_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container,
                    AboutFragment.newInstance()
                )
                .commitNow()
        }
    }

    /// Unloads the feature modules
    override fun onStop() {
        super.onStop()
        unloadKoinModules(aboutModule)
    }
}
