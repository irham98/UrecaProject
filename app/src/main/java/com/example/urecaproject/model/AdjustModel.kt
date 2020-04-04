package com.example.urecaproject.model

import androidx.annotation.DrawableRes
import com.example.urecaproject.R

class AdjustModel (@DrawableRes var drawable: Int, var text: String){
    companion object {
        var list: ArrayList<AdjustModel> = ArrayList()
        fun addAllAdjust() {
            list.add(AdjustModel(R.drawable.ic_adjust_exposure, "Exposure"))
            list.add(AdjustModel(R.drawable.ic_adjust_contrast, "Contrast"))
            list.add(AdjustModel(R.drawable.ic_adjust_brightness, "Brightness"))
            list.add(AdjustModel(R.drawable.ic_adjust_saturation, "Saturation"))
            list.add(AdjustModel(R.drawable.ic_adjust_shadows, "Shadows"))
            list.add(AdjustModel(R.drawable.ic_adjust_sharpen, "Sharpen"))

        }
    }


}