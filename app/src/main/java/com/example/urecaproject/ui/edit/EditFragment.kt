package com.example.urecaproject.ui.edit

import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.urecaproject.R
import com.example.urecaproject.factory.SubFilterFactory
import com.zomato.photofilters.imageprocessors.Filter
import com.zomato.photofilters.imageprocessors.SubFilter
import kotlinx.android.synthetic.main.fragment_edit.*
import kotlinx.android.synthetic.main.fragment_edit.view.*
import java.util.*

class EditFragment : Fragment(), SliderFragment.EditImageListener {

    private val editViewModel: EditViewModel by lazy {
        ViewModelProvider(activity!!).get(EditViewModel::class.java)
    }

    private lateinit var imagePath : String
    private lateinit var origBm : Bitmap
    private lateinit var filteredBm : Bitmap

    private lateinit var finalStr : String
    private var finalFloat : Float = 0f

    companion object {
        val IMAGE_SAVED = 1
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

        cancel.setOnClickListener(object: View.OnClickListener {
            override fun onClick(p0: View?) {
                endEdit(false)
            }
        })

        apply.setOnClickListener(object: View.OnClickListener {
            override fun onClick(p0: View?) {
                endEdit(true)
            }
        })

        saveImage.setOnClickListener(object: View.OnClickListener {
            override fun onClick(p0: View?) {
                saveImage()
                val bundle = Bundle()
                bundle.putInt("imageSaved", IMAGE_SAVED)
                Navigation.findNavController(view).navigate(R.id.nav_home, bundle)
            }
        })

        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.bottom_fragment) as NavHostFragment?
        val navController = navHostFragment!!.navController
        NavigationUI.setupWithNavController(bottomNav, navController)

        showImage(view)

        editViewModel.filteredBitmap.observe(viewLifecycleOwner, Observer { bitmap ->
            filteredBm = bitmap
            chosenImage.setImageBitmap(filteredBm)
        })

        editViewModel.visible.observe(viewLifecycleOwner, Observer { value ->
            bottomNav.visibility = value
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

    fun toggleImageFilter (value: Int): Bitmap {

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
        editImage(value, string)
    }

    fun editImage(value: Float, string: String) : Bitmap {
        val myFilter = Filter()
        val factory = SubFilterFactory()
        val subfilter : SubFilter = factory.getSubFilter(context!!, string, value)
        var bitmap : Bitmap
        finalFloat = value
        finalStr = string
        if (string.equals("Filter")) {
            bitmap = toggleImageFilter(value.toInt())
            chosenImage.setImageBitmap(bitmap)
        }
        else {
            myFilter.addSubFilter(subfilter)
            bitmap = myFilter.processFilter(origBm.copy(Bitmap.Config.ARGB_8888, true))
            chosenImage.setImageBitmap(bitmap)
        }
        return bitmap
    }

    override fun onEditStarted() {
        //TODO("Not yet implemented")
    }

    override fun onEditCompleted() {
        //TODO("Not yet implemented")
    }

    fun endEdit (apply : Boolean) {
        if (apply) {
            origBm = editImage(finalFloat, finalStr)
        }
        editViewModel.setVisible(View.VISIBLE)
        chosenImage.setImageBitmap(origBm)
    }

    private fun saveImage() {
        val finalBitmap : Bitmap = origBm

        val resolver : ContentResolver = context!!.contentResolver
        val collection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)

        val name : String = generateRandomString()

        val image = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, name)
            put(MediaStore.Images.Media.IS_PENDING, 1)
        }

        val imageUri = resolver.insert(collection,image)

        resolver.openOutputStream(imageUri!!).use { stream ->
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream)
        }

        image.clear();
        image.put(MediaStore.Images.Media.IS_PENDING, 0);
        resolver.update(imageUri, image, null, null);

    }

    private fun generateRandomString(): String {
        val generator = Random()
        var n = 10000
        n = generator.nextInt(n)
        val fname = "edited-image-$n.jpg"
        return fname
    }

    override fun onDestroyView() {
        super.onDestroyView()
        editViewModel.setVisible(View.VISIBLE)
    }

}