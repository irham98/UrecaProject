package com.example.urecaproject.adapter

import android.content.Context
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseAdapter<T, VH : BaseViewHolder<T>>
    (val context: Context)
    : RecyclerView.Adapter<VH>() {

    lateinit var items : List<T>
    var layoutId: Int = 0
    lateinit var listener: ItemListener<T>

    constructor(context: Context, items : List<T>, @LayoutRes layoutId : Int, listener: ItemListener<T>)
            : this(context) {
        this.items = items
        this.layoutId = layoutId
        this.listener = listener
    }

    constructor(context: Context,  @LayoutRes layoutId : Int, listener: ItemListener<T>)
            : this(context) {
        this.layoutId = layoutId
        this.listener = listener
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item : T = items[position]
        CoroutineScope(Dispatchers.Main).launch{holder.bindItem(item)}
    }

    interface ItemListener<T> {
        fun onItemClicked(item : T)
    }
}