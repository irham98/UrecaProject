package com.example.urecaproject

import android.content.Context
import com.example.urecaproject.model.FilterModel
import com.zomato.photofilters.geometry.Point
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubFilter
import com.zomato.photofilters.imageprocessors.subfilters.ToneCurveSubFilter

class FilterPack {
    companion object {
        fun getFilterPack(context : Context) : ArrayList<FilterModel> {
            val filters : ArrayList<FilterModel> = ArrayList()
            filters.add(getStarlitFilter(context))
            filters.add(getMetropolisFilter(context))
            filters.add(getAdeleFilter(context))
            return filters
        }

        fun getStarlitFilter(context: Context) : FilterModel {
            val rgbKnots = arrayOfNulls<Point>(8)
            rgbKnots[0] = Point(0f, 0f)
            rgbKnots[1] = Point(34f, 6f)
            rgbKnots[2] = Point(69f, 23f)
            rgbKnots[3] = Point(100f, 58f)
            rgbKnots[4] = Point(150f, 154f)
            rgbKnots[5] = Point(176f, 196f)
            rgbKnots[6] = Point(207f, 233f)
            rgbKnots[7] = Point(255f, 255f)
            val filter : FilterModel = FilterModel()
            filter.name = "Starlit"
            filter.addSubfilter(ToneCurveSubFilter(rgbKnots, null, null, null))
            return filter
        }

        fun getAdeleFilter(context: Context) : FilterModel {
            val filter : FilterModel = FilterModel("Adele")
            filter.addSubfilter(SaturationSubFilter(-100f))
            return filter
        }

        fun getMetropolisFilter(context: Context) : FilterModel {
            val filter : FilterModel = FilterModel("Metropolis")
            filter.addSubfilter(SaturationSubFilter(-100f))
            filter.addSubfilter(ContrastSubFilter(1.7f))
            filter.addSubfilter(BrightnessSubFilter(70))
            return filter
        }
    }
}