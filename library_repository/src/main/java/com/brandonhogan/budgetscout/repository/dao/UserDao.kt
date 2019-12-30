package com.brandonhogan.budgetscout.repository.dao

import androidx.room.Dao
import androidx.room.Query
import com.brandonhogan.budgetscout.repository.dao.base.BaseDao
import com.brandonhogan.budgetscout.repository.entity.User

/**
 * @Creator         Brandon Hogan
 * @Date            2019-30-19
 * @File            UserDao
 * @Description     Data Access Object for the user table
 */

@Dao
interface UserDao: BaseDao<User> {

    @Query("SELECT * FROM ${User.NAME} ORDER BY ${User.PROPERTY_ID} ASC LIMIT 1")
    fun get(): User
}