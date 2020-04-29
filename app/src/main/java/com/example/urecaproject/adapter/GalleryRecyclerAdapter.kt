package com.example.urecaproject.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.urecaproject.R
import kotlinx.android.synthetic.main.gallery_item.view.*
import java.io.File


class GalleryRecyclerAdapter (
    context: Context,
    items: ArrayList<String>,
    @LayoutRes layoutId: Int,
    listener: ItemListener<String>)
    : BaseAdapter<String, GalleryRecyclerAdapter.ViewHolder>(context, items, layoutId, listener)   {

    inner class ViewHolder(v: View) : BaseViewHolder<String>(v) {
        private val view: View = v
        override lateinit var item: String

        override fun onClick(view: View?) {
            listener.onItemClicked(items[adapterPosition])
        }

/*        init {
            v.setOnClickListener(this)
        }*/

        override suspend fun bindItem(item: String) {
            super.bindItem(item)
            getImage(item)
        }

        private fun getImage(item: String) {

            view.galleryImage.load(item) {
                placeholder(R.drawable.ic_broken_image)
                crossfade(true)
                crossfade(500)
            }
/*            Glide.with(context)
                .load(Uri.decode(item))
                .thumbnail(0.01f)
                .apply(RequestOptions().placeholder(R.drawable.ic_broken_image).centerCrop())
                .into(view.galleryImage)*/
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(layoutId, parent, false))
    }

/*    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item : String = items[position]
        holder.bindItem(item)
    }*/

    fun setImages(galleryImages: ArrayList<String>) {
        this.items = galleryImages
        notifyDataSetChanged()
    }



}