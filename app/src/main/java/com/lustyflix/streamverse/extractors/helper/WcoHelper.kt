package com.lustyflix.streamverse.extractors.helper

import com.fasterxml.jackson.annotation.JsonProperty
import com.lustyflix.streamverse.AcraApplication.Companion.getKey
import com.lustyflix.streamverse.AcraApplication.Companion.setKey
import com.lustyflix.streamverse.app

class WcoHelper {
    companion object {
        private const val BACKUP_KEY_DATA = "github_keys_backup"

        data class ExternalKeys(
            @JsonProperty("wco_key")
            val wcoKey: String? = null,
            @JsonProperty("wco_cipher_key")
            val wcocipher: String? = null
        )

        data class NewExternalKeys(
            @JsonProperty("cipherKey")
            val cipherkey: String? = null,
            @JsonProperty("encryptKey")
            val encryptKey: String? = null,
            @JsonProperty("mainKey")
            val mainKey: String? = null,
        )

        private var keys: ExternalKeys? = null
        private var newKeys: NewExternalKeys? = null
        private suspend fun getKeys() {
            keys = keys
                ?: app.get("https://raw.githubusercontent.com/reduplicated/Streamverse/master/docs/keys.json")
                    .parsedSafe<ExternalKeys>()?.also { setKey(BACKUP_KEY_DATA, it) } ?: getKey(
                    BACKUP_KEY_DATA
                )
        }

        suspend fun getWcoKey(): ExternalKeys? {
            getKeys()
            return keys
        }

        private suspend fun getNewKeys() {
            newKeys = newKeys
                ?: app.get("https://raw.githubusercontent.com/chekaslowakiya/BruhFlow/main/keys.json")
                    .parsedSafe<NewExternalKeys>()?.also { setKey(BACKUP_KEY_DATA, it) } ?: getKey(
                    BACKUP_KEY_DATA
                )
        }

        suspend fun getNewWcoKey(): NewExternalKeys? {
            getNewKeys()
            return newKeys
        }
    }
}