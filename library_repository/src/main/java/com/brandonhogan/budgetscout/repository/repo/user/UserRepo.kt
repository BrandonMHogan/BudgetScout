package com.brandonhogan.budgetscout.repository.repo.user

import androidx.annotation.WorkerThread
import com.brandonhogan.budgetscout.repository.entity.User

@WorkerThread
interface UserRepo {
    suspend fun get(): User
    suspend fun insert(user: User): Long
}