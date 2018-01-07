package com.androidproject.comarkdown.markdownedit.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.androidproject.comarkdown.R
import com.androidproject.comarkdown.markdownedit.adapter.MdPreviewAdapter
import com.androidproject.comarkdown.markdownedit.contract.MdPreviewContract
import kotlinx.android.synthetic.main.fragment_markdown_preview.*

/**
 * Created by evan on 2018/1/7.
 */
class MdPreviewFragment : Fragment(), MdPreviewContract.View{
    override lateinit var presenter: MdPreviewContract.Presenter

    override var isActive: Boolean = false
        get() = isAdded

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_markdown_preview, null)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        preview_view_list.layoutManager = LinearLayoutManager(context)
        preview_view_list.adapter = MdPreviewAdapter
    }
}