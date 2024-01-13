package com.scrollz.partrecognizer.domain.use_cases

import com.scrollz.partrecognizer.domain.repository.ImageRepository
import javax.inject.Inject

class DeleteImageUseCase @Inject constructor(
    private val imageRepository: ImageRepository
) {
    suspend operator fun invoke(imagePath: String): Result<Boolean> {
        return imageRepository.deleteImage(imagePath)
    }
}
