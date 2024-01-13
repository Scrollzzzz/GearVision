package com.scrollz.partrecognizer.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.scrollz.partrecognizer.domain.repository.PreferencesRepository
import com.scrollz.partrecognizer.domain.model.BaseUrl
import com.scrollz.partrecognizer.domain.model.Theme
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PreferencesRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): PreferencesRepository {
    private object PreferencesKeys {
        val URL_DOMAIN = stringPreferencesKey("url_domain")
        val URL_PORT = stringPreferencesKey("url_port")
        val THEME = intPreferencesKey("theme")
    }

    override val theme: Flow<Theme> = dataStore.data
        .catch { Theme.System }
        .map { preferences ->
            when (preferences[PreferencesKeys.THEME]) {
                Theme.Dark.value -> Theme.Dark
                Theme.Light.value -> Theme.Light
                else -> Theme.System
            }
        }
        .flowOn(dispatcher)

    override val baseUrl: Flow<BaseUrl> = dataStore.data
        .catch { BaseUrl() }
        .map { preferences ->
            BaseUrl(
                domain = preferences[PreferencesKeys.URL_DOMAIN] ?: "",
                port = preferences[PreferencesKeys.URL_PORT] ?: ""
            )
        }
        .flowOn(dispatcher)

    override val baseUrlRaw: BaseUrl get() = runBlocking(dispatcher) {
        dataStore.data.first().let { preferences ->
            BaseUrl(
                domain = preferences[PreferencesKeys.URL_DOMAIN] ?: "",
                port = preferences[PreferencesKeys.URL_PORT] ?: ""
            )
        }
    }

    override suspend fun updateTheme(theme: Theme): Result<Unit> = runCatching {
        withContext(dispatcher) {
            dataStore.edit { preferences ->
                preferences[PreferencesKeys.THEME] = theme.value
            }
        }
    }

    override suspend fun updateBaseUrl(domain: String, port: String): Result<Unit> = runCatching {
        withContext(dispatcher) {
            dataStore.edit { preferences ->
                preferences[PreferencesKeys.URL_DOMAIN] = domain
                preferences[PreferencesKeys.URL_PORT] = port
            }
        }
    }

}
