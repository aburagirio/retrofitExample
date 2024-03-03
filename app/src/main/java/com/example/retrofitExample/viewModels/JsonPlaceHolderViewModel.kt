package com.example.retrofitExample.viewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.retrofitExample.JsonPlaceHolderUIState
import com.example.retrofitExample.interfaces.JsonPlaceHolderAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

private  const val TAG = "JsonPlaceHolderViewModel"
@HiltViewModel
class JsonPlaceHolderViewModel @Inject constructor (
    private val jsonPlaceHolderAPI: JsonPlaceHolderAPI
): ViewModel() {

    /**
     * JsonPlaceHolderUIStateのインスタンス
     *
     * - 初期値はLoading
     */
    var jsonPlaceHolderUIState: JsonPlaceHolderUIState by mutableStateOf(JsonPlaceHolderUIState.Loading)

    /**
     * JsonPlaceHolderからPostを取得するsuspend関数
     *　
     * - withContext(Dispatchers.IO)で新たなコルーチンを開始する
     * - 取得の開始時、jsonPlaceHolderUIStateにLoadingをセットする
     * - 取得が完了したら、jsonPlaceHolderUIStateにSuccess(listOfPosts)をセットする
     * - 取得に失敗したら、jsonPlaceHolderUIStateにErrorをセットする
     */
    suspend fun getPosts() = withContext(Dispatchers.IO){
        try {
            jsonPlaceHolderUIState = JsonPlaceHolderUIState.Loading

            /**
             * getPost()はsuspendなので、コルーチンスコープ内から呼ぶ
             */
            val postList = jsonPlaceHolderAPI.getPosts()

            /**
             * ロード中状態を模倣するためにdelayをおく
             */
            delay(1000)
            jsonPlaceHolderUIState = JsonPlaceHolderUIState.Success(postList)
        } catch (e: Exception){
            Log.e(TAG, "getPosts: exception", e)
            jsonPlaceHolderUIState= JsonPlaceHolderUIState.Error
        }
    }
}