package com.enzo.livedata_mvvm.view



import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.enzo.livedata_mvvm.R
import com.enzo.livedata_mvvm.adapter.PhotoAdapter
import com.enzo.livedata_mvvm.databinding.FragmentPhotoBinding
import com.enzo.livedata_mvvm.viewmodel.SharedViewModel
import com.enzo.livedata_mvvm.viewmodel.PhotoViewModel



class PhotoFragment : Fragment() {

    private var _binding:FragmentPhotoBinding? = null
    //This property only valid between onCreateView and onDestoryView
    private val binding:FragmentPhotoBinding  get()= _binding!!
    private val photoViewModel:PhotoViewModel by viewModels()
    private val sharedViewModel:SharedViewModel by activityViewModels()
    private lateinit var adapter: PhotoAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhotoBinding.inflate(inflater,container,false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recycleView.layoutManager = GridLayoutManager(activity, 4)
        adapter = PhotoAdapter(photoViewModel)
        binding.recycleView.adapter = adapter

        /* ViewLifecycleOwner :
        * Fragment 的 View 會跟著其中的 onCreatView 與 onDestroyView 產生與回收，
        * 但是 Lifecycle Owner 的週期卻是跟著 Activity 的，
        * 當我們重新連接 Fragment 時，原先的 LiveData 會持有一個新的 Observer，
        * 但 LiveData 不會刪除舊的 Observer，這樣就會造成越來越多的 Observer 在活動狀態，
        * 導致 Memory Leak 的發生。
        * ViewLifecycleOwner 取得的 Lifecycle Owner 和一般的不一樣，不是依照 Fragment 的週期，
        * 而是Fragment View的週期。
        */

        photoViewModel.dataList.observe(viewLifecycleOwner,
                {
                    adapter.setPhotos(it)
                })

        photoViewModel.clickItem.observe(viewLifecycleOwner,{
            sharedViewModel.select(it)
            findNavController().navigate(R.id.action_photoFragment_to_detailFragment)
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}