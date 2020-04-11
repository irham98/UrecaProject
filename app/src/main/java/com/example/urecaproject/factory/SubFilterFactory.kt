package com.example.urecaproject.factory

import android.content.Context
import android.graphics.Bitmap
import com.example.urecaproject.model.FilterSubFilter
import com.zomato.photofilters.imageprocessors.SubFilter
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubFilter
import com.zomato.photofilters.imageprocessors.subfilters.VignetteSubFilter

class SubFilterFactory {

    fun getSubFilter(context: Context? = null, subfilter : String, value: Float, bitmap: Bitmap? = null) : SubFilter {
        val valueInt : Int = value.toInt()
        when(subfilter) {
            "Brightness" -> return BrightnessSubFilter(valueInt)
            "Vignette" -> return VignetteSubFilter(context, valueInt)
            "Saturation" -> return SaturationSubFilter(value)
            "Contrast" -> return ContrastSubFilter(value)
            "Filter" -> return FilterSubFilter(valueInt, bitmap!!)
            //some default
            else -> return BrightnessSubFilter(valueInt)
        }
    }

}