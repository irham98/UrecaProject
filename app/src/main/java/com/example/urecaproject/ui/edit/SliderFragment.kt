package com.example.urecaproject.ui.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.urecaproject.R
import com.example.urecaproject.factory.AdjustModelFactory
import com.example.urecaproject.model.AdjustModel
import kotlinx.android.synthetic.main.fragment_slider.*

class SliderFragment : Fragment(), SeekBar.OnSeekBarChangeListener {

    private lateinit var editType : String
    private lateinit var edit : AdjustModel

    private val editViewModel: EditViewModel by lazy {
        ViewModelProvider(activity!!).get(EditViewModel::class.java)
    }

    companion object {
        private lateinit var editImageListener: EditImageListener
        fun setListener (listener: EditImageListener) {
            editImageListener = listener
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_slider, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = AdjustModelFactory()
        val bundle : Bundle? = arguments
        if (bundle != null) {
            if (bundle.containsKey("editType")) {
                editType = bundle.getString("editType")!!
                edit = factory.getAdjustModel(editType)!!
                seekBar.max = edit.max
                seekBar.min = edit.min
                seekBar.progress = edit.start
                editImageListener.onSliderInit(edit)
            }
            else {
                editType = "Filter"
                seekBar.max = 255
                seekBar.min = 0
                seekBar.progress = 255
            }
        }
        seekBar.setOnSeekBarChangeListener(this)


        editViewModel.visible.observe(viewLifecycleOwner, Observer { visible ->
            if (visible == View.VISIBLE) {
                Navigation.findNavController(view).navigate(R.id.nav_filter)
            }

        })

    }


    override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
        var progressFloat : Float = progress.toFloat()
        if (!editType.equals("Filter"))
            progressFloat *= edit.multiplier
        editImageListener.onSliderChanged(progressFloat, editType)
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {

    }

    override fun onStopTrackingTouch(p0: SeekBar?) {

    }

    interface EditImageListener {
        fun onSliderChanged(value: Float, string: String)
        fun onSliderInit(model : AdjustModel?)
    }
}