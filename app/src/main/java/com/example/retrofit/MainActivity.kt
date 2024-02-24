package com.example.retrofit

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.retrofit.dataClasses.Post
import com.example.retrofit.interfaces.JsonPlaceHolderGetter
import com.example.retrofit.ui.theme.RetrofitTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

private const val TAG = "MainActivity"
class MainActivity : ComponentActivity() {

    /**
     * UIStateHolderのインスタンス
     */
    private val uiStateHolder = UIStateHolder()

    /**
     * Retrofitのインスタンス
     * - 接続先は https://jsonplaceholder.typicode.com/
     * - converterとしてGsonConverterFactoryを与えている
     */
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    /**
     * JsonPlaceHolderGetterのインスタンス
     *
     * https://jsonplaceholder.typicode.com/ から記事を取得する
     *
     */
    private val jsonPlaceHolderGetter = retrofit.create<JsonPlaceHolderGetter>()

    /**
     * retrofit2.Callbackのインスタンス
     * Post型のインスタンスのListを得田場合と失敗した場合を捕捉する
     */
    private val callback: Callback<List<Post>> = object: Callback<List<Post>> {
        override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
            Log.d(TAG, "onResponse: "+ response.body()?.size)
            val body = response.body()
            if (body != null) {
                uiStateHolder.updatePostsList(body)
            }
        }

        override fun onFailure(call: Call<List<Post>>, t: Throwable) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RetrofitTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        val call = jsonPlaceHolderGetter.getPosts()
        call.enqueue(callback)
    }


}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RetrofitTheme {
        Greeting("Android")
    }
}