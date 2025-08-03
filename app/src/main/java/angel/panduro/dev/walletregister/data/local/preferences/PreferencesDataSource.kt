package angel.panduro.dev.walletregister.data.local.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import angel.panduro.dev.walletregister.presentation.ui.utils.companions.EMPTY_ID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferencesDataSource(
    private val dataStore: DataStore<Preferences>
){
    companion object{
        val KEY_ID_CARD = longPreferencesKey("key_id_card")
    }

    fun getIdCard(): Flow<Long> {
        return dataStore.data.map { preferences ->
            preferences[KEY_ID_CARD] ?: Long.EMPTY_ID
        }
    }

    suspend fun saveIdCard(idCard: Long){
        dataStore.edit { preferences ->
            preferences[KEY_ID_CARD] = idCard
        }
    }
}