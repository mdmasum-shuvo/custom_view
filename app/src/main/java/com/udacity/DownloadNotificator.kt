package com.udacity

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.udacity.ext.createDownloadStatusChannel
import com.udacity.ext.getNotificationManager
import com.udacity.ext.sendDownloadCompletedNotification

class DownloadNotificator(private val context: Context, private val lifecycle: Lifecycle) :
    LifecycleObserver {

    init {
        lifecycle.addObserver(this).also {
        }
    }

    fun notify(
        fileName: String,
        downloadStatus: DownloadStatus
    ) {
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
            Toast.makeText(
                context,
                context.getString(R.string.download_completed),
                Toast.LENGTH_SHORT
            ).show();
        }
        with(context.applicationContext) {
            getNotificationManager().run {
                createDownloadStatusChannel(applicationContext)
                sendDownloadCompletedNotification(
                    fileName,
                    downloadStatus,
                    applicationContext
                )
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun unregisterObserver() = lifecycle.removeObserver(this)
        .also {  }
}