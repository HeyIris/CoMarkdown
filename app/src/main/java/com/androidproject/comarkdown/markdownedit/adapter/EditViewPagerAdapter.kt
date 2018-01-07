package com.androidproject.comarkdown.markdownedit.adapter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.androidproject.comarkdown.markdownedit.contract.MdEditContract
import com.androidproject.comarkdown.markdownedit.contract.MdPreviewContract
import com.androidproject.comarkdown.markdownedit.fragment.MdEditFragment
import com.androidproject.comarkdown.markdownedit.fragment.MdPreviewFragment
import com.androidproject.comarkdown.markdownedit.presenter.MdEditPresenter
import com.androidproject.comarkdown.markdownedit.presenter.MdPreviewPresenter

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