package org.rsschool.android2021task5.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.rsschool.android2021task5.api.ApiService
import org.rsschool.android2021task5.repository.RemoteRepository
import org.rsschool.android2021task5.repository.Repository
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideRemoteService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideRepository(remoteRepository: RemoteRepository): Repository = remoteRepository
}
