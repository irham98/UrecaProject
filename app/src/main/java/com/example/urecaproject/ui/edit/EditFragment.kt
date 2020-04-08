package com.example.urecaproject.ui.edit

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
import kotlinx.android.synthetic.main.fragment_edit.*
import kotlinx.android.synthetic.main.fragment_edit.view.*

class EditFragment : Fragment(), SliderFragment.EditImageListener {

    private val editViewModel: EditViewModel by lazy {
        ViewModelProvider(activity!!).get(EditViewModel::class.java)
    }

    private lateinit var imagePath : String
    private lateinit var bm : Bitmap

    companion object {
        const val REQUEST_OPEN_IMAGE = 1
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
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.bottom_fragment) as NavHostFragment?
        val navController = navHostFragment!!.navController
        NavigationUI.setupWithNavController(bottomNav, navController)

        showImage(view)
    }



    fun requestImage() {
        val getPictureIntent = Intent(Intent.ACTION_GET_CONTENT)
        getPictureIntent.type = "image/*"
        val pickPictureIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        val chooserIntent = Intent.createChooser(getPictureIntent, "Select Image")
        chooserIntent.putExtra(
            Intent.EXTRA_INITIAL_INTENTS, arrayOf(
                pickPictureIntent
            )
        )
        startActivityForResult(chooserIntent, REQUEST_OPEN_IMAGE)
    }

    private fun showImage(view: View) {
        val bundle: Bundle? = arguments
        if (bundle != null) {
            imagePath = bundle.getString("path")!!
            Glide.with(context!!)
                .load(Uri.decode(imagePath))
                .listener(object : RequestListener<Drawable> {
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
                        bm = image.bitmap
                        editViewModel.setBitmap(bm)
                        return false
                    }

                })
                .apply(RequestOptions().placeholder(R.drawable.ic_broken_image).centerCrop())
                .into(view.chosenImage)
        }

    }

    override fun onSliderChanged(value: Float, string: String) {
        if (string.equals("Brightness")){
            var myFilter = Filter()
            myFilter.addSubFilter(BrightnessSubFilter(value.toInt()))
            chosenImage.setImageBitmap(myFilter.processFilter(bm.copy(Bitmap.Config.ARGB_8888, true)))
        }
        else if (string.equals("Saturation")){
            var myFilter = Filter()
            myFilter.addSubFilter(SaturationSubFilter(value))
            chosenImage.setImageBitmap(myFilter.processFilter(bm.copy(Bitmap.Config.ARGB_8888, true)))
        }
        else if (string.equals("Contrast")){
            var myFilter = Filter()
            myFilter.addSubFilter(ContrastSubFilter(value))
            chosenImage.setImageBitmap(myFilter.processFilter(bm.copy(Bitmap.Config.ARGB_8888, true)))
        }
    }

    override fun onEditStarted() {
        //TODO("Not yet implemented")
    }

    override fun onEditCompleted() {
        //TODO("Not yet implemented")
    }


/*    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == REQUEST_OPEN_IMAGE && resultCode == RESULT_OK) {
            if (data != null) {
                val imgUri = data.data
                val source = ImageDecoder.createSource(activity!!.getContentResolver(), imgUri!!)
                mBitmap = ImageDecoder.decodeBitmap(source)
                mImageView.setImageBitmap(mBitmap)
            }
        }
    }*/


}