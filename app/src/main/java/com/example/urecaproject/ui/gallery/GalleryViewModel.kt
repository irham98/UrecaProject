package com.example.urecaproject.ui.gallery

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
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

    /**
     * Getting All Images Path.
     *
     * Required Storage Permission
     *
     * @return ArrayList with images Path
     */
    private fun loadImagesfromSDCard(): ArrayList<String> {
        val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val cursor: Cursor?
        val columnIndexId: Int
        val imageList = ArrayList<String>()
        var imageId: Long
        var imagePath: String?

        val projection = arrayOf(MediaStore.Images.Media._ID,MediaStore.Images.Media.RELATIVE_PATH )

        cursor = context.contentResolver.query(uri, projection, null, null, null)
        columnIndexId = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
        val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.RELATIVE_PATH)


        while (cursor.moveToNext()) {

            imageId = cursor.getLong(columnIndexId)

            val some = cursor.getString(columnIndex)
            Log.i("imagerelative", ""+some)
            val imageUri = ContentUris.withAppendedId(uri,  imageId)
            //val file = File(imageUri)
            imagePath = imageUri.toString()
            //Log.i("imagepath", imagePath)
            //val input = context.contentResolver.getType(imageUri)
            //val inputAsString = input!!.bufferedReader().use { it.readText() }
            //Log.i("imagepath",input)
            imageList.add(imagePath)
        }
        cursor.close()
        return imageList
    }

    fun getAllImages() {
        launch(Dispatchers.Main) {
            imagesLiveData.value = withContext(Dispatchers.IO) {
                loadImagesfromSDCard()
            }
        }
    }
}