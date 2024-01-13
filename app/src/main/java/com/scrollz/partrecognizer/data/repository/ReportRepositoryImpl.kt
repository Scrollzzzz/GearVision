package com.scrollz.partrecognizer.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.scrollz.partrecognizer.data.local.DataBase
import com.scrollz.partrecognizer.domain.model.Report
import com.scrollz.partrecognizer.domain.repository.ReportRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(
    db: DataBase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): ReportRepository {

    private val dao = db.Dao()

    override fun getReports(): Flow<PagingData<Report>> = Pager(
        PagingConfig(pageSize = 10, prefetchDistance = 20)
    ) { dao.getReports() }.flow

    override suspend fun getReport(id: Long): Result<Report> = withContext(dispatcher) {
        val report = dao.getReport(id)
        if (report != null) Result.success(report) else Result.failure(NoSuchElementException())
    }

    override suspend fun getImagePaths(ids: Set<Long>): List<String> = withContext(dispatcher) {
        dao.getImagePaths(ids)
    }

    override suspend fun insertReport(report: Report): Long = withContext(dispatcher) {
        dao.insertReport(report)
    }

    override suspend fun deleteReport(id: Long) = withContext(dispatcher) {
        dao.deleteReport(id)
    }

    override suspend fun deleteReports(ids: Set<Long>) = withContext(dispatcher) {
        dao.deleteReports(ids)
    }

}
