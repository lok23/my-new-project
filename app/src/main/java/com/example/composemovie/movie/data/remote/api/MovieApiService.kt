package com.example.composemovie.movie.data.remote.api

import com.example.composemovie.BuildConfig
import com.example.composemovie.movie.data.remote.models.MovieDto
import com.example.composemovie.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

private const val TRENDING_MOVIE_ENDPOINT ="trending/movie/week"

interface MovieApiService {

    @GET(Constants.MOVIE_ENDPOINT)
    suspend fun fetchDiscoverMovie(
        @Query("api_key") apiKey: String = BuildConfig.apiKey,
        @Query("include_adult") includeAdult: Boolean = false
    ): MovieDto

    @GET(TRENDING_MOVIE_ENDPOINT)
    suspend fun fetchTrendingMovie(
        @Query("api_key") apiKey: String = BuildConfig.apiKey,
        @Query("include_adult") includeAdult: Boolean = false
    ): MovieDto
}