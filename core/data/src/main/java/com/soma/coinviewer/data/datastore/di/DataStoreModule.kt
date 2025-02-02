package com.soma.coinviewer.data.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.soma.coinviewer.data.datastore.datasource.I18NDataSource
import com.soma.coinviewer.data.datastore.datasource.LocalExchangeRateDataSource
import com.soma.coinviewer.data.datastore.datasource.LocalSettingDataSource
import com.soma.coinviewer.i18n.I18NHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

internal val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    @Singleton
    fun provideLocalSettingDataSource(dataStore: DataStore<Preferences>): LocalSettingDataSource {
        return LocalSettingDataSource(dataStore)
    }

    @Provides
    @Singleton
    fun provideLocalExchangeRateDataSource(dataStore: DataStore<Preferences>): LocalExchangeRateDataSource {
        return LocalExchangeRateDataSource(dataStore)
    }

    @Provides
    @Singleton
    fun provideI18NDataSource(dataStore: DataStore<Preferences>): I18NHelper {
        return I18NDataSource(dataStore)
    }
}
