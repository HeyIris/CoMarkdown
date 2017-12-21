package com.androidproject.comarkdown.markdownedit.viewholder

import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import kotlinx.android.synthetic.main.item_editview.view.*
import kotlin.properties.Delegates

/**
 * Created by evan on 2017/12/13.
 */
class EditViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    var content: String by Delegates.observable("") { property, oldValue, newValue ->
        editView.setText(newValue)
    }
    private val editView: EditText
    var index: Int

    init {
        editView = view.edit_view
        index = -1
    }

    public fun setPosition(position: Int) {
        index = position
        if(index!=-1){
            editView.addTextChangedListener(object :TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                }
            })
        }
    }
}