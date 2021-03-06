package com.enzo.livedata_mvvm.view



import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.enzo.livedata_mvvm.R
import com.enzo.livedata_mvvm.adapter.PhotoAdapter
import com.enzo.livedata_mvvm.databinding.FragmentPhotoBinding
import com.enzo.livedata_mvvm.model.Photo
import com.enzo.livedata_mvvm.retrofit.Status
import com.enzo.livedata_mvvm.viewmodel.SharedViewModel
import com.enzo.livedata_mvvm.viewmodel.PhotoViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PhotoFragment : BaseFragment(),PhotoAdapter.OnItemClickListener {

    private var _binding:FragmentPhotoBinding? = null
    //This property only valid between onCreateView and onDestroyView
    private val binding:FragmentPhotoBinding  get()= _binding!!
    private val photoViewModel:PhotoViewModel by viewModels()
    private val sharedViewModel:SharedViewModel by activityViewModels()
    private val adapter by lazy {
        PhotoAdapter(this,photoViewModel)
    }


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

        binding.apply {
            binding.recycleView.layoutManager = GridLayoutManager(activity, 4)
            binding.recycleView.adapter = adapter
        }


        /* ViewLifecycleOwner :
        * Fragment 的 View 會跟著其中的 onCreateView 與 onDestroyView 產生與回收，
        * 但是 Lifecycle Owner 的週期卻是跟著 Activity 的，
        * 當我們重新連接 Fragment 時，原先的 LiveData 會持有一個新的 Observer，
        * 但 LiveData 不會刪除舊的 Observer，這樣就會造成越來越多的 Observer 在活動狀態，
        * 導致 Memory Leak 的發生。
        * ViewLifecycleOwner 取得的 Lifecycle Owner 和一般的不一樣，不是依照 Fragment 的週期，
        * 而是Fragment View的週期。
        */

        photoViewModel.dataList.observe(viewLifecycleOwner,
                {
                    when (it.status) {
                       is Status.Success -> {
                            showProgressBar(false)
                            it.data?.let { photoList -> adapter.setPhotoList(photoList) }
                        }
                        is Status.Error -> {
                            showProgressBar(true)
                            Toast.makeText(activity,it.message!!,Toast.LENGTH_LONG).show()
                        }
                    }
                })
    }



    private fun showProgressBar(visible:Boolean){
        when(visible){
            true -> binding.progressBar.visibility = View.VISIBLE
            false -> binding.progressBar.visibility = View.INVISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.recycleView.adapter = null
        _binding = null
    }

    override fun onItemClick(photo: Photo) {
        sharedViewModel.storage(photo)
        nav().navigate(R.id.action_photoFragment_to_detailFragment)
    }

}