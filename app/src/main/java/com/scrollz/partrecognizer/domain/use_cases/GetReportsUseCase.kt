package com.scrollz.partrecognizer.domain.use_cases

import androidx.paging.PagingData
import com.scrollz.partrecognizer.domain.model.Report
import com.scrollz.partrecognizer.domain.repository.ReportRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetReportsUseCase @Inject constructor(
    private val reportRepository: ReportRepository
) {
    operator fun invoke(): Flow<PagingData<Report>> = reportRepository.getReports()
}
