package com.scrollz.partrecognizer.di

import com.scrollz.partrecognizer.domain.repository.ImageRepository
import com.scrollz.partrecognizer.domain.repository.PreferencesRepository
import com.scrollz.partrecognizer.domain.repository.ReportRepository
import com.scrollz.partrecognizer.domain.use_cases.DeleteImageUseCase
import com.scrollz.partrecognizer.domain.use_cases.DeleteReportUseCase
import com.scrollz.partrecognizer.domain.use_cases.DeleteReportsUseCase
import com.scrollz.partrecognizer.domain.use_cases.GetReportUseCase
import com.scrollz.partrecognizer.domain.use_cases.GetReportsUseCase
import com.scrollz.partrecognizer.domain.use_cases.GetSettingsUseCase
import com.scrollz.partrecognizer.domain.use_cases.SaveBaseUrlUseCase
import com.scrollz.partrecognizer.domain.use_cases.SaveReportUseCase
import com.scrollz.partrecognizer.domain.use_cases.SaveImageUseCase
import com.scrollz.partrecognizer.domain.use_cases.SwitchThemeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideSaveImageUseCase(
        imageRepository: ImageRepository
    ): SaveImageUseCase {
        return SaveImageUseCase(imageRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteImageUseCase(
        imageRepository: ImageRepository
    ): DeleteImageUseCase {
        return DeleteImageUseCase(imageRepository)
    }

    @Provides
    @Singleton
    fun provideGetReportsUseCase(
        reportRepository: ReportRepository
    ): GetReportsUseCase {
        return GetReportsUseCase(reportRepository)
    }

    @Provides
    @Singleton
    fun provideGetReportUseCase(
        reportRepository: ReportRepository
    ): GetReportUseCase {
        return GetReportUseCase(reportRepository)
    }

    @Provides
    @Singleton
    fun provideSaveReportUseCase(
        reportRepository: ReportRepository
    ): SaveReportUseCase {
        return SaveReportUseCase(reportRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteReportUseCase(
        reportRepository: ReportRepository,
        deleteImageUseCase: DeleteImageUseCase
    ): DeleteReportUseCase {
        return DeleteReportUseCase(reportRepository, deleteImageUseCase)
    }

    @Provides
    @Singleton
    fun provideDeleteReportsUseCase(
        reportRepository: ReportRepository,
        deleteImageUseCase: DeleteImageUseCase
    ): DeleteReportsUseCase {
        return DeleteReportsUseCase(reportRepository, deleteImageUseCase)
    }

    @Provides
    @Singleton
    fun provideGetSettingsUseCase(
        preferencesRepository: PreferencesRepository
    ): GetSettingsUseCase {
        return GetSettingsUseCase(preferencesRepository)
    }

    @Provides
    @Singleton
    fun provideSwitchThemeUseCase(
        preferencesRepository: PreferencesRepository
    ): SwitchThemeUseCase {
        return SwitchThemeUseCase(preferencesRepository)
    }

    @Provides
    @Singleton
    fun provideSaveBaseUrlUseCase(
        preferencesRepository: PreferencesRepository
    ): SaveBaseUrlUseCase {
        return SaveBaseUrlUseCase(preferencesRepository)
    }

}
