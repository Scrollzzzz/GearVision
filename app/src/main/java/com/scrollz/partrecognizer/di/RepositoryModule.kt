package com.scrollz.partrecognizer.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.scrollz.partrecognizer.data.local.DataBase
import com.scrollz.partrecognizer.data.repository.ImageRepositoryImpl
import com.scrollz.partrecognizer.data.repository.PreferencesRepositoryImpl
import com.scrollz.partrecognizer.data.repository.ReportRepositoryImpl
import com.scrollz.partrecognizer.domain.repository.ImageRepository
import com.scrollz.partrecognizer.domain.repository.PreferencesRepository
import com.scrollz.partrecognizer.domain.repository.ReportRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule{

    @Provides
    @Singleton
    fun providePreferencesRepository(
        dataStore: DataStore<Preferences>
    ): PreferencesRepository {
        return PreferencesRepositoryImpl(dataStore)
    }

    @Provides
    @Singleton
    fun provideImageRepository(
        @ApplicationContext appContext: Context
    ): ImageRepository {
        return ImageRepositoryImpl(appContext)
    }

    @Provides
    @Singleton
    fun provideReportRepository(
        db: DataBase
    ): ReportRepository {
        return ReportRepositoryImpl(db)
    }

}

