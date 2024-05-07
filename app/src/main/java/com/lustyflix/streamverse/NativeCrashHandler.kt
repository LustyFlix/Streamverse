package com.lustyflix.streamverse

import com.lustyflix.streamverse.MainActivity.Companion.lastError
import com.lustyflix.streamverse.mvvm.logError
import com.lustyflix.streamverse.plugins.PluginManager.checkSafeModeFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object NativeCrashHandler {
    // external fun triggerNativeCrash()
    /*private external fun initNativeCrashHandler()
    private external fun getSignalStatus(): Int

    private fun initSignalPolling() = CoroutineScope(Dispatchers.IO).launch {

        //launch {
        //    delay(10000)
        //    triggerNativeCrash()
        //}

        while (true) {
            delay(10_000)
            val signal = getSignalStatus()
            // Signal is initialized to zero
            if (signal == 0) continue

            // Do not crash in safe mode!
            if (lastError != null) continue
            if (checkSafeModeFile()) continue

            AcraApplication.exceptionHandler?.uncaughtException(
                Thread.currentThread(),
                RuntimeException("Native crash with code: $signal. Try uninstalling extensions.\n")
            )
        }
    }

    fun initCrashHandler() {
        try {
            System.loadLibrary("native-lib")
            initNativeCrashHandler()
        } catch (t: Throwable) {
            // Make debug crash.
            if (BuildConfig.DEBUG) throw t
            logError(t)
            return
        }

        initSignalPolling()
    }*/
}