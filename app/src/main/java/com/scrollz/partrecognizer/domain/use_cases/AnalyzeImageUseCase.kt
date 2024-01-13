package com.scrollz.partrecognizer.domain.use_cases

import androidx.camera.core.ImageProxy
import com.scrollz.partrecognizer.domain.model.Response
import com.scrollz.partrecognizer.domain.repository.ImageRepository
import com.scrollz.partrecognizer.domain.repository.NetworkRepository
import com.scrollz.partrecognizer.utils.NetworkException
import javax.inject.Inject

class AnalyzeImageUseCase @Inject constructor(
    private val imageRepository: ImageRepository,
    private val networkRepository: NetworkRepository
) {
    suspend operator fun invoke(imageProxy: ImageProxy): Result<Response> = runCatching {
        val base64 = imageRepository.imageProxyToBase64(imageProxy).getOrThrow()
        networkRepository.analyzeImage(base64).getOrElse { e -> throw NetworkException(e.message) }
    }
}
