package com.example.retrofit

import com.example.retrofit.dataClasses.Post
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * UIの状態を保管し広報する
 */

class UIStateHolder {
    private val scope = CoroutineScope(Dispatchers.Main)
    private val _postsStateFlow: MutableStateFlow<List<Post>> = MutableStateFlow(listOf())
    var postsStateFlow = _postsStateFlow.asStateFlow()

    /**
     * 記事のリストを広報する
     *
     * 記事を取得するにはpostsStateFlowをcollectする
     */
    fun updatePostsList(post: List<Post>){
        _postsStateFlow.update { post }
    }
}