package com.example.urecaproject.model

import android.graphics.Bitmap
import com.zomato.photofilters.imageprocessors.Filter

class FilterModel(
    var name : String? = null) : Filter() {

/*    fun addSubfilter (subFilter: SubFilter) {
        subfilters.add(subFilter)
    }

    fun processFilter(inputImage: Bitmap): Bitmap {
        var outputImage = inputImage.copy(Bitmap.Config.ARGB_8888, true)
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
    }*/

}

/*package com.example.urecaproject.model

import android.graphics.Bitmap
import com.zomato.photofilters.imageprocessors.Filter
import com.zomato.photofilters.imageprocessors.SubFilter

class FilterModel : Filter (
) {

    fun addSubfilter (subFilter: SubFilter) {
        subfilters.add(subFilter)
    }

    fun processFilter(inputImage: Bitmap): Bitmap {
        var outputImage = inputImage.copy(Bitmap.Config.ARGB_8888, true)
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
    }*/

