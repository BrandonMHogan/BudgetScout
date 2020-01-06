package com.brandonhogan.budgetscout.repository.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.brandonhogan.budgetscout.repository.BuildConfig
import com.brandonhogan.budgetscout.repository.converter.Converters
import com.brandonhogan.budgetscout.repository.dao.BudgetDao
import com.brandonhogan.budgetscout.repository.dao.EnvelopeDao
import com.brandonhogan.budgetscout.repository.dao.GroupDao
import com.brandonhogan.budgetscout.repository.dao.UserDao
import com.brandonhogan.budgetscout.repository.entity.*
import com.brandonhogan.budgetscout.repository.entity.relations.BudgetWithGroups
import com.brandonhogan.budgetscout.repository.entity.relations.BudgetWithGroupsAndEnvelopes
import com.brandonhogan.budgetscout.repository.entity.relations.GroupWithEnvelopes

/**
 * @Creator         Brandon Hogan
 * @Date            2019-12-19
 * @File            AppDatabase
 * @Description     Applications main database
 */


const val version = 1

// TODO: export schema should not be false when going to production. Need to do proper migrations
@Database(entities = [Budget::class, Envelope::class, Group::class, Transaction::class, User::class], version = version, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    /**
     * The abstracts for the dao interfaces
     */
    abstract fun budgetDao(): BudgetDao
    abstract fun groupDao(): GroupDao
    abstract fun envelopeDao(): EnvelopeDao
    abstract fun userDao(): UserDao

    companion object {
        // Sets if the mode is test or not
        var TEST_MODE = false
        // The database name
        private const val name = "BudgetScoutDB"
        // stored instance of the database
        private var instance: AppDatabase? = null

        /**
         * Creates the Database instance
         */
        private fun setInstance(context: Context) {
            val builder = if (TEST_MODE) {
                Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).allowMainThreadQueries()
            } else {
                Room.databaseBuilder(context, AppDatabase::class.java, name)
            }

            // builds the database and sets the instance
            instance = builder.build()
        }

        /**
         * Closes the database instance
         */
        private fun close() {
            instance?.close()
        }


        fun budgetDao(context: Context): BudgetDao {
            // if instance set, grab the dao
            if (instance != null) {
                return instance!!.budgetDao()
            }
            // set instance
            setInstance(context)
            // return dao
            return instance!!.budgetDao()
        }

        fun groupDao(context: Context): GroupDao {
            // if instance set, grab the dao
            if (instance != null) {
                return instance!!.groupDao()
            }
            // set instance
            setInstance(context)
            // return dao
            return instance!!.groupDao()
        }

        fun envelopeDao(context: Context): EnvelopeDao {
            // if instance set, grab the dao
            if (instance != null) {
                return instance!!.envelopeDao()
            }
            // set instance
            setInstance(context)
            // return dao
            return instance!!.envelopeDao()
        }

        fun userDao(context: Context): UserDao {
            // if instance set, grab the dao
            if (instance != null) {
                return instance!!.userDao()
            }
            // set instance
            setInstance(context)
            // return dao
            return instance!!.userDao()
        }
    }
}