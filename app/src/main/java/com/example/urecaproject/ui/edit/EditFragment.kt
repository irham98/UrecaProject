package com.example.urecaproject.ui.edit

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.urecaproject.R
import com.zomato.photofilters.imageprocessors.Filter
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubFilter
import com.zomato.photofilters.imageprocessors.subfilters.VignetteSubFilter
import kotlinx.android.synthetic.main.fragment_edit.*
import kotlinx.android.synthetic.main.fragment_edit.view.*

class EditFragment : Fragment(), SliderFragment.EditImageListener {

    private val editViewModel: EditViewModel by lazy {
        ViewModelProvider(activity!!).get(EditViewModel::class.java)
    }

    private lateinit var imagePath : String
    private lateinit var origBm : Bitmap
    private lateinit var filteredBm : Bitmap


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        SliderFragment.setListener(this)
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.bottom_fragment) as NavHostFragment?
        val navController = navHostFragment!!.navController
        NavigationUI.setupWithNavController(bottomNav, navController)

        showImage(view)

        editViewModel.filteredBitmap.observe(viewLifecycleOwner, Observer { bitmap ->
            filteredBm = bitmap
            chosenImage.setImageBitmap(filteredBm)
        })
    }

    private fun showImage(view: View) {
        val bundle: Bundle? = arguments

        val listener = object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                val image = resource as BitmapDrawable
                origBm = image.bitmap
                editViewModel.setBitmap(origBm)
                return false
            }

        }
        if (bundle != null) {
            imagePath = bundle.getString("path")!!
            Glide.with(context!!)
                .load(Uri.decode(imagePath))
                .listener(listener)
                .apply(RequestOptions().placeholder(R.drawable.ic_broken_image).centerCrop())
                .into(view.chosenImage)
        }

    }

    fun toggleImageFilter (value: Int): Bitmap? {

        val layer : Bitmap = origBm.copy(Bitmap.Config.ARGB_8888, true)
        val filter : Bitmap = Bitmap.createScaledBitmap(filteredBm, layer.width, layer.height, true)
        val paint = Paint().apply {
            isAntiAlias = true
            alpha = value
        }
        val comboImage = Canvas(layer)
        comboImage.drawBitmap(filter, 0f, 0f, paint)
        return layer
    }

    override fun onSliderChanged(value: Float, string: String) {
        val myFilter = Filter()
        if (string.equals("Vignette")){
            myFilter.addSubFilter(VignetteSubFilter(context, value.toInt()))
            chosenImage.setImageBitmap(myFilter.processFilter(origBm.copy(Bitmap.Config.ARGB_8888, true)))
        }
        else if (string.equals("Brightness")){
            myFilter.addSubFilter(BrightnessSubFilter(value.toInt()))
            chosenImage.setImageBitmap(myFilter.processFilter(origBm.copy(Bitmap.Config.ARGB_8888, true)))
        }
        else if (string.equals("Saturation")){
            myFilter.addSubFilter(SaturationSubFilter(value))
            chosenImage.setImageBitmap(myFilter.processFilter(origBm.copy(Bitmap.Config.ARGB_8888, true)))
        }
        else if (string.equals("Contrast")){
            myFilter.addSubFilter(ContrastSubFilter(value))
            chosenImage.setImageBitmap(myFilter.processFilter(origBm.copy(Bitmap.Config.ARGB_8888, true)))
        }
        else if (string.equals("Filter")) {
            chosenImage.setImageBitmap(toggleImageFilter(value.toInt()))
        }

    }

    override fun onEditStarted() {
        //TODO("Not yet implemented")
    }

    override fun onEditCompleted() {
        //TODO("Not yet implemented")
    }

}