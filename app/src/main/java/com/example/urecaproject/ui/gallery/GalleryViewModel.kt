package com.example.urecaproject.ui.gallery

import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.util.Size
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

        val projection = arrayOf(MediaStore.Images.Media._ID)

        cursor = context.contentResolver.query(uri, projection, null, null, null)
        if (cursor != null) {

                Log.i("number of rows", cursor.count.toString())
        }
        columnIndexId = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media._ID)


        while (cursor.moveToNext()) {

            imageId = cursor.getLong(columnIndexId)
            Log.i("imageid", ""+imageId)
            val imageUri = Uri.withAppendedPath(uri, "" + imageId)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val bitmap : Bitmap = context.contentResolver.loadThumbnail(imageUri, Size(640, 480), null)

            }
            imagePath = imageUri.toString()
            Log.i("imagepath", imagePath)
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