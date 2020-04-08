package com.example.urecaproject.ui.edit

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.urecaproject.FilterPack
import com.example.urecaproject.R
import com.example.urecaproject.adapter.ThumbnailRecyclerAdapter
import com.example.urecaproject.model.FilterModel
import kotlinx.android.synthetic.main.fragment_filter.*

class FilterFragment : Fragment() {

    private lateinit var bm : Bitmap
    private lateinit var adapter : ThumbnailRecyclerAdapter
    private val thumbnails : ArrayList<Bitmap> = ArrayList()

    private val editViewModel: EditViewModel by lazy {
        ViewModelProvider(activity!!).get(EditViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Log.i("TAG", bm.toString())

        //get image from EditFragment
        editViewModel.bitmap.observe(viewLifecycleOwner, Observer { bitmap ->
            bm = bitmap
            bindDataToAdapter()
        })

        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        filterRv.layoutManager = linearLayoutManager
        adapter = ThumbnailRecyclerAdapter(context!!, R.layout.thumbnail_item, thumbnails, object :
            ThumbnailRecyclerAdapter.ImageListener {
            override fun onImageSelected(imageBitmap: Bitmap) {
                //change the image on EditFragment
                Log.i("TAG", "this thumbnail was clicked")

            }
        })
        filterRv.adapter = adapter

    }

    fun bindDataToAdapter() {
        val handler = Handler()
        val run : Runnable = object : Runnable {
            override fun run() {
                applyFilterForThumbnail()
            }
        }
        handler.post(run)
    }

    fun applyFilterForThumbnail () {
        val filters: ArrayList<FilterModel> = FilterPack.getFilterPack(context!!)
        val size : Int = context!!.resources.getDimension(R.dimen.thumbnail_size).toInt()
        thumbnails.clear()
        for (filter in filters) {
            //process the thumbnail and add that to a list of bitmap
            val small = Bitmap.createScaledBitmap(bm, size, size, false)
            val processedThumbnail = filter.processFilter(small)
            thumbnails.add(processedThumbnail)
        }
        adapter.setImages(thumbnails)
    }
}