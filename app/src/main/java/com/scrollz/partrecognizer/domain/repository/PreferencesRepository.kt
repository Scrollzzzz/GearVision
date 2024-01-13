package com.scrollz.partrecognizer.domain.repository

import com.scrollz.partrecognizer.domain.model.BaseUrl
import com.scrollz.partrecognizer.domain.model.Theme
import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {

    val theme: Flow<Theme>
    val baseUrl: Flow<BaseUrl>
    val baseUrlRaw: BaseUrl

    suspend fun updateTheme(theme: Theme): Result<Unit>

    suspend fun updateBaseUrl(domain: String, port: String): Result<Unit>

    companion object {
        const val PREFERENCES_NAME = "gear_vision_preferences"
    }

}
