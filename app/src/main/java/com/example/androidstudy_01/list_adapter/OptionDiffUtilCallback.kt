package com.example.androidstudy_01.list_adapter

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import com.example.androidstudy_01.common.OptionData

object OptionDiffUtilCallback : DiffUtil.ItemCallback<OptionData>() {

    override fun areItemsTheSame(
        oldItem: OptionData,
        newItem: OptionData
    ): Boolean {
        Log.d("qwe123", "areItemsTheSame: ${oldItem == newItem}")
        return oldItem === newItem
    }

    override fun areContentsTheSame(
        oldItem: OptionData,
        newItem: OptionData
    ): Boolean {
        Log.d("qwe123", "areContentsTheSame: ${oldItem == newItem}")
        return oldItem == newItem
    }
}