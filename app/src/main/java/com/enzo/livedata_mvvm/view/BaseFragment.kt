package com.enzo.livedata_mvvm.view

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment


open class BaseFragment : Fragment() {

    fun nav(): NavController {
        return NavHostFragment.findNavController(this);
    }

}