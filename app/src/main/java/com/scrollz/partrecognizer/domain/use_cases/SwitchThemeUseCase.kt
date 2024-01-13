package com.scrollz.partrecognizer.domain.use_cases

import com.scrollz.partrecognizer.domain.model.Theme
import com.scrollz.partrecognizer.domain.repository.PreferencesRepository
import javax.inject.Inject

class SwitchThemeUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) {
    suspend operator fun invoke(theme: Theme): Result<Unit> {
        return preferencesRepository.updateTheme(theme)
    }
}
