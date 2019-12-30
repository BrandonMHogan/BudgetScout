package com.brandonhogan.budgetscout.repository.repo.user

import com.brandonhogan.budgetscout.repository.dao.UserDao
import com.brandonhogan.budgetscout.repository.entity.User
import org.koin.core.KoinComponent

class UserRepoImpl(private val userDao: UserDao): UserRepo, KoinComponent {

    override suspend fun get(): User {
        return userDao.get()
    }

    override suspend fun set(user: User): Long {
        return userDao.insert(user).first()
    }
}