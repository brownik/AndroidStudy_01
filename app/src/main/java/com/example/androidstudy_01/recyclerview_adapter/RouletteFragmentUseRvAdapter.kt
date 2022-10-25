package com.example.androidstudy_01.recyclerview_adapter

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
import com.example.androidstudy_01.common.OptionData
import com.example.androidstudy_01.databinding.FragmentRouletteBinding
import java.text.DecimalFormat

class RouletteFragmentUseRvAdapter : Fragment() {

    private lateinit var binding: FragmentRouletteBinding

    private lateinit var optionRvAdapter: OptionRvAdapter

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

        optionRvAdapter = OptionRvAdapter { data, position ->
            onDeleteBtnClick(data, position)
        }

        binding.rvOptionList.adapter = optionRvAdapter
        optionRvAdapter.setItem(mutableListOf<OptionData>(
            OptionData(""),
            OptionData(""),
        ))
        addOnClickListener()
    }

    private fun addOnClickListener() = with(binding) {
        // X버튼
        btnClose.setOnClickListener {
            var fragmentManager = activity?.supportFragmentManager
            fragmentManager?.beginTransaction()?.remove(this@RouletteFragmentUseRvAdapter)?.commit()
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
            optionRvAdapter.addItem(OptionData(""))
            Log.d("text", "size: ${optionRvAdapter.getItem().size}")
        }

        // 완료 버튼
        btnComplete.setOnClickListener {
            val optionList = optionRvAdapter.getItem()
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
            optionList.forEach {
                if (it.optionContent.isEmpty()) {
                    Toast.makeText(context, "옵션을 입력하세요.", Toast.LENGTH_LONG).show()
                    check2 = false
                    return@forEach
                }
            }
            if (check1 && check2) Toast.makeText(context, "완료!", Toast.LENGTH_LONG).show()
        }
    }

    // Option 삭제
    private fun onDeleteBtnClick(data: OptionData, position: Int) {
        val listSize = optionRvAdapter.getItem().size
        if (listSize > 2)
            optionRvAdapter.deleteItem(position)
        else
            Toast.makeText(context, "옵션은 최소 두 개입니다.", Toast.LENGTH_LONG).show()

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
}