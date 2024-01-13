package com.scrollz.partrecognizer.di

import com.scrollz.partrecognizer.data.remote.Api
import com.scrollz.partrecognizer.data.repository.NetworkRepositoryImpl
import com.scrollz.partrecognizer.domain.repository.ImageRepository
import com.scrollz.partrecognizer.domain.repository.NetworkRepository
import com.scrollz.partrecognizer.domain.repository.PreferencesRepository
import com.scrollz.partrecognizer.domain.use_cases.AnalyzeImageUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(ViewModelComponent::class)
object ScannerModule {

    @Provides
    @ViewModelScoped
    fun provideRetrofit(
        preferencesRepository: PreferencesRepository,
        okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(preferencesRepository.baseUrlRaw.url)
            .client(okHttpClient)
            .addConverterFactory(moshiConverterFactory)
            .build()
    }

    @Provides
    @ViewModelScoped
    fun provideApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }

    @Provides
    @ViewModelScoped
    fun provideNetworkRepository(
        api: Api
    ): NetworkRepository {
        return NetworkRepositoryImpl(api)
    }

    @Provides
    @ViewModelScoped
    fun provideAnalyzeImageUseCase(
        imageRepository: ImageRepository,
        networkRepository: NetworkRepository
    ): AnalyzeImageUseCase {
        return AnalyzeImageUseCase(imageRepository, networkRepository)
    }

}
