package com.parthdesai1208.compose.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn

class ManageStateOnTextChangeViewModel : ViewModel() {

    var userName by mutableStateOf("")
        private set

    fun updateUserName(userName: String) {
        this.userName = userName
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val isUserNameHasError: StateFlow<Boolean> =
        snapshotFlow { userName }
            .mapLatest { isUserNameAvailable(it) }
            .stateIn(
                scope = viewModelScope, started = SharingStarted.WhileSubscribed(5_000),
                initialValue = false
            )

    @Suppress("UNUSED_PARAMETER")
    private suspend fun isUserNameAvailable(it: String): Boolean {
        //here we do nothing with it param
        return if (it.isBlank()) {
            false
        } else {
            delay(3000) //intentionally,assume API call here
            true
        }
    }
}