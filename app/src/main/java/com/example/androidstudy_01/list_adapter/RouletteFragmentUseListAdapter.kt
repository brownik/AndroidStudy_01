package com.example.androidstudy_01.list_adapter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.androidstudy_01.common.OptionData
import com.example.androidstudy_01.databinding.FragmentRouletteBinding
import java.text.DecimalFormat


class RouletteFragmentUseListAdapter : Fragment() {
    private lateinit var binding: FragmentRouletteBinding
    private lateinit var optionDataViewModel: OptionDataViewModel
    private val optionListAdapter: OptionListAdapter by lazy {
        OptionListAdapter { data, position ->
            onDeleteBtnClick(position)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentRouletteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvOptionList.adapter = optionListAdapter

        optionDataViewModel = ViewModelProvider(this).get(OptionDataViewModel::class.java)
        observeViewModel()
        addOnClickListener()
    }

    private fun addOnClickListener() = with(binding) {
        // X버튼
        btnClose.setOnClickListener {
            var fragmentManager = activity?.supportFragmentManager
            fragmentManager?.beginTransaction()?.remove(this@RouletteFragmentUseListAdapter)
                ?.commit()
            fragmentManager?.popBackStack()
        }

        // 체크박스
        cbSetCarat.setOnClickListener {
            if (cbSetCarat.isChecked) setCaratLayer.isVisible = true
            else setCaratLayer.isGone = true
        }

        // DecimalFormat
        etSetCarat.addTextChangedListener(CustomTextWatcher())

        // Option 추가
        btnAddOption.setOnClickListener {
            optionDataViewModel.addItem(OptionData(""))
        }

        // 완료 버튼
        btnComplete.setOnClickListener {
            val check1 = if (cbSetCarat.isChecked) {
                if (etSetCarat.text.isEmpty()) {
                    Toast.makeText(context, "금액을 입력하세요.", Toast.LENGTH_LONG).show()
                    false
                } else {
                    if (etSetCarat.text.toString().toInt() < 10) {
                        Toast.makeText(context, "10캐럿 이상 입력하세요.", Toast.LENGTH_LONG).show()
                        false
                    } else {
                        true
                    }
                }
            } else true
            var check2: Boolean = true
            optionDataViewModel.getList()?.forEach {
                if (it.optionContent.isEmpty()) {
                    Log.d("test", it.optionContent)
                    Toast.makeText(context, "옵션을 입력하세요.", Toast.LENGTH_SHORT).show()
                    check2 = false
                    return@forEach
                }
            }
            if (check1 && check2) Toast.makeText(context, "완료!", Toast.LENGTH_SHORT).show()
        }
    }

    inner class CustomTextWatcher() : TextWatcher {
        private var oldNum = ""
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (s.toString() != oldNum) {
                oldNum = binding.etSetCarat.text.toString()
                val newNum = setComma(oldNum.replace(",", "").toInt())
                binding.etSetCarat.setText(newNum)
            }
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    private fun setComma(number: Int): String {
        val dec = DecimalFormat("#,###")
        return dec.format(number)
    }

    private fun onDeleteBtnClick(position: Int) {
        optionDataViewModel.removeItem(position)
    }

    private fun observeViewModel(){
        optionDataViewModel.currentList.observe(viewLifecycleOwner) {
            Log.d("qwe123", "observeViewModel it.size: ${it.size}")
            val list = it
            optionListAdapter.submitList(list)
        }
    }
}