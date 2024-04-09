package com.parade621.compose_permission_sample.ui.screen.mainscreen

import androidx.lifecycle.ViewModel
import com.parade621.compose_permission_sample.data.PermissionData
import com.parade621.compose_permission_sample.permissions.PermissionCheckList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val _state: MutableStateFlow<MainState> = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.OnCheckAllClicked -> {
                _state.update { it.copy(checkAll = !_state.value.checkAll) }
            }

            is MainEvent.OnCheckPermissionClicked -> {
                val permissionList = _state.value.permissionList.toMutableList()
                permissionList[event.index] =
                    permissionList[event.index].copy(isChecked = !permissionList[event.index].isChecked)
                Timber.e("permission: ${permissionList[event.index].permissionText} isChecked: ${permissionList[event.index].isChecked}")
                _state.update { it.copy(permissionList = permissionList) }
            }
        }
    }

    init {
        val newList = PermissionCheckList.permissionToRequest.map {
            val str = it.replace("android.permission.", "")
            PermissionData(str, false)
        }
        _state.update { it.copy(permissionList = newList) }
    }
}