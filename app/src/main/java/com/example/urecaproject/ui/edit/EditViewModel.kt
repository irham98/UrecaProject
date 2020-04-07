package com.example.urecaproject.ui.edit

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EditViewModel : ViewModel() {

    private val _bitmap = MutableLiveData<Bitmap>()
    val bitmap : LiveData<Bitmap> = _bitmap

    fun setBitmap (item: Bitmap) {
        _bitmap.value = item
    }

}