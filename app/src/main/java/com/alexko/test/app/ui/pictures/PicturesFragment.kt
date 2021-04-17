package com.alexko.test.app.ui.pictures

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexko.test.app.R
import com.alexko.test.app.databinding.FragmentPicturesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PicturesFragment : Fragment(R.layout.fragment_pictures), PicturesListAdapted.Callback {

    private val picturesViewModel: PicturesViewModel by viewModels()
    private val listAdapted = PicturesListAdapted(this)
    private lateinit var binding: FragmentPicturesBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPicturesBinding.bind(view).apply {
            recyclerView.apply {
                isGone = true
                adapter = listAdapted
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
            }
            progress.isGone = false
        }
        picturesViewModel.pictures.observe(viewLifecycleOwner) {
            binding.apply {
                recyclerView.isGone = false
                progress.isGone = true
            }
            listAdapted.submit(it)
        }

        requestMore()
    }

    override fun requestMore() {
        picturesViewModel.requestPictures()
    }
}