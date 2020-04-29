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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.urecaproject.R
import com.example.urecaproject.adapter.BaseAdapter
import com.example.urecaproject.adapter.GalleryRecyclerAdapter
import com.example.urecaproject.factory.GalleryViewModelFactory
import kotlinx.android.synthetic.main.fragment_gallery.*

class GalleryFragment : Fragment() {


    private val galleryViewModel: GalleryViewModel by lazy {
        val factory = GalleryViewModelFactory(context!!)
        ViewModelProvider(activity!!, factory).get(GalleryViewModel::class.java)
    }
    private var images : ArrayList<String> = ArrayList()
    private var videos : ArrayList<String> = ArrayList()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // load images
        galleryViewModel.getAllDocs()
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

/*        val listener = object : ImageListener {
            override fun onImageSelected(imagePath: String) {
                val bundle = Bundle()
                bundle.putString("path", imagePath)
                Navigation.findNavController(view).navigate(R.id.nav_edit, bundle)
            }
        }*/

        val imgAdapter = initRecyclerView(picturesRv, images)
        val vidAdapter = initRecyclerView(videosRv, videos)
        /*val imgAdapter = GalleryRecyclerAdapter(context!!, R.layout.gallery_item, images, listener)
        val vidAdapter = GalleryRecyclerAdapter(context!!, R.layout.gallery_item, videos, listener)
        val linearLayoutManager = LinearLayoutManager(context)
        val linearLayoutManager2 = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        linearLayoutManager2.orientation = LinearLayoutManager.HORIZONTAL

        picturesRv.adapter = imgAdapter
        picturesRv.layoutManager = linearLayoutManager
        picturesRv.setHasFixedSize(true)

        videosRv.adapter = vidAdapter
        videosRv.layoutManager = linearLayoutManager2
        videosRv.setHasFixedSize(true)
*/
        galleryViewModel.getImageList().observe(viewLifecycleOwner, Observer { listOfImage ->
            images = ArrayList(listOfImage)
            imgAdapter.setImages(images)

        })

        galleryViewModel.getVideosList().observe(viewLifecycleOwner, Observer { listOfVids ->
            videos = ArrayList(listOfVids)
            vidAdapter.setImages(videos)

        })

    }

    fun initRecyclerView(rv : RecyclerView, list : ArrayList<String>) : GalleryRecyclerAdapter {
        val mAdapter : GalleryRecyclerAdapter
        rv.apply {
            layoutManager = LinearLayoutManager(context).apply { orientation = LinearLayoutManager.HORIZONTAL }
            mAdapter = GalleryRecyclerAdapter(context!!, list, R.layout.gallery_item, object : BaseAdapter.ItemListener<String>{
                override fun onItemClicked(item: String) {
                    val bundle = Bundle()
                    bundle.putString("path", item)
                    Navigation.findNavController(view!!).navigate(R.id.nav_edit, bundle)
                }

            })
            adapter = mAdapter
            setHasFixedSize(true)
        }
        return mAdapter
    }


}
