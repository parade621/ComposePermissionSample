package com.parade621.compose_permission_sample.utils

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import com.parade621.compose_permission_sample.PermissionSample

fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}

fun checkPermissionGranted(permission: String): Boolean {
    return (PermissionSample.context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED)
}