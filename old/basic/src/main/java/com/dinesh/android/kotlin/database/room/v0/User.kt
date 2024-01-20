package com.dinesh.android.kotlin.database.room.v0

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user_table")
data class User(
    var username: String,
    var password: String
) {

    @PrimaryKey(autoGenerate = true)
    var id = 0

}