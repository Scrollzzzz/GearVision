package com.scrollz.partrecognizer.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.scrollz.partrecognizer.domain.model.Report

@Dao
interface Dao {

    @Query("SELECT * FROM report")
    fun getReports(): PagingSource<Int, Report>

    @Query("SELECT * FROM report WHERE id = :id")
    suspend fun getReport(id: Long): Report?

    @Query("SELECT resultImagePath FROM report WHERE id in (:ids)")
    suspend fun getImagePaths(ids: Set<Long>): List<String>

    @Insert(entity = Report::class)
    suspend fun insertReport(report: Report): Long

    @Query("DELETE FROM report WHERE id = :id")
    suspend fun deleteReport(id: Long)

    @Query("DELETE FROM report WHERE id in (:ids)")
    suspend fun deleteReports(ids: Set<Long>)

}
