package com.am.genreclassifier.helper

import android.Manifest
import android.app.Activity
import android.os.Process

class PermissionManager(private val activity: Activity) {

    private val requestCode: Int = 111


    fun requestPermission(): Int {
        val arrayOf = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        activity.requestPermissions(
            arrayOf, requestCode
        )
        return activity.checkPermission(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Process.myPid(),
            Process.myUid()
        )
    }
}