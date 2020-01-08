package com.brandonhogan.budgetscout.repository.module

import com.brandonhogan.budgetscout.repository.database.AppDatabase
import com.brandonhogan.budgetscout.repository.repo.budget.BudgetRepo
import com.brandonhogan.budgetscout.repository.repo.budget.BudgetRepoImpl
import com.brandonhogan.budgetscout.repository.repo.user.UserRepo
import com.brandonhogan.budgetscout.repository.repo.user.UserRepoImpl
import com.brandonhogan.budgetscout.repository.testing.BudgetCreator
import org.koin.dsl.module

val repositoryModule = module {

    single { AppDatabase.groupDao(get()) }
    single { AppDatabase.envelopeDao(get()) }
    single { AppDatabase.budgetDao(get()) }
    single { AppDatabase.userDao(get()) }

    single<BudgetRepo> { BudgetRepoImpl(get(), get(), get()) }

    single<UserRepo> { UserRepoImpl(get()) }


    // Creates the Budget Creator singleton for quick data setup for debugging
    single { BudgetCreator(get()) }
}