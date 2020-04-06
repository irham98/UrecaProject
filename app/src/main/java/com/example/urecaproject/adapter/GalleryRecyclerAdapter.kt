package com.example.urecaproject.adapter

import android.R
import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.gallery_item.view.*
import java.io.File


class GalleryRecyclerAdapter (private val context: Context,
    @LayoutRes private val layoutId: Int,
    private var galleryImages: ArrayList<String>) : RecyclerView.Adapter<GalleryRecyclerAdapter.ViewHolder>()   {

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private val view: View = v
        private lateinit var item: String

        override fun onClick(p0: View?) {
            //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        init {
            v.setOnClickListener(this)
        }

        fun bindItem(item: String) {
            this.item = item
            getImage(item)
        }

        private fun getImage(item: String) {
/*            val imgFile = File(item)
            if (imgFile.exists()) {
                val myBitmap = BitmapFactory.decodeFile(item)
                view.galleryImage.setImageBitmap(myBitmap)
            }*/
            Glide.with(context)
                .load(Uri.decode(item))
                .thumbnail(0.1f)
                .apply(RequestOptions().placeholder(R.drawable.ic_menu_camera).centerCrop())
                .into(view.galleryImage)
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
        val item : String = galleryImages[position]
        holder.bindItem(item)
    }

    fun setImages(galleryImages: ArrayList<String>) {
        this.galleryImages = galleryImages
        notifyDataSetChanged()
    }


}