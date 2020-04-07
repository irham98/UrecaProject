package com.example.urecaproject.model

import android.graphics.Bitmap
import com.zomato.photofilters.imageprocessors.SubFilter

class FilterModel(
    var name : String? = null,
    val subfilters : ArrayList<SubFilter> = ArrayList()) {

    fun addSubfilter (subFilter: SubFilter) {
        subfilters.add(subFilter)
    }

    fun processFilter(inputImage: Bitmap): Bitmap {
        var outputImage = inputImage
        for (subFilter in subfilters) {
            try {
                //processing via library
                outputImage = subFilter.process(outputImage)
            } catch (oe: OutOfMemoryError) {
                System.gc()
                try {
                    outputImage = subFilter.process(outputImage)
                } catch (ignored: OutOfMemoryError) {
                }
            }
        }
        return outputImage
    }

}