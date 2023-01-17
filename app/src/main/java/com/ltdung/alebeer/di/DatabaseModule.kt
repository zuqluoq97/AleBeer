package com.ltdung.alebeer.di

import android.content.Context
import androidx.room.Room
import com.ltdung.alebeer.data.local.BeerDatabase
import com.ltdung.alebeer.data.local.dao.BeerDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): BeerDatabase {
        return Room.databaseBuilder(appContext, BeerDatabase::class.java, "beer_database").build()
    }

    @Provides
    @Singleton
    fun provideChannelDao(beerDatabase: BeerDatabase): BeerDao {
        return beerDatabase.beerDao()
    }
}
