package com.dinesh.android.java.database.room

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.dinesh.android.R
import java.time.LocalDateTime


@RequiresApi(Build.VERSION_CODES.O)
@Entity(tableName = "table_name")
@TypeConverters(LocalDateTimeConverter::class)
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val notes: String,
    val dateCreated: LocalDateTime,
    val dateModified: LocalDateTime = LocalDateTime.now(),
    val customPosition: Int = 0,
    val isPinned: Boolean = false,
    val color: Int = R.color.white,
    val label: String = "nullLabel"
)
