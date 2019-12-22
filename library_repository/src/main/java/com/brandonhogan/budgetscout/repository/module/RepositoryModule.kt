package com.brandonhogan.budgetscout.repository.module

import com.brandonhogan.budgetscout.repository.dao.BudgetDao
import com.brandonhogan.budgetscout.repository.database.AppDatabase
import com.brandonhogan.budgetscout.repository.repo.BudgetRepo
import com.brandonhogan.budgetscout.repository.repo.BudgetRepoImpl
import org.koin.dsl.module

val repositoryModule = module {

    single { AppDatabase.budgetDao(get()) }
    single<BudgetRepo> { BudgetRepoImpl(get()) }
}