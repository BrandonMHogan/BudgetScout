package com.brandonhogan.budgetscout.about.modules

import com.brandonhogan.budgetscout.about.ui.AboutViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val aboutModule = module {

    // MyViewModel ViewModel
    viewModel { AboutViewModel() }
}