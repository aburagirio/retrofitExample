package com.example.retrofitExample

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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.example.retrofitExample.interfaces.JsonPlaceHolderAPI
import com.example.retrofitExample.ui.theme.RetrofitTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "MainActivity"
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    /**
     * JsonPlaceHolderAPIのインスタンス
     *
     * https://jsonplaceholder.typicode.com/ から記事を取得する
     *
     * - converterとしてGsonConverterFactoryを与えている
     */
    @Inject
    lateinit var jsonPlaceHolderAPI: JsonPlaceHolderAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CoroutineScope(Dispatchers.Default).launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                /**
                 * Resumeすると実行する
                 */
                launch(Dispatchers.IO) {
                    val postList = jsonPlaceHolderAPI.getPosts()
                    val text = "postList size is ${postList.size}"
                    Log.d(TAG, "getPost: $text")
                    Log.d(TAG, "getPost: ${postList[0].text}")
                }
            }
        }

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