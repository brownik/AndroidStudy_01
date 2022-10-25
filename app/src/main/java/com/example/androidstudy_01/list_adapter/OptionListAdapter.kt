package com.example.androidstudy_01.list_adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.androidstudy_01.common.OptionData
import com.example.androidstudy_01.R
import com.example.androidstudy_01.databinding.ListAdapterOptionBinding


class OptionListAdapter(
    private var onClick: (OptionData, Int) -> Unit,
): ListAdapter<OptionData, OptionListAdapter.OptionDataViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<OptionData>() {
            override fun areItemsTheSame(oldItem: OptionData, newItem: OptionData): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: OptionData, newItem: OptionData): Boolean {
                return oldItem == newItem
            }
        }
    }


    inner class OptionDataViewHolder(private val binding: ListAdapterOptionBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(optionData: OptionData) {
            binding.dataModel = optionData
            binding.tvOptionNum.text = "옵션 ${adapterPosition + 1}"
            binding.etSetOption.setText(optionData.optionContent)
            binding.btnDelete.setOnClickListener{
                onClick(optionData, adapterPosition)
            }
        }
    }

    override fun submitList(list: MutableList<OptionData>?) {
        Log.d("qwe123", "OptionListAdapter.submitList()::: currentList.size: ${currentList.size}")
        Log.d("qwe123", "OptionListAdapter.submitList()::: list.size: ${list?.size}")
        super.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionDataViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListAdapterOptionBinding = DataBindingUtil.inflate(layoutInflater,
            R.layout.list_adapter_option, parent, false)
        return OptionDataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OptionDataViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}