package com.brandonhogan.budgetscout.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.brandonhogan.budgetscout.repository.dao.BudgetDao
import com.brandonhogan.budgetscout.repository.entity.Budget

/**
 * @Creator         Brandon Hogan
 * @Date            2019-12-19
 * @File            AppDatabase
 * @Description     Applications main database
 */


const val version = 1

@Database(entities = arrayOf(Budget::class), version = version)
abstract class AppDatabase : RoomDatabase() {

    abstract fun budgetDao(): BudgetDao

}