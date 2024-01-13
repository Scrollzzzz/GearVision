package com.scrollz.partrecognizer.domain.repository

import androidx.paging.PagingData
import com.scrollz.partrecognizer.domain.model.Report
import kotlinx.coroutines.flow.Flow

interface ReportRepository {

    fun getReports(): Flow<PagingData<Report>>

    suspend fun getReport(id: Long): Result<Report>

    suspend fun getImagePaths(ids: Set<Long>): List<String>

    suspend fun insertReport(report: Report): Long

    suspend fun deleteReport(id: Long)

    suspend fun deleteReports(ids: Set<Long>)

}
