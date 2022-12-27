package com.example.pokemonapp.data.di

import com.example.pokemonapp.data.PokemonRepository
import com.example.pokemonapp.data.network.RemoteDataSource
import com.example.pokemonapp.data.network.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
class AppModule {

    @Provides
    fun providePokemonRepository(remoteDataSource: RemoteDataSource) : PokemonRepository = PokemonRepository(remoteDataSource)

    @Provides
    fun provideRemoteDataSource(apiService: ApiService) : RemoteDataSource = RemoteDataSource(apiService)
}