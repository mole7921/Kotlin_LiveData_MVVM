package com.enzo.livedata_mvvm.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.enzo.livedata_mvvm.R
import com.enzo.livedata_mvvm.databinding.FragmentMainBinding



class MainFragment : BaseFragment() {
    private var _binding: FragmentMainBinding? = null
    //This property only valid between onCreateView and onDestoryView
    private val binding: FragmentMainBinding get()= _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.button.setOnClickListener {
            nav().navigate(R.id.action_mainFragment_to_photoFragment)
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}