package com.example.urecaproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.urecaproject.R
import com.example.urecaproject.model.AdjustModel
import kotlinx.android.synthetic.main.adjust_item.view.*

class AdjustRecyclerAdapter(
    @LayoutRes private val layoutId: Int,
    private val adjustItem: ArrayList<AdjustModel>,
    private val listener: ButtonListener)
    : RecyclerView.Adapter<AdjustRecyclerAdapter.ViewHolder>()  {

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private val view: View = v
        private lateinit var item: AdjustModel

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            listener.onButtonSelected(adjustItem.get(adapterPosition).text)
        }

        fun bindItem(item: AdjustModel) {
            this.item = item
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

    override fun getItemCount(): Int {
        return adjustItem.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item : AdjustModel = adjustItem[position]
        holder.bindItem(item)
    }

    interface ButtonListener {
        fun onButtonSelected(string : String)
    }
}