package com.example.urecaproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.urecaproject.R
import com.example.urecaproject.model.AdjustModel
import kotlinx.android.synthetic.main.adjust_item.view.*

class AdjustRecyclerAdapter(@LayoutRes private val layoutId: Int, private val adjustItem: ArrayList<AdjustModel>) : RecyclerView.Adapter<AdjustRecyclerAdapter.ViewHolder>()  {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private var view: View = v
        private var item: AdjustModel? = null

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
    ): AdjustRecyclerAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(layoutId, parent, false))
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        return adjustItem.size
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: AdjustRecyclerAdapter.ViewHolder, position: Int) {

        val item : AdjustModel = adjustItem[position]
        holder.bindItem(item)
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}