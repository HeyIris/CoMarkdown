package com.androidproject.comarkdown.markdownedit.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.androidproject.comarkdown.R
import com.androidproject.comarkdown.markdownedit.viewholder.EditViewHolder

/**
 * Created by evan on 2017/12/13.
 */
object MdEditAdapter : RecyclerView.Adapter<EditViewHolder>() {
    var itemList: ArrayList<String>

    init {
        itemList = ArrayList()
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): EditViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_editview, parent, false)
        val vh = EditViewHolder(view)
        return vh
    }

    override fun onBindViewHolder(holder: EditViewHolder, position: Int) {
        holder.content = itemList[position]
        holder.index = position
    }
}