package com.brandonhogan.budgetscout.repository.dao

import androidx.room.*
import com.brandonhogan.budgetscout.repository.dao.base.BaseDao
import com.brandonhogan.budgetscout.repository.entity.Budget
import com.brandonhogan.budgetscout.repository.entity.relations.BudgetWithGroups
import com.brandonhogan.budgetscout.repository.entity.relations.BudgetWithGroupsAndEnvelopes

/**
 * @Creator         Brandon Hogan
 * @Date            2019-12-19
 * @File            BudgetDao
 * @Description     Data Access Object for the budget table
 */

@Dao
interface BudgetDao: BaseDao<Budget> {

    @Query("DELETE FROM ${Budget.NAME}")
    fun deleteAll()

    /**
     * Returns all budgets
     */
    @Query("SELECT * FROM ${Budget.NAME}")
    fun getAll(): List<Budget>

    /**
     * Will retrieve just the budget
     */
    @Query("SELECT * FROM ${Budget.NAME} WHERE ${Budget.PROPERTY_ID} IS :id")
    fun get(id: Long): Budget

    /**
     * Will retrieve with its groups
     */
    @Transaction
    @Query("SELECT * FROM ${Budget.NAME} WHERE ${Budget.PROPERTY_ID} IS :id")
    fun getWithGroups(id: Long): BudgetWithGroups

    /**
     * Will retrieve with the nested list of groups, and its nested list of envelopes
     */
    @Transaction
    @Query("SELECT * FROM ${Budget.NAME} WHERE ${Budget.PROPERTY_ID} IS :id")
    fun getWithGroupsAndEnvelopes(id: Long): BudgetWithGroupsAndEnvelopes
}