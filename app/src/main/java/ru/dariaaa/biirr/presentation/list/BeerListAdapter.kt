package ru.dariaaa.biirr.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.dariaaa.biirr.data.cache.model.BeerModel
import ru.dariaaa.biirr.databinding.ItemBeerBinding
import ru.dariaaa.biirr.presentation.handlers.ImageLoader

class BeerListAdapter (
    private val imageLoader: ImageLoader
) : PagingDataAdapter<BeerModel, BeerListAdapter.ViewHolder>(Calculator()) {

    var onClickListener: IListener? = null

    interface IListener {
        fun onClick(id: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBeerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position) as? BeerModel
        holder.bind(item)
    }

    inner class ViewHolder(val binding: ItemBeerBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: BeerModel?) {
            item?.apply {
                binding.name.text = name
                binding.root.setOnClickListener {
                    onClickListener?.onClick(id)
                }
                binding.tagline.text = tagline

                picUrl?.let {
                    imageLoader.loadDrawable(binding.image, it)
                }
            }

        }
    }

    class Calculator : DiffUtil.ItemCallback<BeerModel>() {

        override fun areItemsTheSame(oldItem: BeerModel, newItem: BeerModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BeerModel, newItem: BeerModel): Boolean = oldItem == newItem
    }
}