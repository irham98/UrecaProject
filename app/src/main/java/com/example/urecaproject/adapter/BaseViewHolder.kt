package com.example.urecaproject.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(itemView: View)
    : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    abstract var item : T

    init {
        itemView.setOnClickListener(this)
    }

    open suspend fun bindItem(item: T) {
        this.item = item
    }
}