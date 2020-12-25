package com.enzo.livedata_mvvm.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.enzo.livedata_mvvm.databinding.FragmentDetailBinding
import com.enzo.livedata_mvvm.imageCache.DoubleCache
import com.enzo.livedata_mvvm.imageCache.ImageLoader
import com.enzo.livedata_mvvm.imageCache.MD5Encoder
import com.enzo.livedata_mvvm.viewmodel.SharedViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DetailFragment : BaseFragment() {
    private var _binding: FragmentDetailBinding? = null
    //This property only valid between onCreateView and onDestoryView
    private val binding: FragmentDetailBinding get()= _binding!!
    private val sharedViewModel: SharedViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater,container,false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


       /* A LifecycleScope is defined for each Lifecycle object.
          Any coroutine launched in this scope is canceled when the Lifecycle is destroyed.
        */
        sharedViewModel.data.observe(viewLifecycleOwner,
                { photo ->
                    photo.url?.let { Log.e("here!!!!!!", it) };
                    binding.photoId.text = photo.id
                    binding.title.text = photo.title
                    photo.url?.let {
                        binding.imageView2.tag = MD5Encoder.encode(it)
                        viewLifecycleOwner.lifecycleScope.launch{
                            ImageLoader.displayImage(it,binding.imageView2, DoubleCache())
                        }
                    }
                })



        binding.root.setOnClickListener {
            nav().popBackStack()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}