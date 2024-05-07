package com.lustyflix.streamverse.services

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ForegroundInfo
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.lustyflix.streamverse.R
import com.lustyflix.streamverse.utils.AppUtils.createNotificationChannel
import com.lustyflix.streamverse.utils.BackupUtils
import com.lustyflix.streamverse.utils.UIHelper.colorFromAttribute
import java.util.concurrent.TimeUnit

const val BACKUP_CHANNEL_ID = "streamverse.backups"
const val BACKUP_WORK_NAME = "work_backup"
const val BACKUP_CHANNEL_NAME = "Backups"
const val BACKUP_CHANNEL_DESCRIPTION = "Notifications for background backups"
const val BACKUP_NOTIFICATION_ID = 938712898 // Random unique

class BackupWorkManager(val context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {
    companion object {
        fun enqueuePeriodicWork(context: Context?, intervalHours: Long) {
            if (context == null) return

            if (intervalHours == 0L) {
                WorkManager.getInstance(context).cancelUniqueWork(BACKUP_WORK_NAME)
                return
            }

            val constraints = Constraints.Builder()
                .setRequiresStorageNotLow(true)
                .build()

            val periodicSyncDataWork =
                PeriodicWorkRequest.Builder(
                    BackupWorkManager::class.java,
                    intervalHours,
                    TimeUnit.HOURS
                )
                    .addTag(BACKUP_WORK_NAME)
                    .setConstraints(constraints)
                    .build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                BACKUP_WORK_NAME,
                ExistingPeriodicWorkPolicy.UPDATE,
                periodicSyncDataWork
            )

            // Uncomment below for testing

//            val oneTimeBackupWork =
//                OneTimeWorkRequest.Builder(BackupWorkManager::class.java)
//                    .addTag(BACKUP_WORK_NAME)
//                    .setConstraints(constraints)
//                    .build()
//
//            WorkManager.getInstance(context).enqueue(oneTimeBackupWork)
        }
    }

    private val backupNotificationBuilder =
        NotificationCompat.Builder(context, BACKUP_CHANNEL_ID)
            .setColorized(true)
            .setOnlyAlertOnce(true)
            .setSilent(true)
            .setAutoCancel(true)
            .setContentTitle(context.getString(R.string.pref_category_backup))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setColor(context.colorFromAttribute(R.attr.colorPrimary))
            .setSmallIcon(R.drawable.ic_streamverse_monochrome_big)

    override suspend fun doWork(): Result {
        context.createNotificationChannel(
            BACKUP_CHANNEL_ID,
            BACKUP_CHANNEL_NAME,
            BACKUP_CHANNEL_DESCRIPTION
        )

        setForeground(
            ForegroundInfo(
                BACKUP_NOTIFICATION_ID,
                backupNotificationBuilder.build()
            )
        )

        BackupUtils.backup(context)

        return Result.success()
    }
}