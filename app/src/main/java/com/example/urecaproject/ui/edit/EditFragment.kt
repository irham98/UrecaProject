package com.example.urecaproject.ui.edit

import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.urecaproject.R
import com.example.urecaproject.factory.SubFilterFactory
import com.example.urecaproject.model.AdjustModel
import com.zomato.photofilters.imageprocessors.Filter
import com.zomato.photofilters.imageprocessors.SubFilter
import kotlinx.android.synthetic.main.fragment_edit.*
import kotlinx.coroutines.*
import org.opencv.core.Point
import java.io.File
import java.io.FileOutputStream
import java.util.*
import kotlin.math.roundToInt

typealias Coordinates = Pair<Point, Point>
class EditFragment : Fragment(), SliderFragment.EditImageListener, View.OnTouchListener {

    private val editViewModel: EditViewModel by lazy {
        ViewModelProvider(activity!!).get(EditViewModel::class.java)
    }

    private lateinit var imagePath : String
    private lateinit var internalPath : String
    private lateinit var origBm : Bitmap
    private var filteredBm : Bitmap? = null

    private var finalStr : String? = null
    private var finalFloat : Float = 0f

    private var allowCut : Boolean = false

    private val coordinates: Coordinates = Coordinates(Point(-1.0, -1.0), Point(-1.0, -1.0))

    companion object {
        const val IMAGE_SAVED = 1
    }

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

        cancel.setOnClickListener { endEdit(false) }

        apply.setOnClickListener { endEdit(true) }

        saveImage.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch { saveImage() }
            val bundle = Bundle()
            bundle.putInt("imageSaved", IMAGE_SAVED)
            Navigation.findNavController(view).navigate(R.id.nav_home, bundle)
        }

        chosenImage.setOnTouchListener (this)

        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.bottom_fragment) as NavHostFragment?
        val navController = navHostFragment!!.navController
        NavigationUI.setupWithNavController(bottomNav, navController)

        showImage()

        observeChanges()

    }

    private fun observeChanges() {
        editViewModel.filter.observe(viewLifecycleOwner, Observer { filter ->
            if (filter != null) {
                filteredBm = filter.processFilter(origBm.copy(Bitmap.Config.ARGB_8888, true))
                chosenImage.setImageBitmap(filteredBm)
                //TODO SOME ISSUES WITH ORIGBM
            }
        })

        editViewModel.visible.observe(viewLifecycleOwner, Observer { value ->
            bottomNav.visibility = value
        })

        editViewModel.cut.observe(viewLifecycleOwner, Observer { cut ->
            allowCut = cut
            if (cut)
                CoroutineScope(Dispatchers.IO).launch {  writeToExternalAppStorage(origBm) }
        })

        editViewModel.finish.observe(viewLifecycleOwner, Observer { finish ->
            if (finish)
                CoroutineScope(Dispatchers.Main).launch { (grabCut())}
        })
    }

    private suspend fun grabCut() {
        if (isTargetChosen()) {

            loadingGrabcut.visibility = View.VISIBLE

            val path = withContext(Dispatchers.Default) {
                editViewModel.extractForegroundFromBackground(coordinates, internalPath) }

            loadWithGlide(path)

            loadingGrabcut.visibility = View.GONE
        }
    }

    private fun loadWithGlide(path: String)  {
        Glide.with(this)
            .asBitmap()
            .load(path)
            .thumbnail(0.1f)
            .placeholder(R.drawable.ic_broken_image)
            .into(object : CustomTarget<Bitmap>(){
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    chosenImage.setImageBitmap(resource)
                    editViewModel.setBitmap(resource)
                    origBm = resource
                }
                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
    }

    private fun showImage() {
        val bundle: Bundle? = arguments

        if (bundle != null) {
            imagePath = bundle.getString("path")!!
            loadWithGlide(imagePath)
        }

    }

    private fun writeToExternalAppStorage(bm : Bitmap) {
        deleteFiles()

        val filename = "chosenimage" + generateRandomInt()
        val file = File(context!!.getExternalFilesDir(Environment.DIRECTORY_PICTURES), filename)

        //TODO update may be needed
        val bitmapCopy = bm.copy(Bitmap.Config.ARGB_8888, true)

        val outStream = FileOutputStream(file, false)
        bitmapCopy.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
        outStream.flush()
        outStream.close()

        internalPath = file.absolutePath
    }

    private fun deleteFiles() {
        val dir = context!!.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        for (i in dir!!.listFiles()!!) {
            Log.i("TAG", i.name)
            i.delete()
        }
    }

    //called when slider is moved
    override fun onSliderChanged(value: Float, string: String) {
        editImage(value, string)
    }

    //called when slider is first initialized
    override fun onSliderInit(model: AdjustModel?) {
        if (model != null) {
            finalFloat = model.start.toFloat() * model.multiplier
            finalStr = model.text
        }
        else {
            finalStr = "Filter"
            finalFloat = 255f
        }
    }

    private fun editImage(value: Float, string: String) : Bitmap {
        val myFilter = Filter()
        val subfilter : SubFilter = SubFilterFactory().getSubFilter(context, string, value, filteredBm)
        finalFloat = value
        finalStr = string
        myFilter.addSubFilter(subfilter)
        val bitmap = myFilter.processFilter(origBm.copy(Bitmap.Config.ARGB_8888, true))
        chosenImage.setImageBitmap(bitmap)

        return bitmap
    }

    private fun endEdit (apply : Boolean) {
        if (apply) {
            if (finalStr != null)
                origBm = editImage(finalFloat, finalStr!!)
        }
        editViewModel.setVisible(View.VISIBLE)
        chosenImage.setImageBitmap(origBm)
    }

    private fun saveImage() {
        val finalBitmap : Bitmap = origBm

        val resolver : ContentResolver = context!!.contentResolver
        val collection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)

        val n = generateRandomInt()
        val fname = "edited-image-$n.jpg"

        val image = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fname)
            put(MediaStore.Images.Media.IS_PENDING, 1)
            //store in a new file
            put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/Ureca")
        }

        val imageUri = resolver.insert(collection,image)

        resolver.openOutputStream(imageUri!!).use { stream ->
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        }

        image.clear()
        image.put(MediaStore.Images.Media.IS_PENDING, 0)
        resolver.update(imageUri, image, null, null)
    }

    private fun generateRandomInt(): Int {
        val generator = Random()
        var n = 10000
        n = generator.nextInt(n)
        return n
    }


    private fun getBitmapPositionInsideImageView(imageView: ImageView): FloatArray {
        val rect = FloatArray(6)

        if (imageView.drawable == null)
            return rect

        // Get image dimensions
        // Get image matrix values and place them in an array
        val f = FloatArray(9)
        imageView.imageMatrix.getValues(f)

        // Extract the scale values using the constants (if aspect ratio maintained, scaleX == scaleY)
        val scaleX = f[Matrix.MSCALE_X]
        val scaleY = f[Matrix.MSCALE_Y]

        rect[4] = scaleX
        rect[5] = scaleY

        // Get the drawable (could also get the bitmap behind the drawable and getWidth/getHeight)
        val d = imageView.drawable
        val origW = d.intrinsicWidth
        val origH = d.intrinsicHeight

        // Calculate the actual dimensions
        val actW = (origW * scaleX).roundToInt()
        val actH = (origH * scaleY).roundToInt()

        rect[2] = actW.toFloat()
        rect[3] = actH.toFloat()

        // Get image position
        // We assume that the image is centered into ImageView
        val imgViewW = imageView.width
        val imgViewH = imageView.height

        val left = (imgViewW - actW) / 2
        val top = (imgViewH - actH) / 2

        rect[0] = left.toFloat()
        rect[1] = top.toFloat()

        return rect
    }

    private fun resetTarget() {
        resetTargetCoordinates()
        chosenImage.setImageBitmap(origBm)
    }

    private fun resetTargetCoordinates() {
        coordinates.apply {
            first.apply { x = -1.0; y = -1.0 }
            second.apply { x = -1.0; y = -1.0 }
        }
    }


    private fun isTargetChosen() = coordinates.first.x != -1.0 && coordinates.first.y != -1.0 &&
            coordinates.second.x != -1.0 && coordinates.second.y != -1.0

    private fun hasChosenTopLeft() = coordinates.first.x != -1.0 && coordinates.first.y != -1.0
    private fun hasChosenBottomRight() = coordinates.second.x != -1.0 && coordinates.second.y != -1.0

    override fun onDestroyView() {
        super.onDestroyView()
        editViewModel.setVisible(View.VISIBLE)
        editViewModel.allowCut(false)
        editViewModel.setFilter(null)
    }

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        if (allowCut) {
            if (event.action == MotionEvent.ACTION_DOWN) {
                view.performClick()
                val bounds = getBitmapPositionInsideImageView(chosenImage)
                val xScaled = (event.x / bounds[4]) - bounds[0]
                val yScaled = (event.y / bounds[5]) - bounds[1]
                if (!hasChosenTopLeft()) {
                    coordinates.first.apply {
                        x = xScaled.toDouble()
                        y = yScaled.toDouble()
                    }
                } else if (!hasChosenBottomRight()) {
                    with(Bitmap.createBitmap(origBm.width, origBm.height, Bitmap.Config.RGB_565)) {
                        coordinates.second.apply {
                            x = xScaled.toDouble()
                            y = yScaled.toDouble()
                        }
                        val rectPaint = Paint().apply {
                            setARGB(255, 255, 0, 0)
                            style = Paint.Style.STROKE
                            strokeWidth = 3f
                        }
                        Canvas(this).apply {
                            drawBitmap(origBm, 0f, 0f, null)
                            drawRect(RectF(coordinates.first.x.toFloat(), coordinates.first.y.toFloat(), coordinates.second.x.toFloat(), coordinates.second.y.toFloat()), rectPaint)
                        }
                        chosenImage.setImageDrawable(BitmapDrawable(resources, this))
                    }

                } else {
                    resetTarget()
                }
            }
        }
        return true
    }

}