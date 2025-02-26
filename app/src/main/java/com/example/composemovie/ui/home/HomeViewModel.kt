package com.example.composemovie.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composemovie.movie.domain.models.Movie
import com.example.composemovie.movie.domain.repository.MovieRepository
import com.example.composemovie.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository,
) : ViewModel() {
    private val _homeState = MutableStateFlow(HomeState())
    val homeState = _homeState.asStateFlow()

    init {
        fetchDiscoverMovie()
    }
    init {
        fetchTrendingMovie()
    }

    private fun fetchDiscoverMovie() = viewModelScope.launch {
        repository.fetchDiscoverMovie().collect { response ->
            when (response) {
                is Response.Error -> {
                    _homeState.update {
                        it.copy(isLoading = false, error = response.error?.message)
                    }
                }

                is Response.Success -> {
                    _homeState.update {
                        it.copy(isLoading = false, error = null, discoverMovies = response.data)
                    }
                }

                is Response.Loading -> {
                    _homeState.update {
                        it.copy(isLoading = true, error = null)
                    }
                }
            }
        }
    }

    private fun fetchTrendingMovie() = viewModelScope.launch {
        repository.fetchTrendingMovie().collect { response ->
            when (response) {
                is Response.Error -> {
                    _homeState.update {
                        it.copy(isLoading = false, error = response.error?.message)
                    }
                }

                is Response.Success -> {
                    _homeState.update {
                        it.copy(isLoading = false, error = null, trendingMovies = response.data)
                    }
                }

                is Response.Loading -> {
                    _homeState.update {
                        it.copy(isLoading = true, error = null)
                    }
                }
            }
        }
    }

}

data class HomeState(
    val discoverMovies: List<Movie> = emptyList(),
    val trendingMovies: List<Movie> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false
)