package com.example.urecaproject.ui.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.urecaproject.R
import kotlinx.android.synthetic.main.fragment_draw.*

class DrawFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_draw, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clearDraw.setOnClickListener { draw_view.clearCanvas() }
        undoDraw.setOnClickListener { draw_view.undo() }
        redoDraw.setOnClickListener { draw_view.redo() }
    }
}