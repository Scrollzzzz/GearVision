package com.scrollz.partrecognizer.domain.use_cases

import com.scrollz.partrecognizer.domain.model.Settings
import com.scrollz.partrecognizer.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetSettingsUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) {
    operator fun invoke(): Flow<Settings> = combine(
        preferencesRepository.theme,
        preferencesRepository.baseUrl
    ) { theme, baseUrl ->
        Settings(theme, baseUrl)
    }
}
