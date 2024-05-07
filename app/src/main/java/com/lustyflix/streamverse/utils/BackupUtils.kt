package com.lustyflix.streamverse.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.WorkerThread
import androidx.fragment.app.FragmentActivity
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.module.kotlin.readValue
import com.lustyflix.streamverse.AcraApplication.Companion.getActivity
import com.lustyflix.streamverse.CommonActivity.showToast
import com.lustyflix.streamverse.R
import com.lustyflix.streamverse.mvvm.logError
import com.lustyflix.streamverse.plugins.PLUGINS_KEY
import com.lustyflix.streamverse.plugins.PLUGINS_KEY_LOCAL
import com.lustyflix.streamverse.syncproviders.providers.AniListApi.Companion.ANILIST_CACHED_LIST
import com.lustyflix.streamverse.syncproviders.providers.AniListApi.Companion.ANILIST_TOKEN_KEY
import com.lustyflix.streamverse.syncproviders.providers.AniListApi.Companion.ANILIST_UNIXTIME_KEY
import com.lustyflix.streamverse.syncproviders.providers.AniListApi.Companion.ANILIST_USER_KEY
import com.lustyflix.streamverse.syncproviders.providers.MALApi.Companion.MAL_CACHED_LIST
import com.lustyflix.streamverse.syncproviders.providers.MALApi.Companion.MAL_REFRESH_TOKEN_KEY
import com.lustyflix.streamverse.syncproviders.providers.MALApi.Companion.MAL_TOKEN_KEY
import com.lustyflix.streamverse.syncproviders.providers.MALApi.Companion.MAL_UNIXTIME_KEY
import com.lustyflix.streamverse.syncproviders.providers.MALApi.Companion.MAL_USER_KEY
import com.lustyflix.streamverse.syncproviders.providers.OpenSubtitlesApi.Companion.OPEN_SUBTITLES_USER_KEY
import com.lustyflix.streamverse.ui.result.txt
import com.lustyflix.streamverse.utils.Coroutines.ioSafe
import com.lustyflix.streamverse.utils.Coroutines.main
import com.lustyflix.streamverse.utils.DataStore.getDefaultSharedPrefs
import com.lustyflix.streamverse.utils.DataStore.getSharedPrefs
import com.lustyflix.streamverse.utils.DataStore.mapper
import com.lustyflix.streamverse.utils.UIHelper.checkWrite
import com.lustyflix.streamverse.utils.UIHelper.requestRW
import com.lustyflix.streamverse.utils.VideoDownloadManager.setupStream
import okhttp3.internal.closeQuietly
import java.io.OutputStream
import java.io.PrintWriter
import java.lang.System.currentTimeMillis
import java.text.SimpleDateFormat
import java.util.Date

object BackupUtils {

    /**
     * No sensitive or breaking data in the backup
     * */
    private val nonTransferableKeys = listOf(
        // When sharing backup we do not want to transfer what is essentially the password
        ANILIST_TOKEN_KEY,
        ANILIST_CACHED_LIST,
        ANILIST_UNIXTIME_KEY,
        ANILIST_USER_KEY,
        MAL_TOKEN_KEY,
        MAL_REFRESH_TOKEN_KEY,
        MAL_CACHED_LIST,
        MAL_UNIXTIME_KEY,
        MAL_USER_KEY,

        // The plugins themselves are not backed up
        PLUGINS_KEY,
        PLUGINS_KEY_LOCAL,

        OPEN_SUBTITLES_USER_KEY,

        DOWNLOAD_EPISODE_CACHE,

        "biometric_key", // can lock down users if backup is shared on a incompatible device
        "nginx_user", // Nginx user key
        "download_path_key" // No access rights after restore data from backup
    )

    /** false if key should not be contained in backup */
    private fun String.isTransferable(): Boolean {
        return !nonTransferableKeys.any { this.contains(it) }
    }

    private var restoreFileSelector: ActivityResultLauncher<Array<String>>? = null

    // Kinda hack, but I couldn't think of a better way
    data class BackupVars(
        @JsonProperty("_Bool") val _Bool: Map<String, Boolean>?,
        @JsonProperty("_Int") val _Int: Map<String, Int>?,
        @JsonProperty("_String") val _String: Map<String, String>?,
        @JsonProperty("_Float") val _Float: Map<String, Float>?,
        @JsonProperty("_Long") val _Long: Map<String, Long>?,
        @JsonProperty("_StringSet") val _StringSet: Map<String, Set<String>?>?,
    )

    data class BackupFile(
        @JsonProperty("datastore") val datastore: BackupVars,
        @JsonProperty("settings") val settings: BackupVars
    )

    @Suppress("UNCHECKED_CAST")
    private fun getBackup(context: Context?): BackupFile? {
        if (context == null) return null

        val allData = context.getSharedPrefs().all.filter { it.key.isTransferable() }
        val allSettings = context.getDefaultSharedPrefs().all.filter { it.key.isTransferable() }

        val allDataSorted = BackupVars(
            allData.filter { it.value is Boolean } as? Map<String, Boolean>,
            allData.filter { it.value is Int } as? Map<String, Int>,
            allData.filter { it.value is String } as? Map<String, String>,
            allData.filter { it.value is Float } as? Map<String, Float>,
            allData.filter { it.value is Long } as? Map<String, Long>,
            allData.filter { it.value as? Set<String> != null } as? Map<String, Set<String>>
        )

        val allSettingsSorted = BackupVars(
            allSettings.filter { it.value is Boolean } as? Map<String, Boolean>,
            allSettings.filter { it.value is Int } as? Map<String, Int>,
            allSettings.filter { it.value is String } as? Map<String, String>,
            allSettings.filter { it.value is Float } as? Map<String, Float>,
            allSettings.filter { it.value is Long } as? Map<String, Long>,
            allSettings.filter { it.value as? Set<String> != null } as? Map<String, Set<String>>
        )

        return BackupFile(
            allDataSorted,
            allSettingsSorted
        )
    }

    @WorkerThread
    fun restore(
        context: Context?,
        backupFile: BackupFile,
        restoreSettings: Boolean,
        restoreDataStore: Boolean
    ) {
        if (context == null) return
        if (restoreSettings) {
            context.restoreMap(backupFile.settings._Bool, true)
            context.restoreMap(backupFile.settings._Int, true)
            context.restoreMap(backupFile.settings._String, true)
            context.restoreMap(backupFile.settings._Float, true)
            context.restoreMap(backupFile.settings._Long, true)
            context.restoreMap(backupFile.settings._StringSet, true)
        }

        if (restoreDataStore) {
            context.restoreMap(backupFile.datastore._Bool)
            context.restoreMap(backupFile.datastore._Int)
            context.restoreMap(backupFile.datastore._String)
            context.restoreMap(backupFile.datastore._Float)
            context.restoreMap(backupFile.datastore._Long)
            context.restoreMap(backupFile.datastore._StringSet)
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun backup(context: Context?) = ioSafe {
        if (context == null) return@ioSafe

        var fileStream: OutputStream? = null
        var printStream: PrintWriter? = null
        try {
            if (!context.checkWrite()) {
                showToast(R.string.backup_failed, Toast.LENGTH_LONG)
                context.getActivity()?.requestRW()
                return@ioSafe
            }

            val date = SimpleDateFormat("yyyy_MM_dd_HH_mm").format(Date(currentTimeMillis()))
            val ext = "txt"
            val displayName = "CS3_Backup_${date}"
            val backupFile = getBackup(context)
            val stream = setupStream(context, displayName, null, ext, false)

            fileStream = stream.openNew()
            printStream = PrintWriter(fileStream)
            printStream.print(mapper.writeValueAsString(backupFile))

            showToast(
                R.string.backup_success,
                Toast.LENGTH_LONG
            )
        } catch (e: Exception) {
            logError(e)
            try {
                showToast(
                    txt(R.string.backup_failed_error_format, e.toString()),
                    Toast.LENGTH_LONG
                )
            } catch (e: Exception) {
                logError(e)
            }
        } finally {
            printStream?.closeQuietly()
            fileStream?.closeQuietly()
        }
    }

    fun FragmentActivity.setUpBackup() {
        try {
            restoreFileSelector =
                registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
                    if (uri == null) return@registerForActivityResult
                    val activity = this
                    ioSafe {
                        try {
                            val input = activity.contentResolver.openInputStream(uri)
                                ?: return@ioSafe

                            val restoredValue =
                                mapper.readValue<BackupFile>(input)

                            restore(
                                activity,
                                restoredValue,
                                restoreSettings = true,
                                restoreDataStore = true
                            )
                            activity.runOnUiThread { activity.recreate() }
                        } catch (e: Exception) {
                            logError(e)
                            main { // smth can fail in .format
                                showToast(
                                    getString(R.string.restore_failed_format).format(e.toString())
                                )
                            }
                        }
                    }
                }
        } catch (e: Exception) {
            logError(e)
        }
    }

    fun FragmentActivity.restorePrompt() {
        runOnUiThread {
            try {
                restoreFileSelector?.launch(
                    arrayOf(
                        "text/plain",
                        "text/str",
                        "text/x-unknown",
                        "application/json",
                        "unknown/unknown",
                        "content/unknown",
                        "application/octet-stream",
                    )
                )
            } catch (e: Exception) {
                showToast(e.message)
                logError(e)
            }
        }
    }

    private fun <T> Context.restoreMap(
        map: Map<String, T>?,
        isEditingAppSettings: Boolean = false
    ) {
        val editor = DataStore.editor(this, isEditingAppSettings)
        map?.forEach {
            if (it.key.isTransferable()) {
                editor.setKeyRaw(it.key, it.value)
            }
        }
        editor.apply()
    }
}