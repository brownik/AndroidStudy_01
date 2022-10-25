package com.example.androidstudy_01.recyclerview_adapter

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidstudy_01.common.OptionData
import com.example.androidstudy_01.R
import com.example.androidstudy_01.databinding.RecyclerviewAdapterOptionBinding

class OptionRvAdapter(
    private var onClick: (OptionData, Int) -> Unit,
) : RecyclerView.Adapter<OptionRvAdapter.ViewHolder>() {

    private var dataList: MutableList<OptionData> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerviewAdapterOptionBinding.bind(
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_adapter_option, parent, false)
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = dataList.size

    // 최초 item 설정
    @SuppressLint("NotifyDataSetChanged")
    fun setItem(dataList: MutableList<OptionData>) {
        this.dataList = dataList.toMutableList()
        notifyDataSetChanged()

    }

    // item 추가
    @SuppressLint("NotifyDataSetChanged")
    fun addItem(data: OptionData) {
        dataList.add(data)
        notifyItemChanged(dataList.size)
    }

    // item 삭제
    @SuppressLint("NotifyDataSetChanged")
    fun deleteItem(position: Int) {
        dataList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, dataList.size)
    }

    // item 출력
    fun getItem() = this.dataList

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList[position], position)
    }

    inner class ViewHolder(private val binding: RecyclerviewAdapterOptionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: OptionData, position: Int) = with(binding) {
            tvOptionNum.text = "옵션 ${position + 1}"
            etSetOption.setText(item.optionContent)
            btnDelete.setOnClickListener {
                onClick(item, position)
            }
            etSetOption.addTextChangedListener(CustomEditTextListener(position))
        }
    }

    inner class CustomEditTextListener(private var position: Int) : TextWatcher {
        private var text = ""
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            text = s.toString()
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (s.toString() != text) {
                dataList[position].optionContent = s.toString()
            }
        }
        override fun afterTextChanged(s: Editable?) {}
    }
}