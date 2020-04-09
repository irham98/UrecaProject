package com.example.urecaproject.ui.edit

import android.graphics.Bitmap
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EditViewModel : ViewModel() {

    private val _bitmap = MutableLiveData<Bitmap>()
    private val _filteredBitmap = MutableLiveData<Bitmap>()
    private val _visible = MutableLiveData<Int>()

    val bitmap : LiveData<Bitmap> = _bitmap
    val filteredBitmap : LiveData<Bitmap> = _filteredBitmap
    val visible : LiveData<Int> = _visible

    init{
        _visible.value = View.VISIBLE
    }

    fun setBitmap (value: Bitmap) {
        _bitmap.value = value
    }

    fun setFilteredBitmap(value: Bitmap) {
        _filteredBitmap.value = value
    }

    fun setVisible (value : Int) {
        _visible.value = value
    }

}