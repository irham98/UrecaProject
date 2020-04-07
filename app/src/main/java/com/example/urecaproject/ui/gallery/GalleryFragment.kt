package com.example.urecaproject.ui.gallery

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.urecaproject.R
import com.example.urecaproject.adapter.GalleryRecyclerAdapter
import com.example.urecaproject.adapter.GalleryRecyclerAdapter.ImageListener
import com.example.urecaproject.factory.GalleryViewModelFactory
import kotlinx.android.synthetic.main.fragment_gallery.*

class GalleryFragment : Fragment() {


    private val galleryViewModel: GalleryViewModel by lazy {
        val factory = GalleryViewModelFactory(context!!)
        ViewModelProvider(this, factory).get(GalleryViewModel::class.java)
    }
    private var images : ArrayList<String> = ArrayList()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // load images
        galleryViewModel.getAllImages()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listener = object : ImageListener {
            override fun onImageSelected(imagePath: String) {
                val bundle = Bundle()
                bundle.putString("path", imagePath)
                Navigation.findNavController(view).navigate(R.id.nav_edit, bundle)
            }
        }
        val adapter = GalleryRecyclerAdapter(context!!, R.layout.gallery_item, images, listener)
        galleryRv.adapter = adapter
        galleryRv.layoutManager = GridLayoutManager(context, 3)

        galleryViewModel.getImageList().observe(viewLifecycleOwner, Observer { listOfImage ->
            images = ArrayList(listOfImage)
            adapter.setImages(images)

        })

    }


}
