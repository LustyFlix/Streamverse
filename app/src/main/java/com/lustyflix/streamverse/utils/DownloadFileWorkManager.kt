package com.lustyflix.streamverse.utils

import android.app.Notification
import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.lustyflix.streamverse.AcraApplication.Companion.removeKey
import com.lustyflix.streamverse.mvvm.logError
import com.lustyflix.streamverse.utils.Coroutines.ioSafe
import com.lustyflix.streamverse.utils.Coroutines.main
import com.lustyflix.streamverse.utils.DataStore.getKey
import com.lustyflix.streamverse.utils.VideoDownloadManager.WORK_KEY_INFO
import com.lustyflix.streamverse.utils.VideoDownloadManager.WORK_KEY_PACKAGE
import com.lustyflix.streamverse.utils.VideoDownloadManager.downloadCheck
import com.lustyflix.streamverse.utils.VideoDownloadManager.downloadEpisode
import com.lustyflix.streamverse.utils.VideoDownloadManager.downloadFromResume
import com.lustyflix.streamverse.utils.VideoDownloadManager.downloadStatusEvent
import com.lustyflix.streamverse.utils.VideoDownloadManager.getDownloadResumePackage
import kotlinx.coroutines.delay

const val DOWNLOAD_CHECK = "DownloadCheck"

class DownloadFileWorkManager(val context: Context, private val workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val key = workerParams.inputData.getString("key")
        try {
            if (key == DOWNLOAD_CHECK) {
                downloadCheck(applicationContext, ::handleNotification)
            } else if (key != null) {
                val info =
                    applicationContext.getKey<VideoDownloadManager.DownloadInfo>(WORK_KEY_INFO, key)
                val pkg =
                    applicationContext.getKey<VideoDownloadManager.DownloadResumePackage>(
                        WORK_KEY_PACKAGE,
                        key
                    )

                if (info != null) {
                    getDownloadResumePackage(applicationContext, info.ep.id)?.let { dpkg ->
                        downloadFromResume(applicationContext, dpkg, ::handleNotification)
                    } ?: run {
                        downloadEpisode(
                            applicationContext,
                            info.source,
                            info.folder,
                            info.ep,
                            info.links,
                            ::handleNotification
                        )
                    }
                } else if (pkg != null) {
                    downloadFromResume(applicationContext, pkg, ::handleNotification)
                }
                removeKeys(key)
            }
            return Result.success()
        } catch (e: Exception) {
            logError(e)
            if (key != null) {
                removeKeys(key)
            }
            return Result.failure()
        }
    }

    private fun removeKeys(key: String) {
        removeKey(WORK_KEY_INFO, key)
        removeKey(WORK_KEY_PACKAGE, key)
    }

    private suspend fun awaitDownload(id: Int) {
        var isDone = false
        val listener = { (localId, localType): Pair<Int, VideoDownloadManager.DownloadType> ->
            if (id == localId) {
                when (localType) {
                    VideoDownloadManager.DownloadType.IsDone, VideoDownloadManager.DownloadType.IsFailed, VideoDownloadManager.DownloadType.IsStopped -> {
                        isDone = true
                    }

                    else -> Unit
                }
            }
        }
        downloadStatusEvent += listener
        while (!isDone) {
            println("AWAITING $id")
            delay(1000)
        }
        downloadStatusEvent -= listener
    }

    private fun handleNotification(id: Int, notification: Notification) {
        main {
            setForegroundAsync(ForegroundInfo(id, notification))
        }
    }
}