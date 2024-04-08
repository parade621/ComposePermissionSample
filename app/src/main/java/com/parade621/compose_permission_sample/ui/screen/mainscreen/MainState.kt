package com.parade621.compose_permission_sample.ui.screen.mainscreen

import com.parade621.compose_permission_sample.data.PermissionItem

data class MainState(
    val permissionList: List<PermissionItem> = emptyList(),
    val checkAll: Boolean = false
)