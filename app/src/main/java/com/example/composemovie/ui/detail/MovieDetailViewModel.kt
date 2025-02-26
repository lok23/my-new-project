package com.example.composemovie.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composemovie.movie.domain.models.Movie
import com.example.composemovie.movie_detail.domain.models.MovieDetail
import com.example.composemovie.movie_detail.domain.repository.MovieDetailRepository
import com.example.composemovie.utils.Constants
import com.example.composemovie.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: MovieDetailRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _detailState = MutableStateFlow(DetailState())
    val detailState = _detailState.asStateFlow()

    val id: Int = savedStateHandle.get<Int>(Constants.MOVIE_ID) ?: -1

    init {
        fetchMovieDetailById()
    }

    private fun fetchMovieDetailById() = viewModelScope.launch {
        if (id == -1) {
            _detailState.update {
                it.copy(isLoading = false, error = "Movie not found")
            }
        } else {
            repository.fetchMovieDetail(id).collect { response ->
                when (response) {
                    is Response.Error -> {
                        _detailState.update {
                            it.copy(isLoading = false, error = response.error?.message)
                        }
                    }

                    is Response.Success -> {
                        _detailState.update {
                            it.copy(
                                isLoading = false,
                                error = null,
                                movieDetail = response.data
                            )
                        }
                    }

                    is Response.Loading -> {
                        _detailState.update {
                            it.copy(isLoading = true, error = null)
                        }
                    }
                }
            }
        }
    }

    fun fetchMovie() = viewModelScope.launch {
        repository.fetchMovie().collect() { response ->
            when (response) {
                is Response.Error -> {
                    _detailState.update {
                        it.copy(isLoading = false, error = response.error?.message)
                    }
                }

                is Response.Success -> {
                    _detailState.update {
                        it.copy(
                            isLoading = false,
                            error = null,
                            movies = response.data
                        )
                    }
                }

                is Response.Loading -> {
                    _detailState.update {
                        it.copy(isLoading = true, error = null)
                    }
                }
            }
        }
    }

}

data class DetailState(
    val movieDetail: MovieDetail? = null,
    val movies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isMovieLoading: Boolean = false
)