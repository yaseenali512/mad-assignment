package com.example.mad_class

import android.app.NotificationManager
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import coil.compose.AsyncImage
import com.example.mad_class.ui.theme.Mad_classTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.Request
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import java.net.URI
import java.util.Calendar
import kotlin.math.log

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<ImageViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityCompat.requestPermissions(
            this,
            arrayOf("android.permission.READ_MEDIA_IMAGES"),
            0
        )

        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
        )

        val yesterdayinmillis = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -1)
        }.timeInMillis

        val selection = "${MediaStore.Images.Media.DATE_TAKEN} >= ?"
        val selectionArgs = arrayOf(yesterdayinmillis.toString())

        val sortOrder = "${MediaStore.Images.Media.DATE_TAKEN} DESC"

        contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID)
            val nameColumn = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)

            val images = mutableListOf<Image>()

            while(cursor.moveToNext()){
                val id = cursor.getLong(idColumn);
                val name = cursor.getString(nameColumn);
                val uri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id
                )

                images.add(Image(id, name, uri));
            }
            viewModel.updateViewModel(images)

        }

        setContent {
            Mad_classTheme {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                        items(viewModel.images){image ->
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                AsyncImage(model=image.uri, contentDescription=null)
                                Text(text=image.name)
                            }
                        }
                }
            }
        }
    }
}

data class Image(
    val id: Long,
    val name: String,
    val uri: Uri
)

@Composable
fun Mainx(context: Context){
    var scope = rememberCoroutineScope()

    Column {
        Button(onClick = {
            scope.launch {
                var request = Request.Builder().url("https://fakestoreapi.com/products").build();

                OkHttpClient().newCall(request = request).enqueue()



                Log.d("Message", "this is coroutine function...")
            }


            Log.d("Message", "Main: Hello")
        }) {
            Text(text = "Corrutine")
        }
    }
}

private fun Call.enqueue() {
//    response.body?.string()?.let { Log.d("tag", it) }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
//    Main()
}