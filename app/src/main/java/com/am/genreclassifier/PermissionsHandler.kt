package com.am.genreclassifier

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build

object PermissionsHandler {

    fun init(activity: Activity){
        // these may not necessarily all be required for your use case (e.g. if you're not recording
        // from device audio inputs or reading/writing files) but are here for self-documentation
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val PERMISSIONS = arrayOf(
                Manifest.permission.RECORD_AUDIO,  // RECORD_AUDIO must be granted prior to engine.start()
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            // Check if we have all the necessary permissions, if not: prompt user
            val permission: Int = activity.checkSelfPermission(Manifest.permission.RECORD_AUDIO)
            if (permission != PackageManager.PERMISSION_GRANTED) activity.requestPermissions(
                PERMISSIONS,
                111
            )
        }
    }
}
