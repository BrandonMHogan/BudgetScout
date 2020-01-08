package com.brandonhogan.budgetscout.repository.dao.base

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Update


interface BaseDao<T> {

    @Insert(onConflict = REPLACE)
    suspend fun insert(vararg obj: T): List<Long>

    @Update
    suspend fun update(vararg obj: T)

    @Delete
    suspend fun delete(obj: T)
}