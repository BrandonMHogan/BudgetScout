package com.brandonhogan.budgetscout.splash

import android.app.ActivityOptions
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.brandonhogan.budgetscout.R
import com.brandonhogan.budgetscout.actions.Activities
import com.brandonhogan.budgetscout.actions.intentTo

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        Handler().postDelayed({
            startActivity(
                intentTo(Activities.Budget),
                ActivityOptions.makeSceneTransitionAnimation(this@SplashActivity).toBundle()
            )
        }, 1000)
    }


}
