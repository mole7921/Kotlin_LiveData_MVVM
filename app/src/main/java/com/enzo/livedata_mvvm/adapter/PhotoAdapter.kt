package com.enzo.livedata_mvvm.adapter



import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.enzo.livedata_mvvm.databinding.ItemViewBinding
import com.enzo.livedata_mvvm.imageCache.DoubleCache
import com.enzo.livedata_mvvm.imageCache.ImageLoader
import com.enzo.livedata_mvvm.imageCache.MD5Encoder
import com.enzo.livedata_mvvm.model.Photo
import com.enzo.livedata_mvvm.viewmodel.PhotoViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class PhotoAdapter(private val listener: OnItemClickListener,private val photoViewModel: PhotoViewModel) : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {
    var list: List<Photo> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemViewBinding.inflate(layoutInflater, parent, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photo = list[position]
        holder.bind(photo,photoViewModel)
    }

    override fun getItemCount(): Int {
        return list.let {
            list.size
        }
    }

    fun setPhotoList(photos:List<Photo>){
        val oldList = list
        val diffResult:DiffUtil.DiffResult = DiffUtil.calculateDiff(PhotoItemDiffCallback(oldList,photos))
        list = photos
        diffResult.dispatchUpdatesTo(this)
    }


    // DiffUtil是幫助我們在刷新RecyclerView時，計算新舊DATA的差異，
    // 並自動調用RecyclerView.Adapter的刷新方法，以完成高效刷新並伴有項目動畫的效果，
    // 避免RecyclerView無腦調用mAdapter.notifyDataSetChanged()
    class PhotoItemDiffCallback(var oldPhotoList:List<Photo>,var newPhotoList:List<Photo>,):DiffUtil.Callback(){
        override fun getOldListSize(): Int {
           return oldPhotoList.size
        }

        override fun getNewListSize(): Int {
            return newPhotoList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldPhotoList[oldItemPosition].id == newPhotoList[newItemPosition].id)
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return(oldPhotoList[oldItemPosition] == newPhotoList[newItemPosition])
        }

    }

    inner class PhotoViewHolder (private val binding: ItemViewBinding) :
            RecyclerView.ViewHolder(binding.root) {
        var job: Job? = null



        /* A ViewModelScope is defined for each ViewModel in your app.
           Any coroutine launched in this scope is automatically canceled if the ViewModel is cleared.
         */
        fun bind(photo: Photo, photoViewModel: PhotoViewModel) {
            job?.cancel()
            binding.PhotoId.text = photo.id
            binding.PhotoTitle.text = photo.title
            photo.url?.let {
                binding.imageView.tag = MD5Encoder.encode(it)
                job = photoViewModel.viewModelScope.launch{
                    ImageLoader.displayImage(it,binding.imageView, DoubleCache())
                } }

            binding.root.setOnClickListener {
                listener.onItemClick(photo)
            }

        }
    }

    interface OnItemClickListener {
        fun onItemClick(photo: Photo)
    }
}


