package com.example.retrofitExample.di

import com.example.retrofitExample.interfaces.JsonPlaceHolderAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    /**
     * Retrofitのインスタスを供給する
     * urlはhttps://jsonplaceholder.typicode.com/、converterはGson
     */
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * RetrofitのインスタンスからJsonPlaceHolderAPIインターフェイスのインスタンスを生成し供給する
     */
    @Provides
    fun provideJsonPlaceHolderAPI(
        retrofit: Retrofit
    ): JsonPlaceHolderAPI{
        return retrofit.create<JsonPlaceHolderAPI>()
    }
}