package com.example.urecaproject.ui.edit

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EditViewModel : ViewModel() {

    private val _bitmap = MutableLiveData<Bitmap>()
    private val _filteredBitmap = MutableLiveData<Bitmap>()
    val bitmap : LiveData<Bitmap> = _bitmap
    val filteredBitmap : LiveData<Bitmap> = _filteredBitmap

    fun setBitmap (item: Bitmap) {
        _bitmap.value = item
    }

    fun setFilteredBitmap(item: Bitmap) {
        _filteredBitmap.value = item
    }

}