package com.soma.coinviewer.i18n.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.soma.coinviewer.i18n.datasource.SelectedI18NDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "i18n")

@Module
@InstallIn(SingletonComponent::class)
object I18NModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    @Singleton
    fun provideSelectedI18NDataSource(dataStore: DataStore<Preferences>): SelectedI18NDataSource {
        return SelectedI18NDataSource(dataStore)
    }
}