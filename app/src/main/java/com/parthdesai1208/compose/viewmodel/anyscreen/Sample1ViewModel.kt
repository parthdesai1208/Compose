package com.parthdesai1208.compose.viewmodel.anyscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parthdesai1208.compose.model.anyscreen.EmailsRepository
import com.parthdesai1208.compose.model.anyscreen.EmailsRepositoryImpl
import com.parthdesai1208.compose.model.anyscreen.ReplyHomeUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class Sample1ViewModel(private val emailsRepository: EmailsRepository = EmailsRepositoryImpl()) :
    ViewModel() {
    // UI state exposed to the UI
    private val _uiState = MutableStateFlow(ReplyHomeUIState(loading = true))
    val uiState: StateFlow<ReplyHomeUIState> = _uiState

    init {
        observeEmails()
    }

    private fun observeEmails() {
        viewModelScope.launch {
            emailsRepository.getAllEmails()
                .catch { ex ->
                    _uiState.value = ReplyHomeUIState(error = ex.message)
                }
                .collect { emails ->
                    _uiState.value = ReplyHomeUIState(emails = emails)
                }
        }
    }

}