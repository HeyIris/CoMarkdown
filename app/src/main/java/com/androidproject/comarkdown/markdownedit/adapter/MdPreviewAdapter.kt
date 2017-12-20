package com.androidproject.comarkdown.markdownedit.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.androidproject.comarkdown.R
import com.androidproject.comarkdown.markdownedit.viewholder.PreviewViewHolder
import com.zzhoujay.richtext.CacheType
import com.zzhoujay.richtext.RichText
import com.zzhoujay.richtext.RichType

/**
 * Created by evan on 2017/12/13.
 */

object MdPreviewAdapter : RecyclerView.Adapter<PreviewViewHolder>() {
    var itemList: ArrayList<String>

    init {
        itemList = ArrayList()
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PreviewViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.preview_item, parent, false)
        val vh = PreviewViewHolder(view)
        return vh
    }

    override fun onBindViewHolder(holder: PreviewViewHolder, position: Int) {
        RichText.from(itemList[position]).type(RichType.markdown).showBorder(true)
                .cache(CacheType.all).into(holder.textView);
    }
}