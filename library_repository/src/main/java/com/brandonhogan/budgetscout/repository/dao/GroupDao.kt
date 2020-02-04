package com.brandonhogan.budgetscout.repository.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.brandonhogan.budgetscout.repository.dao.base.BaseDao
import com.brandonhogan.budgetscout.repository.entity.Envelope
import com.brandonhogan.budgetscout.repository.entity.Group
import com.brandonhogan.budgetscout.repository.entity.relations.GroupWithEnvelopes

/**
 * @Creator         Brandon Hogan
 * @Date            2019-30-19
 * @File            GroupDao
 * @Description     Data Access Object for the group table
 */

@Dao
interface GroupDao: BaseDao<Group> {

    /**
     * Will get group
     */
    @Query("SELECT * FROM `${Group.NAME}` WHERE ${Group.PROPERTY_ID} IS :id")
    fun get(id: Long): Group


    /**
     * Will retrieve with the nested list of groups, and its nested list of envelopes
     */
    @Transaction
    @Query("SELECT * FROM `${Group.NAME}` WHERE ${Group.PROPERTY_ID} IS :id")
    fun getWithEnvelopes(id: Long): List<GroupWithEnvelopes>
}