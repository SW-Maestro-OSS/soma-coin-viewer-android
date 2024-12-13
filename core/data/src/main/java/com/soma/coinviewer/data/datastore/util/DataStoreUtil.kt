package com.soma.coinviewer.data.datastore.util

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal suspend fun <T> DataStore<Preferences>.savePreference(
    key: Preferences.Key<String>,
    value: T,
    transform: (T) -> String
) {
    edit { preferences ->
        preferences[key] = transform(value)
    }
}

internal fun <T> DataStore<Preferences>.getPreference(
    key: Preferences.Key<String>,
    transform: (String?) -> T
): Flow<T> {
    return data.map { preferences ->
        val value = preferences[key]
        transform(value)
    }
}