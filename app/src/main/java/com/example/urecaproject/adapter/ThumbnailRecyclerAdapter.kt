package com.example.urecaproject.adapter

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.thumbnail_item.view.*


class ThumbnailRecyclerAdapter (
    private val context: Context,
    @LayoutRes private val layoutId: Int,
    private var galleryImages: ArrayList<Bitmap>,
    private val listener: ImageListener)
    : RecyclerView.Adapter<ThumbnailRecyclerAdapter.ViewHolder>()   {

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private val view: View = v
        private lateinit var item: Bitmap

        override fun onClick(view: View?) {
            listener.onImageSelected(galleryImages.get(adapterPosition))
        }

        init {
            v.setOnClickListener(this)
        }

        fun bindItem(item: Bitmap) {
            this.item = item
            view.thumbImage.setImageBitmap(item)
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
        val item : Bitmap = galleryImages[position]
        holder.bindItem(item)
    }

    fun setImages(galleryImages: ArrayList<Bitmap>) {
        this.galleryImages = galleryImages
        notifyDataSetChanged()
    }

    interface ImageListener {
        fun onImageSelected (imageBitmap: Bitmap)
    }


}