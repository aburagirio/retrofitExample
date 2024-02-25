package com.example.retrofitExample.interfaces

import com.example.retrofitExample.dataClasses.Post
import retrofit2.http.GET

interface JsonPlaceHolderAPI {

    /**
     * 全ての記事を取得する
     */
    @GET("posts")
    suspend fun getPosts(): List<Post>
}