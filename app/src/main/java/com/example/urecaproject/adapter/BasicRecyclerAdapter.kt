package com.example.urecaproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.urecaproject.R
import com.example.urecaproject.model.BasicModel
import kotlinx.android.synthetic.main.basic_item.view.*

class BasicRecyclerAdapter(@LayoutRes private val layoutId: Int, private val basicItem: ArrayList<String>) : RecyclerView.Adapter<BasicRecyclerAdapter.ViewHolder>()  {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private var view: View = v
        private var item: String? = null

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        fun bindItem(item: String) {
            this.item = item
            view.buttonName.text = item
            if (item.equals("Exposure")) view.buttonImage.setImageResource(R.drawable.ic_edit_exposure)
            else if (item.equals("Contrast")) view.buttonImage.setImageResource(R.drawable.ic_edit_contrast)
            else if (item.equals("Adjust")) view.buttonImage.setImageResource(R.drawable.ic_edit_adjust)
        }


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BasicRecyclerAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(layoutId, parent, false))
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        return basicItem.size
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: BasicRecyclerAdapter.ViewHolder, position: Int) {

        val item : String = basicItem[position]
        holder.bindItem(item)
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}