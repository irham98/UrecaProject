package com.example.urecaproject.adapter

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import coil.Coil
import coil.api.get
import coil.api.load
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.urecaproject.FilterPack
import com.example.urecaproject.R
import com.example.urecaproject.model.FilterModel
import kotlinx.android.synthetic.main.thumbnail_item.view.*
import kotlinx.coroutines.*


class ThumbnailRecyclerAdapter (
    context: Context,
    @LayoutRes layoutId: Int,
    private var imageBitmap : Bitmap,
    listener: ItemListener<FilterModel>)
    : BaseAdapter<FilterModel, ThumbnailRecyclerAdapter.ViewHolder>(context, layoutId, listener)   {

    //private var imageFilters: ArrayList<FilterModel> =

    init {
        items = FilterPack.getFilterPack(context)
    }

    inner class ViewHolder(v: View) : BaseViewHolder<FilterModel>(v) {
        private val view: View = v
        override lateinit var item: FilterModel

        override fun onClick(view: View?) {
            listener.onItemClicked(items[adapterPosition])
        }
/*
        init {
            v.setOnClickListener(this)
        }*/

        override suspend fun bindItem(item: FilterModel) {
            //view.thumbTitle.visibility = View.INVISIBLE
            super.bindItem(item)
            val thumbnail  = Coil.get(processBitmap())
            view.thumbImage.load(thumbnail) {
                crossfade(true)
                crossfade(200)
            }
            //Glide.with(view).load(thumbnail).thumbnail(0.1f).apply(RequestOptions().centerCrop()).into(view.thumbImage)
            view.thumbTitle.text = item.name
            //view.thumbTitle.visibility = View.VISIBLE
            //view.thumbImage.setImageBitmap(thumbnail)
            //view.thumbImage.setImageBitmap(item)
        }

        fun processBitmap() : Bitmap {
            val processedThumbnail = item.processFilter(imageBitmap.copy(Bitmap.Config.ARGB_8888, true))
            return processedThumbnail

        }

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(layoutId, parent, false))
    }

/*    override fun getItemCount(): Int {
        return imageFilters.size
    }*/

/*    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item : FilterModel = imageFilters[position]
        CoroutineScope(Dispatchers.Main).launch{holder.bindItem(item)}
    }*/

/*    interface ImageListener {
        fun onImageSelected (imageFilter: FilterModel)
    }*/

/*    fun setImages(galleryImages: ArrayList<Bitmap>) {
        this.galleryImages = galleryImages
        notifyDataSetChanged()
    }*/

/*    fun setImageBitmap (bitmap : Bitmap) {
        imageBitmap = bitmap
        processThumbnail()
        notifyDataSetChanged()
    }*/


/*    fun applyFilterForThumbnail () {
        val size : Int = context.resources.getDimension(R.dimen.thumbnail_size).toInt()
        for (filter in galleryImages) {
            //process the thumbnail and add that to a list of bitmap
            val small = Bitmap.createScaledBitmap(imageBitmap, size, size, false)
            val processedThumbnail = filter.processFilter(small)
            view.
            thumbnails.add(processedThumbnail)
        }
    }*/

}