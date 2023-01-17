package com.ltdung.alebeer.di

import com.ltdung.alebeer.domain.repository.BeerRepository
import com.ltdung.alebeer.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    @ViewModelScoped
    fun provideLoadListBeerUseCase(beerRepository: BeerRepository): LoadListBeerUseCase =
        LoadListBeerUseCase(beerRepository)

    @Provides
    @ViewModelScoped
    fun provideSaveFavoriteBeerUseCase(beerRepository: BeerRepository): SaveFavoriteBeerUseCase =
        SaveFavoriteBeerUseCase(beerRepository)

    @Provides
    @ViewModelScoped
    fun provideUpdateFavoriteBeerUseCase(beerRepository: BeerRepository): UpdateFavoriteBeerUseCase =
        UpdateFavoriteBeerUseCase(beerRepository)

    @Provides
    @ViewModelScoped
    fun provideDeleteFavoriteBeerUseCase(beerRepository: BeerRepository): DeleteFavoriteBeerUseCase =
        DeleteFavoriteBeerUseCase(beerRepository)

    @Provides
    @ViewModelScoped
    fun provideLoadListFavoriteBeerUseCase(beerRepository: BeerRepository): LoadListFavoriteBeerUseCase =
        LoadListFavoriteBeerUseCase(beerRepository)
}
