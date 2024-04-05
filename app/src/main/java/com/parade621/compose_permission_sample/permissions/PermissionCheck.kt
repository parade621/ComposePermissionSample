package com.parade621.compose_permission_sample.permissions

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.parade621.compose_permission_sample.openAppSettings
import com.parade621.compose_permission_sample.ui.components.dialog.PermissionDialog

@Composable
fun PermissionCheck(
    permissions: Array<String>,
    onEvent: () -> Unit = {}
) {
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    var onResume: Boolean by remember { mutableStateOf(true) }
    var deniedPermission: Array<String> by remember { mutableStateOf(emptyArray()) }
    var recheckPermissions: Boolean by remember { mutableStateOf(true) }

    val multiplePermissionRequestLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { params ->
            if (params.values.contains(false)) {
                deniedPermission = params.filterValues { !it }.keys.toTypedArray()
            } else {
                onEvent()
            }
        }
    )

    DisposableEffect(Unit) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    onResume = true
                }

                else -> Unit
            }
        }
        lifecycle.addObserver(observer)

        onDispose {
            lifecycle.removeObserver(observer)
        }
    }

    fun updatePermissionsList(): Array<String> {
        val checkedList = ArrayList<String>()
        permissions.forEach { permission ->
            if (context.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                checkedList.add(permission)
            }
        }
        return checkedList.toTypedArray()
    }

    LaunchedEffect(onResume, recheckPermissions) {
        if (onResume && recheckPermissions) {
            recheckPermissions = false
            val newList = updatePermissionsList()
            if (newList.isEmpty()) {
                onEvent()
            } else {
                multiplePermissionRequestLauncher.launch(newList)
            }
        }
    }

    fun setDeniedPermissionText(): String {
        val deniedPermissionNames = mutableListOf<String>()
        deniedPermission.forEach { item ->
            when (item) {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION -> {
                    deniedPermissionNames.add("Location")
                }

                Manifest.permission.READ_MEDIA_IMAGES -> {
                    deniedPermissionNames.add("Read Images")
                }

                Manifest.permission.POST_NOTIFICATIONS -> {
                    deniedPermissionNames.add("Notification")
                }

                Manifest.permission.CAMERA -> {
                    deniedPermissionNames.add("Camera")
                }

                Manifest.permission.READ_PHONE_NUMBERS -> {
                    deniedPermissionNames.add("Phone State")
                }

                Manifest.permission.READ_EXTERNAL_STORAGE -> {
                    deniedPermissionNames.add("Read External Store")
                }

                Manifest.permission.WRITE_EXTERNAL_STORAGE -> {
                    deniedPermissionNames.add("Write External Store")
                }
            }
        }
        return deniedPermissionNames.joinToString(", ")
    }

    if (deniedPermission.isNotEmpty()) {
        PermissionDialog(
            permissionTextProvider = RequiredPermissionTextProvider(setDeniedPermissionText()),
            isPermanentlyDeclined = !ActivityCompat.shouldShowRequestPermissionRationale(
                (context as Activity),
                deniedPermission[0]
            ),
            onDismiss = {
                deniedPermission = emptyArray()
                recheckPermissions = true
            },
            onOkClick = {
                deniedPermission = emptyArray()
                recheckPermissions = true
            },
            onGotoAppSettingClick = {
                onResume = false
                context.openAppSettings()
            }
        )
    }
}
