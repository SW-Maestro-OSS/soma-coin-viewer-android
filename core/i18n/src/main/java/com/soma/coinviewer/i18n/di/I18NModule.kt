package com.soma.coinviewer.i18n.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.soma.coinviewer.i18n.datasource.SelectedI18NDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object I18NModule {

    @Provides
    @Singleton
    fun provideSelectedI18NDataSource(dataStore: DataStore<Preferences>): SelectedI18NDataSource {
        return SelectedI18NDataSource(dataStore)
    }
}