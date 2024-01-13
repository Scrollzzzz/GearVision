package com.scrollz.partrecognizer.domain.use_cases

import com.scrollz.partrecognizer.domain.model.Report
import com.scrollz.partrecognizer.domain.repository.ReportRepository
import javax.inject.Inject

class GetReportUseCase @Inject constructor(
    private val reportRepository: ReportRepository
) {
    suspend operator fun invoke(id: Long): Result<Report> = reportRepository.getReport(id)
}
