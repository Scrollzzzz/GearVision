package com.scrollz.partrecognizer.domain.model

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Immutable
@Entity(tableName = "report")
data class Report(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    val detectedDetails: String,
    val dateTime: String,
    val resultImagePath: String?
)
