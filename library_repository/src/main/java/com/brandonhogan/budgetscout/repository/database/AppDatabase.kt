package com.brandonhogan.budgetscout.repository.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
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

// TODO: export schema should not be false when going to production. Need to do proper migrations
@Database(entities = [Budget::class], version = version, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun budgetDao(): BudgetDao

    companion object {
        var TEST_MODE = false

        private val name = "BudgetScoutDB"

        private var db: AppDatabase? = null
        private var dbInstance: BudgetDao? = null

        fun budgetDao(context: Context): BudgetDao {

            if (dbInstance != null) {
                return dbInstance!!
            }

            if (TEST_MODE) {
                db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).allowMainThreadQueries().build()
                dbInstance = db?.budgetDao()
            } else {
                db = Room.databaseBuilder(context, AppDatabase::class.java, name).build()
                dbInstance = db?.budgetDao()
            }

            return dbInstance!!
        }

        private fun close() {
            db?.close()
        }




        private fun getInstance() {

        }
    }
}