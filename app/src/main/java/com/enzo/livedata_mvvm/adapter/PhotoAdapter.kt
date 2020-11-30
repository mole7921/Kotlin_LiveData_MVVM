package com.enzo.livedata_mvvm.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.enzo.livedata_mvvm.databinding.ItemViewBinding
import com.enzo.livedata_mvvm.imageCache.DoubleCache
import com.enzo.livedata_mvvm.imageCache.ImageLoader
import com.enzo.livedata_mvvm.imageCache.MD5Encoder
import com.enzo.livedata_mvvm.model.Photo
import com.enzo.livedata_mvvm.viewmodel.PhotoViewModel


class PhotoAdapter(private val photoViewModel: PhotoViewModel) : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {
    var list: List<Photo>? = arrayListOf()


    fun setPhotos(photos: List<Photo>?) {
        list = photos
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photo = list!![position]
        holder.bind(photo,photoViewModel)
    }

    override fun getItemCount(): Int {
        return list?.count() ?: 0
    }


    class PhotoViewHolder private constructor(private val binding: ItemViewBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(photo: Photo, photoViewModel: PhotoViewModel) {
            binding.PhotoId.text = photo.id
            binding.PhotoTitle.text = photo.title
            photo.url?.let {
                binding.imageView.tag = MD5Encoder.encode(it)
                ImageLoader.displayImage(it,binding.imageView,DoubleCache()) }

            binding.itemPhoto.setOnClickListener {
                photoViewModel.isClick(photo)
            }
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


