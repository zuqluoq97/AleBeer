package com.ltdung.alebeer.di

import com.ltdung.alebeer.data.local.dao.BeerDao
import com.ltdung.alebeer.data.remote.BeerService
import com.ltdung.alebeer.data.repository.BeerRepositoryImpl
import com.ltdung.alebeer.domain.repository.BeerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
class DataModule {

    @Provides
    @ActivityRetainedScoped
    fun provideAccountRepository(beerService: BeerService, beerDao: BeerDao): BeerRepository =
        BeerRepositoryImpl(beerService, beerDao)
}
