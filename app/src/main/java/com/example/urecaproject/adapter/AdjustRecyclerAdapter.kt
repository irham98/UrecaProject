package com.example.urecaproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.urecaproject.R
import com.example.urecaproject.model.AdjustModel
import kotlinx.android.synthetic.main.adjust_item.view.*

class AdjustRecyclerAdapter(
    context: Context,
    items: ArrayList<AdjustModel>,
    @LayoutRes layoutId: Int,
    listener: ItemListener<AdjustModel>)
    : BaseAdapter<AdjustModel, AdjustRecyclerAdapter.ViewHolder>(context, items, layoutId, listener)  {

    inner class ViewHolder(v: View) : BaseViewHolder<AdjustModel>(v) {
        private val view: View = v
        override lateinit var item: AdjustModel

/*        init {
            v.setOnClickListener(this)
        }*/

        override fun onClick(p0: View?) {
            listener.onItemClicked(items[adapterPosition])
        }

        override suspend fun bindItem(item: AdjustModel) {
            super.bindItem(item)
            view.buttonName.text = item.text
            view.buttonImage.setImageResource(item.drawable)
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(layoutId, parent, false))

    }

/*    override fun getItemCount(): Int {
        return adjustItem.size

    }*/

/*    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item : AdjustModel = adjustItem[position]
        holder.bindItem(item)
    }*/

/*    interface ButtonListener {
        fun onButtonSelected(string : String)
    }*/
}