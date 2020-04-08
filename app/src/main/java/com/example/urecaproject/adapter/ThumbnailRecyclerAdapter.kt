package com.example.urecaproject.adapter

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.urecaproject.FilterPack
import com.example.urecaproject.R
import com.example.urecaproject.model.FilterModel
import kotlinx.android.synthetic.main.thumbnail_item.view.*


class ThumbnailRecyclerAdapter (
    private val context: Context,
    @LayoutRes private val layoutId: Int,
    private var imageBitmap : Bitmap,
    private val listener: ImageListener)
    : RecyclerView.Adapter<ThumbnailRecyclerAdapter.ViewHolder>()   {

    private var galleryImages: ArrayList<FilterModel> = FilterPack.getFilterPack(context)
    //private lateinit var imageBitmap : Bitmap

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private val view: View = v
        private lateinit var item: FilterModel

        override fun onClick(view: View?) {
            listener.onImageSelected(galleryImages[adapterPosition])
        }

        init {
            v.setOnClickListener(this)
        }

        fun bindItem(item: FilterModel) {
            this.item = item
            processBitmap()
            //view.thumbImage.setImageBitmap(item)
        }

        fun processBitmap() {
            val size : Int = context.resources.getDimension(R.dimen.thumbnail_size).toInt()
            val small = Bitmap.createScaledBitmap(imageBitmap, size, size, false)
            val processedThumbnail = item.processFilter(small)
            view.thumbImage.setImageBitmap(processedThumbnail)
        }

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(layoutId, parent, false))
    }

    override fun getItemCount(): Int {
        return galleryImages.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item : FilterModel = galleryImages[position]
        holder.bindItem(item)
    }

    interface ImageListener {
        fun onImageSelected (imageFilter: FilterModel)
    }

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