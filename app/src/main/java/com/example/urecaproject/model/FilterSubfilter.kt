package com.example.urecaproject.model

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import com.zomato.photofilters.imageprocessors.SubFilter

class FilterSubfilter (
    var value : Int,
    var filter : Bitmap
    ): SubFilter {

    private var tag = ""

    override fun process(inputImage: Bitmap): Bitmap {
        val layer : Bitmap = inputImage.copy(Bitmap.Config.ARGB_8888, true)
        val filter : Bitmap = Bitmap.createScaledBitmap(filter, layer.width, layer.height, true)
        val paint = Paint().apply {
            isAntiAlias = true
            alpha = value
        }
        val comboImage = Canvas(layer)
        comboImage.drawBitmap(filter, 0f, 0f, paint)
        return layer
    }

    override fun setTag(tag: Any) {
        this.tag = tag.toString()
    }

    override fun getTag(): Any {
        return tag
    }
}