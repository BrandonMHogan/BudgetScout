package com.brandonhogan.budgetscout.repository.dao

import androidx.room.Dao
import androidx.room.Query
import com.brandonhogan.budgetscout.repository.dao.base.BaseDao
import com.brandonhogan.budgetscout.repository.entity.Operation
import com.brandonhogan.budgetscout.repository.entity.Transaction

@Dao
interface OperationDao: BaseDao<Operation> {

    /**
     * Will get operation
     */
    @Query("SELECT * FROM `${Operation.NAME}` WHERE ${Transaction.PROPERTY_ID} IS :id")
    fun get(id: Long): Operation
}