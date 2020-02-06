package com.brandonhogan.budgetscout.repository.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * @Creator         Brandon Hogan
 * @Date            2019-12-31
 * @File            Envelope
 * @Description     Envelope act as stashes used to give your money a job, and identity. You can
 * specify the amount each envelope has, which can be adjusted as you go
 */

@Parcelize
@Entity(tableName = Envelope.NAME)
data class Envelope (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = PROPERTY_ID) var id: Long = 0,
    // Category name
    @ColumnInfo(name = PROPERTY_NAME) var name: String,
    // Colour associated to the category
    @ColumnInfo(name = PROPERTY_COLOUR) var colour: Int = 0,
    // Total limit for the item
    @ColumnInfo(name = PROPERTY_TOTAL) var total: Double = 0.0,
    // Carryforward allows the budget remainder to carry to the next month
    @ColumnInfo(name = PROPERTY_IS_CARRYFORWARD) var isCarryforward: Boolean = false,
    // Foreign key reference to its group
    @ColumnInfo(name = PROPERTY_GROUP_ID) var groupId: Long,
    // Note for the given envelope
    @ColumnInfo(name = PROPERTY_NOTE) var note: String
): Parcelable
{
    // Ignored property. Current is a calculated value. It needs to be updated each time a
    // transaction happens for the envelope
    @Ignore var current: Double = 0.0

    companion object {
        const val NAME = "Envelope"
        const val PROPERTY_NAME = "name"
        const val PROPERTY_COLOUR = "colour"
        const val PROPERTY_TOTAL = "total"
        const val PROPERTY_CURRENT = "current"
        const val PROPERTY_IS_CARRYFORWARD = "isCarryforward"
        const val PROPERTY_ID = "id"
        const val PROPERTY_GROUP_ID = "groupId"
        const val PROPERTY_NOTE = "note"
    }
}