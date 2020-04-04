package com.example.urecaproject.ui.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.urecaproject.R
import com.example.urecaproject.adapter.BasicRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_basic.*
import kotlinx.android.synthetic.main.fragment_basic.view.*

class BasicFragment : Fragment() {

    //private lateinit var adapter: BasicRecyclerAdapter
    val basicItems: ArrayList<String> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_basic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addBasicItems()

        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        basicRv.layoutManager = linearLayoutManager

        basicRv.adapter = BasicRecyclerAdapter(R.layout.basic_item, basicItems)

    }

    private fun addBasicItems() {
        basicItems.add("Exposure")
        basicItems.add("Contrast")
        basicItems.add("Adjust")
    }


}