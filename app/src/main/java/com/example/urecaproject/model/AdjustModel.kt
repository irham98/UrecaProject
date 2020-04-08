package com.example.urecaproject.model

import androidx.annotation.DrawableRes
import com.example.urecaproject.R
import com.zomato.photofilters.imageprocessors.Filter
import com.zomato.photofilters.imageprocessors.SubFilter
import com.zomato.photofilters.imageprocessors.subfilters.VignetteSubFilter

class AdjustModel (
    @DrawableRes var drawable: Int,
    var text: String,
    var max : Int = 0,
    var min : Int = 0,
    var multiplier : Float = 1f,
    var start: Int = 0
    ){
    companion object {
        var list: ArrayList<AdjustModel> = ArrayList()
        fun addAllAdjust() {
            if (list.size <= 0) {
                //list.add(AdjustModel(R.drawable.ic_adjust_exposure, "Exposure"))
                list.add(AdjustModel(R.drawable.ic_adjust_contrast, "Contrast", 200, 100, 0.01f, 100))
                list.add(AdjustModel(R.drawable.ic_adjust_brightness, "Brightness", 100, -100, 1f, 0))
                list.add(AdjustModel(R.drawable.ic_adjust_saturation, "Saturation",250, -50, 0.01f, 100))
                list.add(AdjustModel(R.drawable.ic_adjust_vignette, "Vignette", 255, 0, 1f, 0))
                list.add(AdjustModel(R.drawable.ic_adjust_shadows, "Shadows"))
            }
        }
    }

}