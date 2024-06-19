package com.example.instagram.common.data


import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.instagram.common.data.local.UserPreferences
import com.example.instagram.common.data.local.UserSettings

internal class IOSUserPreferences (
    private val dataStore: DataStore<Preferences>
): UserPreferences {
    override suspend fun getUserData(): UserSettings {
        TODO("Not yet implemented")
    }

    override suspend fun setUserData(userSettings: UserSettings) {
        TODO("Not yet implemented")
    }

}


internal fun createDatastore(): DataStore<Preferences>? {
   return null
}