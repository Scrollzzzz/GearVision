package com.scrollz.partrecognizer.domain.use_cases

import com.scrollz.partrecognizer.domain.repository.PreferencesRepository
import javax.inject.Inject

class SaveBaseUrlUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) {
    suspend operator fun invoke(domain: String, port: String): Result<Unit> {
        return preferencesRepository.updateBaseUrl(domain, port)
    }
}
