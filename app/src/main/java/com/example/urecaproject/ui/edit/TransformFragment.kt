package com.example.urecaproject.ui.edit

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.urecaproject.R
import kotlinx.android.synthetic.main.fragment_transform.view.*
import org.opencv.core.Point


class TransformFragment : Fragment() {

    private val editViewModel: EditViewModel by lazy {
        ViewModelProvider(activity!!).get(EditViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_transform, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.cut.setOnClickListener({
                editViewModel.allowCut(true)
            })

        view.finish.setOnClickListener({
            editViewModel.finishCut(true)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        editViewModel.allowCut(false)
    }

}