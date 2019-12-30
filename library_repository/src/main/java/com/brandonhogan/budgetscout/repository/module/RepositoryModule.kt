package com.brandonhogan.budgetscout.repository.module

import com.brandonhogan.budgetscout.repository.database.AppDatabase
import com.brandonhogan.budgetscout.repository.repo.budget.BudgetRepo
import com.brandonhogan.budgetscout.repository.repo.budget.BudgetRepoImpl
import com.brandonhogan.budgetscout.repository.repo.user.UserRepo
import com.brandonhogan.budgetscout.repository.repo.user.UserRepoImpl
import org.koin.dsl.module

val repositoryModule = module {

    single { AppDatabase.budgetDao(get()) }
    single { AppDatabase.userDao(get()) }

    single<BudgetRepo> { BudgetRepoImpl(get()) }

    single<UserRepo> { UserRepoImpl(get()) }
}