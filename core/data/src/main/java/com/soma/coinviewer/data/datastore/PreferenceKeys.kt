package com.soma.coinviewer.data.datastore

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.soma.coinviewer.domain.datastore.PreferenceKeys

fun PreferenceKeys.toPreferencesKey(): Preferences.Key<String> {
    return stringPreferencesKey(this.key)
}