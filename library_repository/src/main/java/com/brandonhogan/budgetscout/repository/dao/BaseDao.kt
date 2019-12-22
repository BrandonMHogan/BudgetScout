package com.brandonhogan.budgetscout.repository.dao

import androidx.annotation.WorkerThread
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Update

interface BaseDao<T> {

    @WorkerThread
    @Insert(onConflict = REPLACE)
    fun insert(vararg obj: T)

    @WorkerThread
    @Update
    fun update(vararg obj: T)

    @WorkerThread
    @Delete
    fun delete(obj: T)
}