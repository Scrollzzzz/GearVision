package com.scrollz.partrecognizer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.scrollz.partrecognizer.domain.model.Report

@Database(entities = [Report::class], version = 2)
abstract class DataBase: RoomDatabase() {
    abstract fun Dao(): Dao
    companion object {
        const val DATABASE_NAME = "gear_vision"
    }
}
