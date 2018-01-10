package com.androidproject.comarkdown.markdownedit

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.androidproject.comarkdown.markdownedit.edit.MdEditContract
import com.androidproject.comarkdown.markdownedit.preview.MdPreviewContract
import com.androidproject.comarkdown.markdownedit.edit.MdEditFragment
import com.androidproject.comarkdown.markdownedit.preview.MdPreviewFragment
import com.androidproject.comarkdown.markdownedit.edit.MdEditPresenter
import com.androidproject.comarkdown.markdownedit.preview.MdPreviewPresenter

/**
 * Created by evan on 2017/12/21.
 */
class EditViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    val view_list: ArrayList<Fragment>

    init {
        view_list = ArrayList<Fragment>()
        view_list.add(MdEditFragment())
        MdEditPresenter(view_list[0] as MdEditContract.View)
        view_list.add(MdPreviewFragment())
        MdPreviewPresenter(view_list[1] as MdPreviewContract.View)
    }

    override fun getCount() = view_list.size

    override fun getItem(position: Int) = view_list.get(position)
}