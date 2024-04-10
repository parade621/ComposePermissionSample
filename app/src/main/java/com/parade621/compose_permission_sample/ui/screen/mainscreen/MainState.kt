package com.parade621.compose_permission_sample.ui.screen.mainscreen

import com.parade621.compose_permission_sample.data.PermissionData

data class MainState(
    val permissionList: List<PermissionData> = emptyList(),
    val requestList: List<String> = emptyList(),
    val checkAll: Boolean = false,
    val showToast: Boolean = false
)