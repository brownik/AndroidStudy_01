package com.example.androidstudy_01.list_adapter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidstudy_01.common.OptionData

class OptionDataViewModel : ViewModel() {


    // Mutable 타입의 LiveData
    private val _currentList = MutableLiveData(mutableListOf(
        OptionData(""),
        OptionData(""),
    ))
    val currentList: LiveData<MutableList<OptionData>> = _currentList

    fun getList() = currentList.value

    fun addItem(data: OptionData){
        val list = _currentList.value!!
        list.add(data)
        _currentList.value = list
    }

    fun removeItem(position: Int){
        val list = _currentList.value!!
        list.removeAt(position)
        _currentList.value = list
    }
}