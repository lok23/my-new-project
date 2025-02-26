package com.example.composemovie.movie_detail.domain.repository

import com.example.composemovie.movie.domain.models.Movie
import com.example.composemovie.movie_detail.domain.models.MovieDetail
import com.example.composemovie.utils.Response
import kotlinx.coroutines.flow.Flow

interface MovieDetailRepository {
    fun fetchMovieDetail(movieId: Int): Flow<Response<MovieDetail>>
    fun fetchMovie(): Flow<Response<List<Movie>>>
}