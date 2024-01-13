package com.scrollz.partrecognizer.domain.use_cases

import com.scrollz.partrecognizer.domain.repository.ReportRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class DeleteReportsUseCase @Inject constructor(
    private val reportRepository: ReportRepository,
    private val deleteImageUseCase: DeleteImageUseCase
) {
    suspend operator fun invoke(ids: Set<Long>) = coroutineScope {
        val imagePaths = reportRepository.getImagePaths(ids)
        launch { reportRepository.deleteReports(ids) }
        imagePaths.forEach { imagePath -> launch { deleteImageUseCase(imagePath) } }
    }
}
