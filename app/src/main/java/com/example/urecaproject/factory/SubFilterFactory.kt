package com.example.urecaproject.factory

import android.content.Context
import android.graphics.Bitmap
import com.zomato.photofilters.imageprocessors.SubFilter
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubFilter
import com.zomato.photofilters.imageprocessors.subfilters.VignetteSubFilter
import kotlinx.android.synthetic.main.fragment_edit.*

class SubFilterFactory {

    fun getSubFilter(context: Context, subfilter : String, value: Float) : SubFilter {
        val valueInt : Int = value.toInt()
        if (subfilter.equals("Brightness", true)) {
            return BrightnessSubFilter(valueInt)
        }
        else if (subfilter.equals("Vignette", true)) {
            return VignetteSubFilter(context, valueInt)
        }
        else if (subfilter.equals("Saturation", true)) {
            return SaturationSubFilter(value)
        }
        else if (subfilter.equals("Contrast", true)) {
            return ContrastSubFilter(value)
        }
        //some default
        return BrightnessSubFilter(valueInt)
    }

}