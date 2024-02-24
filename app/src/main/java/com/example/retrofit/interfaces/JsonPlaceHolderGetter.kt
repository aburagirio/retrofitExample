package com.example.retrofit.interfaces

import com.example.retrofit.dataClasses.Post
import retrofit2.Call
import retrofit2.http.GET

interface JsonPlaceHolderGetter {

    /**
     * 全ての記事を取得する
     */
    @GET("posts")
    fun getPosts(): Call<List<Post>>
}