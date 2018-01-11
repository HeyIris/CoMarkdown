package com.androidproject.comarkdown.markdownedit

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.androidproject.comarkdown.R
import com.androidproject.comarkdown.markdownedit.edit.MdEditFragment
import kotlinx.android.synthetic.main.content_edit.*

/**
 * Created by evan on 2018/1/11.
 */
class EditFragment:Fragment(){
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.content_edit, null)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        edit_view_pager.adapter = EditViewPagerAdapter(childFragmentManager)
    }
}