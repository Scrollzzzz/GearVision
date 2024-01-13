package com.scrollz.partrecognizer.domain.use_cases

import com.scrollz.partrecognizer.domain.repository.ReportRepository
import javax.inject.Inject

class DeleteReportUseCase @Inject constructor(
    private val reportRepository: ReportRepository,
    private val deleteImageUseCase: DeleteImageUseCase
) {
    suspend operator fun invoke(id: Long, imagePath: String? = null) {
        reportRepository.deleteReport(id)
        imagePath?.let { deleteImageUseCase(imagePath) }
    }
}
