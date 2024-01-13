package com.scrollz.partrecognizer.domain.use_cases

import com.scrollz.partrecognizer.domain.repository.ImageRepository
import javax.inject.Inject

class SaveImageUseCase @Inject constructor(
    private val imageRepository: ImageRepository
) {
    suspend operator fun invoke(imageBase64: String, name: String): Result<String> {
        return imageRepository.saveImage(imageBase64, name)
    }
}
