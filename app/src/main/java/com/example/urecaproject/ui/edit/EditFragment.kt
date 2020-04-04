package com.example.urecaproject.ui.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.urecaproject.R
import kotlinx.android.synthetic.main.fragment_edit.*

class EditFragment : Fragment() {

    //private lateinit var editViewModel: EditViewModel


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
/*        editViewModel =
                ViewModelProviders.of(this).get(EditViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_edit, container, false)
        val textView: TextView = root.findViewById(R.id.text_slideshow)
        editViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val navHostFragment = childFragmentManager.findFragmentById(R.id.bottom_fragment) as NavHostFragment?
        val navController = navHostFragment!!.navController
        NavigationUI.setupWithNavController(bottomNav, navController)
    }
}
