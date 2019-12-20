package com.brandonhogan.budgetscout.repository.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.brandonhogan.budgetscout.repository.entity.Budget

/**
 * @Creator         Brandon Hogan
 * @Date            2019-12-19
 * @File            BudgetDao
 * @Description     {{ foo }}
 */

@Dao
interface BudgetDao {

    @Query("SELECT * FROM budget")
    fun getAll(): List<Budget>

    @Query("SELECT * FROM budget WHERE id IS :id")
    fun findById(id: Int): LiveData<List<Budget>>

    @Insert
    fun insert(budget: Budget)

    @Insert
    fun insert(vararg budgets: Budget)

    @Delete
    fun delete(todo: Budget)

    @Update
    fun updateTodo(vararg budgets: Budget)
}