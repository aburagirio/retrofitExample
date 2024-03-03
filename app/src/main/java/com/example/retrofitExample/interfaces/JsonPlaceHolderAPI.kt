package com.example.retrofitExample.interfaces

import com.example.retrofitExample.dataClasses.Post
import retrofit2.http.GET

/**
 * JsonPlaceHolderAPIのインスタンス
 *
 * - https://jsonplaceholder.typicode.com/ から記事を取得する
 * - converterとしてGsonConverterFactoryを与えている
 */
interface JsonPlaceHolderAPI {

    /**
     * 全ての記事を取得する
     */
    @GET("posts")
    suspend fun getPosts(): List<Post>
}