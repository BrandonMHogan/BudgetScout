package com.brandonhogan.budgetscout.repository.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.brandonhogan.budgetscout.repository.entity.Budget

/**
 * @Creator         Brandon Hogan
 * @Date            2019-12-19
 * @File            BudgetDao
 * @Description     Data Access Object for the budget table
 */

@Dao
interface BudgetDao: BaseDao<Budget> {

    @Query("SELECT * FROM ${Budget.NAME}")
    fun getAll(): List<Budget>

    @Query("SELECT * FROM ${Budget.NAME} WHERE ${Budget.PROPERTY_ID} IS :id")
    fun findById(id: Long): Budget
}