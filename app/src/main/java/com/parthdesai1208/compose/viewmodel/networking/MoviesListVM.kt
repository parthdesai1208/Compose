package com.parthdesai1208.compose.viewmodel.networking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parthdesai1208.compose.model.networking.MoviesListRepo
import com.parthdesai1208.compose.model.networking.MoviesListRepoImpl
import com.parthdesai1208.compose.model.networking.MoviesListScreenUI
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MoviesListVM(private val moviesListRepo: MoviesListRepo = MoviesListRepoImpl()) :
    ViewModel() {
    // UI state exposed to the UI
    private val _uiState = MutableStateFlow(MoviesListScreenUI(loading = true))
    val uiState: StateFlow<MoviesListScreenUI> = _uiState

    init {
        getMovies()
    }

    private fun getMovies() {
        viewModelScope.launch {
            delay(3000) //for simulate progress bar
            moviesListRepo.getMovies()
                .catch { ex ->
                    _uiState.value = MoviesListScreenUI(loading = false)
                    _uiState.value = MoviesListScreenUI(error = ex.localizedMessage)
                }
                .collect { emails ->
                    _uiState.value = MoviesListScreenUI(loading = false)
                    _uiState.value = MoviesListScreenUI(movies = emails)
                }
        }
    }
}