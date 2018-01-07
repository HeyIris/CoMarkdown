package com.androidproject.comarkdown.markdownedit.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.androidproject.comarkdown.R
import com.androidproject.comarkdown.markdownedit.adapter.MdEditAdapter
import com.androidproject.comarkdown.markdownedit.contract.MdEditContract
import kotlinx.android.synthetic.main.fragment_markdown_edit.*

/**
 * Created by evan on 2018/1/7.
 */
class MdEditFragment : Fragment(),MdEditContract.View {
    override lateinit var presenter: MdEditContract.Presenter

    override var isActive: Boolean = false
        get() = isAdded

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_markdown_edit, null)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        edit_view_list.layoutManager = LinearLayoutManager(context)
        edit_view_list.adapter = MdEditAdapter
    }
}