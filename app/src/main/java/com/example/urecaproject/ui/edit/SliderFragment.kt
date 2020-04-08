package com.example.urecaproject.ui.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.example.urecaproject.R
import com.example.urecaproject.factory.AdjustModelFactory
import com.example.urecaproject.model.AdjustModel
import kotlinx.android.synthetic.main.fragment_slider.*

class SliderFragment : Fragment(), SeekBar.OnSeekBarChangeListener {

    private lateinit var editType : String
    private lateinit var edit : AdjustModel

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
                seekBar.setOnSeekBarChangeListener(this)
            }
            else {
                editType = "Filter"
                seekBar.max = 255
                seekBar.min = 0
                seekBar.progress = 0
                seekBar.setOnSeekBarChangeListener(this)
            }
        }

    }


    override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
        var progressFloat : Float = progress.toFloat()
        if (editType.equals("Filter")) {

        }
        else {

            progressFloat *= edit.multiplier

        }
        editImageListener.onSliderChanged(progressFloat, editType)


/*        if (editType.equals("Saturation"))
            saturationControls(p1)
        else if (editType.equals("Contrast"))
            contrastControls(p1)
        else if (editType.equals("Brightness"))
            brightnessControls(p1)*/
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {
        editImageListener.onEditStarted()
    }

    override fun onStopTrackingTouch(p0: SeekBar?) {
        editImageListener.onEditCompleted()
    }

/*
    fun brightnessControls(progress : Int) {
        val progressFloat : Float = progress.toFloat()
        editImageListener.onSliderChanged(progressFloat, editType)
    }

    fun contrastControls(progress: Int) {
        var progressFloat : Float = progress.toFloat()
        progressFloat *= 0.01f
        editImageListener.onSliderChanged(progressFloat, editType)
    }

    fun saturationControls(progress : Int) {
        var progressFloat : Float = progress.toFloat()
        progressFloat *= 0.01f
        editImageListener.onSliderChanged(progressFloat, editType)
    }
*/

    interface EditImageListener {
        fun onSliderChanged(value: Float, string: String)
        fun onEditStarted()
        fun onEditCompleted()
    }
}