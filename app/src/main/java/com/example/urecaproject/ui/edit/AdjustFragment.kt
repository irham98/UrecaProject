package com.example.urecaproject.ui.edit

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.urecaproject.R
import com.example.urecaproject.adapter.AdjustRecyclerAdapter
import com.example.urecaproject.model.AdjustModel
import kotlinx.android.synthetic.main.fragment_adjust.*

class AdjustFragment : Fragment() {

    private val editViewModel: EditViewModel by lazy {
        ViewModelProvider(activity!!).get(EditViewModel::class.java)
    }

    //private lateinit var adapter: BasicRecyclerAdapter
    private var adjustItems: ArrayList<AdjustModel> = ArrayList()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AdjustModel.addAllAdjust()
        adjustItems = AdjustModel.list
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_adjust, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        adjustRv.layoutManager = linearLayoutManager

        adjustRv.adapter = AdjustRecyclerAdapter(R.layout.adjust_item, adjustItems, object : AdjustRecyclerAdapter.ButtonListener {
            override fun onButtonSelected(string: String) {
                val bundle = Bundle()
                bundle.putString("editType", string)
                Navigation.findNavController(view).navigate(R.id.nav_slider, bundle)
                editViewModel.setVisible(View.INVISIBLE)
            }

        })

    }

}