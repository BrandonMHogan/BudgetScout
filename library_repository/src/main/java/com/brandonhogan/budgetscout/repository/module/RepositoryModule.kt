package com.brandonhogan.budgetscout.repository.module

import com.brandonhogan.budgetscout.repository.database.AppDatabase
import com.brandonhogan.budgetscout.repository.repo.budget.BudgetRepo
import com.brandonhogan.budgetscout.repository.repo.budget.BudgetRepoImpl
import com.brandonhogan.budgetscout.repository.repo.envelope.EnvelopeRepo
import com.brandonhogan.budgetscout.repository.repo.envelope.EnvelopeRepoImpl
import com.brandonhogan.budgetscout.repository.repo.group.GroupRepo
import com.brandonhogan.budgetscout.repository.repo.group.GroupRepoImpl
import com.brandonhogan.budgetscout.repository.repo.transaction.TransactionRepo
import com.brandonhogan.budgetscout.repository.repo.transaction.TransactionRepoImpl
import com.brandonhogan.budgetscout.repository.repo.user.UserRepo
import com.brandonhogan.budgetscout.repository.repo.user.UserRepoImpl
import com.brandonhogan.budgetscout.repository.testing.BudgetCreator
import org.koin.dsl.module

val repositoryModule = module {

    single { AppDatabase.groupDao(get()) }
    single { AppDatabase.envelopeDao(get()) }
    single { AppDatabase.budgetDao(get()) }
    single { AppDatabase.userDao(get()) }

    single<BudgetRepo> { BudgetRepoImpl(get(), get(), get(), get()) }
    single<EnvelopeRepo> { EnvelopeRepoImpl(get(), get()) }
    single<GroupRepo> { GroupRepoImpl(get(), get()) }
    single<TransactionRepo> { TransactionRepoImpl(get(), get()) }

    single<UserRepo> { UserRepoImpl(get()) }


    // Creates the Budget Creator singleton for quick data setup for debugging
    single { BudgetCreator(get(), get(), get()) }
}