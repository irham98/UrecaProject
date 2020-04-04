package com.example.urecaproject.ui.edit

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.urecaproject.R
import com.example.urecaproject.model.AdjustModel
import kotlinx.android.synthetic.main.fragment_edit.*

class EditFragment : Fragment() {

    var adjust : AdjustModel = AdjustModel(1, "wow")

    private var mImage : ImageView? = null

    companion object {
        const val REQUEST_OPEN_IMAGE = 1
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        /*if (mImage == null) {
            requestImage()
            //TODO put this in a controller/viewmodel, dialog
        }*/
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



        val navHostFragment = childFragmentManager.findFragmentById(R.id.bottom_fragment) as NavHostFragment?
        val navController = navHostFragment!!.navController
        NavigationUI.setupWithNavController(bottomNav, navController)
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

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == REQUEST_OPEN_IMAGE && resultCode == RESULT_OK) {
            if (data != null) {
/*                val imgUri = data.data
                val source = ImageDecoder.createSource(activity!!.getContentResolver(), imgUri!!)
                mBitmap = ImageDecoder.decodeBitmap(source)
                mImageView.setImageBitmap(mBitmap)*/
            }
        }
    }

}
