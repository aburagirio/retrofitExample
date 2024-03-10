package com.example.retrofitExample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.example.retrofitExample.dataClasses.Post
import com.example.retrofitExample.ui.theme.RetrofitTheme
import com.example.retrofitExample.viewModels.JsonPlaceHolderViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    /**
     * JsonPlaceHolderViewModelのインスタンス
     */
    private val jsonPlaceHolderViewModel: JsonPlaceHolderViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CoroutineScope(Dispatchers.Default).launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                /**
                 * Resumeすると実行する
                 */
                jsonPlaceHolderViewModel.getPosts()
            }
        }

        setContent {
            RetrofitTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    JsonPlaceHolderUI(
                        jsonPlaceHolderUIState = jsonPlaceHolderViewModel.jsonPlaceHolderUIState
                    )
                }
            }
        }
    }
}

/**
 * JsonPlaceHolderとの通信状況を表示するComposable関数
 *
 * @param jsonPlaceHolderUIState Success(listOfPosts) , Loading or Error
 * @param modifier Modifier
 */
@Composable
fun JsonPlaceHolderUI(
    jsonPlaceHolderUIState: JsonPlaceHolderUIState,
    modifier: Modifier = Modifier
){
    /**
     * jsonPlaceHolderUIStateの状態に応じて表示を差し替える
     */
    when (jsonPlaceHolderUIState){
        JsonPlaceHolderUIState.Error -> {
            Text(text = "Error", modifier = modifier)
        }
        JsonPlaceHolderUIState.Loading -> {
            Text(text = "Loading", modifier = modifier)
        }
        is JsonPlaceHolderUIState.Success -> {
            val p = jsonPlaceHolderUIState.posts
            JsonPlaceHolderLazyList(items = p)
        }
    }
}

@Composable
fun JsonPlaceHolderLazyList(
    items: List<Post>,
    modifier: Modifier = Modifier
){
    LazyColumn(
        modifier = modifier
    ) {
        items(count = items.size){
            ListItem(item = items[it])
        }
    }
}

@Composable
fun ListItem(
    item: Post,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        border = BorderStroke(width = Dp.Hairline, color = Color.Black),
        ){
        Text(
            text = item.title,
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(corner = CornerSize(10.dp)))
                .padding(4.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

sealed interface JsonPlaceHolderUIState{
    data class Success(val posts: List<Post>): JsonPlaceHolderUIState
    data object Error: JsonPlaceHolderUIState
    data object Loading: JsonPlaceHolderUIState
}
@Preview(showBackground = true)
@Composable
fun JsonPlaceHolderUIStateSuccessPreview(){
    val posts = listOf(
        Post(1,1 ,"1","1"),
        Post(2,2 ,"2","2"),
    )
    JsonPlaceHolderUI(jsonPlaceHolderUIState = JsonPlaceHolderUIState.Success(posts))
}
@Preview(showBackground = true)
@Composable
fun JsonPlaceHolderUIStateLoadingPreview(){
    JsonPlaceHolderUI(jsonPlaceHolderUIState = JsonPlaceHolderUIState.Loading)
}
@Preview(showBackground = true)
@Composable
fun JsonPlaceHolderUIStateErrorPreview(){
    JsonPlaceHolderUI(jsonPlaceHolderUIState = JsonPlaceHolderUIState.Error)
}
