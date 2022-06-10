package com.amosh.marvelcomicsapp.di

import android.content.Context
import androidx.room.Room
import com.amosh.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Module that holds database related classes
 */
@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    /**
     * Provides [AppDatabase] instance
     */
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room
            .databaseBuilder(context, AppDatabase::class.java, "database")
            .build()
    }

    /**
     * Provides [ComicsDAO] instance
     */
    @Provides
    @Singleton
    fun provideComicsDAO(appDatabase: AppDatabase) = appDatabase.comicsDAO()
}