package com.brandonhogan.budgetscout.repository.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @Creator         Brandon Hogan
 * @Date            2019-12-19
 * @File            Budget
 * @Description     {{ foo }}
 */

@Entity

data class Budget (

    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "name") var name: String

)