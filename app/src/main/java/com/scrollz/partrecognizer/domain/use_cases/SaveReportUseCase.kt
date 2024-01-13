package com.scrollz.partrecognizer.domain.use_cases

import com.scrollz.partrecognizer.domain.model.Report
import com.scrollz.partrecognizer.domain.repository.ReportRepository
import javax.inject.Inject

class SaveReportUseCase @Inject constructor(
    private val reportRepository: ReportRepository
) {
    suspend operator fun invoke(report: Report): Long = reportRepository.insertReport(report)
}
