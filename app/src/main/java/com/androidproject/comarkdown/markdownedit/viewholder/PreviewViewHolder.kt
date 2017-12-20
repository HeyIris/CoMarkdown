package com.androidproject.comarkdown.markdownedit.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.preview_item.view.*

/**
 * Created by evan on 2017/12/13.
 */
class PreviewViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val textView: TextView

    init {
        textView = view.text_view
    }
}