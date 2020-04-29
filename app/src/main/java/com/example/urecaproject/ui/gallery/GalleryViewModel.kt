package com.example.urecaproject.ui.gallery

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class GalleryViewModel (private val context: Context): ViewModel(), CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    private var imagesLiveData: MutableLiveData<List<String>> = MutableLiveData()

    fun getImageList(): LiveData<List<String>> {
        return imagesLiveData
    }

    private var videosLiveData: MutableLiveData<List<String>> = MutableLiveData()

    fun getVideosList(): LiveData<List<String>> {
        return videosLiveData
    }



    /**
     * Getting All Images Path.
     *
     * Required Storage Permission
     *
     * @return ArrayList with images Path
     */
    private fun loadFromSDCard(extUri: Uri, colStr: String ): ArrayList<String> {
        val uri: Uri = extUri
        val cursor: Cursor?
        val columnIndexId: Int
        val list = ArrayList<String>()
        var id: Long
        var path: String?

        val projection = arrayOf(colStr)

        cursor = context.contentResolver.query(uri, projection, null, null, null)
        columnIndexId = cursor!!.getColumnIndexOrThrow(colStr)


        while (cursor.moveToNext()) {

            id = cursor.getLong(columnIndexId)

            val itemUri = ContentUris.withAppendedId(uri,  id)
            //val file = File(imageUri)
            path = itemUri.toString()
            Log.i("imagepath", path)
            list.add(path)
        }
        cursor.close()
        return list
    }



    fun getAllDocs() {
        launch(Dispatchers.Main) {
            val img = async {
                loadFromSDCard(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, MediaStore.Images.Media._ID)
            }
            val vid = async {
                loadFromSDCard(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, MediaStore.Video.Media._ID)
            }
/*            val doc = async {
                loadFromSDCard(MediaStore.Files.getContentUri("external"), MediaStore.Downloads._ID)
            }*/
            imagesLiveData.value = img.await()
            videosLiveData.value = vid.await()
        }
    }
}