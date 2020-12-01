package com.enzo.livedata_mvvm.adapter



import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.enzo.livedata_mvvm.databinding.ItemViewBinding
import com.enzo.livedata_mvvm.imageCache.DoubleCache
import com.enzo.livedata_mvvm.imageCache.ImageLoader
import com.enzo.livedata_mvvm.imageCache.MD5Encoder
import com.enzo.livedata_mvvm.model.Photo
import com.enzo.livedata_mvvm.viewmodel.PhotoViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class PhotoAdapter(private val photoViewModel: PhotoViewModel,private val viewLifecycleOwner: LifecycleOwner) : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {
    var list: List<Photo>? = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photo = list!![position]
        holder.bind(photo,viewLifecycleOwner)

        holder.itemView.setOnClickListener {
            photoViewModel.isClick(photo)
        }
    }

    override fun getItemCount(): Int {
        list = photoViewModel.dataList.value?.data
        return list?.count() ?: 0
    }


    class PhotoViewHolder private constructor(private val binding: ItemViewBinding) :
            RecyclerView.ViewHolder(binding.root) {
        var job: Job? = null

        fun bind(photo: Photo,viewLifecycleOwner: LifecycleOwner) {
            job?.cancel()
            binding.PhotoId.text = photo.id
            binding.PhotoTitle.text = photo.title
            photo.url?.let {
                binding.imageView.tag = MD5Encoder.encode(it)
                job = viewLifecycleOwner.lifecycleScope.launch{
                    ImageLoader.displayImage(it,binding.imageView, DoubleCache())
                } }

        }



        companion object {
            fun from(parent: ViewGroup): PhotoViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemViewBinding.inflate(layoutInflater, parent, false)

                return PhotoViewHolder(binding)
            }
        }
    }
}


