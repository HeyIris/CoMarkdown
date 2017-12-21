package com.androidproject.comarkdown.markdownedit.adapter

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup

/**
 * Created by evan on 2017/12/21.
 */
class EditViewPagerAdapter : PagerAdapter() {
    val view_list:ArrayList<View>

    init {
        view_list = ArrayList<View>(2)
    }

    override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
        return view===`object`
    }

    override fun getCount(): Int {
        return view_list.size
    }

    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
        container?.removeView(view_list.get(position))
    }

    override fun instantiateItem(container: ViewGroup?, position: Int): Any {
        container?.addView(view_list.get(position))
        return view_list.get(position)
    }
}