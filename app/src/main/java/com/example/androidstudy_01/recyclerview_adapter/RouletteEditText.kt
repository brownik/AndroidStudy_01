package com.example.androidstudy_01.recyclerview_adapter

import android.content.Context
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class RouletteEditText : AppCompatEditText {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    private var mListeners: ArrayList<TextWatcher>? = null

    override fun addTextChangedListener(watcher: TextWatcher) {
        if (mListeners == null) mListeners = ArrayList()
        mListeners!!.add(watcher)
        super.addTextChangedListener(watcher)
    }

    fun clearTextChangedListeners() {
        mListeners?.let {
            for (watcher in it) removeTextChangedListener(watcher)
            it.clear()
        }
        mListeners = null
    }
}