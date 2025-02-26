package com.example.composemovie.movie.domain.repository

import com.example.composemovie.movie.domain.models.Movie
import com.example.composemovie.utils.Response
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun fetchDiscoverMovie(): Flow<Response<List<Movie>>>
    fun fetchTrendingMovie(): Flow<Response<List<Movie>>>
}