package com.example.urecaproject

import android.content.Context
import com.example.urecaproject.model.FilterModel
import com.zomato.photofilters.geometry.Point
import com.zomato.photofilters.imageprocessors.Filter
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubFilter
import com.zomato.photofilters.imageprocessors.subfilters.ToneCurveSubFilter

class FilterPack {
    companion object {
        fun getFilterPack(context : Context) : ArrayList<FilterModel> {
            val filters : ArrayList<FilterModel> = ArrayList()
            filters.add(getStarlitFilter(context))
            filters.add(getBlueMessFilter(context))
            filters.add(getAmazonFilter(context))
            filters.add(getAwestruckFilter(context))
            filters.add(getLimeFilter(context))
            filters.add(getMetropolisFilter(context))
            filters.add(getAdeleFilter(context))
            return filters
        }

        fun getAmazonFilter(context: Context): FilterModel {
            val blueKnots: Array<Point?>
            blueKnots = arrayOfNulls(6)
            blueKnots[0] = Point(0f, 0f)
            blueKnots[1] = Point(11f, 40f)
            blueKnots[2] = Point(36f, 99f)
            blueKnots[3] = Point(86f, 151f)
            blueKnots[4] = Point(167f, 209f)
            blueKnots[5] = Point(255f, 255f)
            val filter = FilterModel(context.getString(R.string.amazon))
            filter.addSubfilter(ContrastSubFilter(1.2f))
            filter.addSubfilter(ToneCurveSubFilter(null, null, null, blueKnots))
            return filter
        }

        fun getLimeFilter(context: Context): FilterModel {
            val blueKnots: Array<Point?>
            blueKnots = arrayOfNulls(3)
            blueKnots[0] = Point(0f, 0f)
            blueKnots[1] = Point(165f, 114f)
            blueKnots[2] = Point(255f, 255f)
            val filter = FilterModel(context.getString(R.string.lime))
            filter.addSubfilter(ToneCurveSubFilter(null, null, null, blueKnots))
            return filter
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
            val filter = FilterModel(context.getString(R.string.starlit))
            filter.addSubfilter(ToneCurveSubFilter(rgbKnots, null, null, null))
            return filter
        }

        fun getBlueMessFilter(context: Context): FilterModel {
            val redKnots: Array<Point?>
            redKnots = arrayOfNulls(8)
            redKnots[0] = Point(0f, 0f)
            redKnots[1] = Point(86f, 34f)
            redKnots[2] = Point(117f, 41f)
            redKnots[3] = Point(146f, 80f)
            redKnots[4] = Point(170f, 151f)
            redKnots[5] = Point(200f, 214f)
            redKnots[6] = Point(225f, 242f)
            redKnots[7] = Point(255f, 255f)
            val filter = FilterModel(context.getString(R.string.bluemess))
            filter.addSubfilter(ToneCurveSubFilter(null, redKnots, null, null))
            filter.addSubfilter(BrightnessSubFilter(30))
            filter.addSubfilter(ContrastSubFilter(1f))
            return filter
        }

        fun getAwestruckFilter(context: Context): FilterModel {
            val rgbKnots: Array<Point?>
            val redKnots: Array<Point?>
            val greenKnots: Array<Point?>
            val blueKnots: Array<Point?>

            rgbKnots = arrayOfNulls(5)
            rgbKnots[0] = Point(0f, 0f)
            rgbKnots[1] = Point(80f, 43f)
            rgbKnots[2] = Point(149f, 102f)
            rgbKnots[3] = Point(201f, 173f)
            rgbKnots[4] = Point(255f, 255f)

            redKnots = arrayOfNulls(5)
            redKnots[0] = Point(0f, 0f)
            redKnots[1] = Point(125f, 147f)
            redKnots[2] = Point(177f, 199f)
            redKnots[3] = Point(213f, 228f)
            redKnots[4] = Point(255f, 255f)

            greenKnots = arrayOfNulls(6)
            greenKnots[0] = Point(0f, 0f)
            greenKnots[1] = Point(57f, 76f)
            greenKnots[2] = Point(103f, 130f)
            greenKnots[3] = Point(167f, 192f)
            greenKnots[4] = Point(211f, 229f)
            greenKnots[5] = Point(255f, 255f)

            blueKnots = arrayOfNulls(7)
            blueKnots[0] = Point(0f, 0f)
            blueKnots[1] = Point(38f, 62f)
            blueKnots[2] = Point(75f, 112f)
            blueKnots[3] = Point(116f, 158f)
            blueKnots[4] = Point(171f, 204f)
            blueKnots[5] = Point(212f, 233f)
            blueKnots[6] = Point(255f, 255f)

            val filter = FilterModel(context.getString(R.string.awestruck))
            filter.addSubfilter(ToneCurveSubFilter(rgbKnots, redKnots, greenKnots, blueKnots))
            return filter
        }

        fun getAdeleFilter(context: Context) : FilterModel {
            val filter = FilterModel(context.getString(R.string.adele))
            filter.addSubfilter(SaturationSubFilter(-100f))
            return filter
        }

        fun getMetropolisFilter(context: Context) : FilterModel {
            val filter  = FilterModel(context.getString(R.string.metropolis))
            filter.addSubfilter(SaturationSubFilter(-100f))
            filter.addSubfilter(ContrastSubFilter(1.7f))
            filter.addSubfilter(BrightnessSubFilter(70))
            return filter
        }
    }
}