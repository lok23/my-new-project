package com.example.composemovie.di

import com.example.composemovie.common.data.ApiMapper
import com.example.composemovie.movie.data.remote.models.MovieDto
import com.example.composemovie.movie.domain.models.Movie
import com.example.composemovie.movie_detail.data.mapper_impl.MovieDetailMapperImpl
import com.example.composemovie.movie_detail.data.remote.api.MovieDetailApiService
import com.example.composemovie.movie_detail.data.remote.models.MovieDetailDto
import com.example.composemovie.movie_detail.data.repo_impl.MovieDetailRepositoryImpl
import com.example.composemovie.movie_detail.domain.models.MovieDetail
import com.example.composemovie.movie_detail.domain.repository.MovieDetailRepository
import com.example.composemovie.utils.Constants
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MovieDetailModule {
    private val json = Json {
        coerceInputValues = true
        ignoreUnknownKeys = true
    }


    @Provides
    @Singleton
    fun provideMovieDetailRepository(
        movieDetailApiService: MovieDetailApiService,
        mapper: ApiMapper<MovieDetail, MovieDetailDto>,
        movieMapper: ApiMapper<List<Movie>, MovieDto>
    ): MovieDetailRepository = MovieDetailRepositoryImpl(
        movieDetailApiService = movieDetailApiService,
        apiDetailMapper = mapper,
        apiMovieMapper = movieMapper,
    )

    @Provides
    @Singleton
    fun provideMovieMapper(): ApiMapper<MovieDetail, MovieDetailDto> = MovieDetailMapperImpl()

    @Provides
    @Singleton
    fun provideMovieDetailApiService(): MovieDetailApiService {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(MovieDetailApiService::class.java)
    }
}