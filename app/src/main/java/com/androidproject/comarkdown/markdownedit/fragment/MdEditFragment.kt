package com.androidproject.comarkdown.markdownedit.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.androidproject.comarkdown.R
import com.androidproject.comarkdown.markdownedit.contract.MdEditContract
import kotlinx.android.synthetic.main.fragment_markdown_edit.*
import org.greenrobot.eventbus.EventBus
import java.io.File
import java.net.URI

/**
 * Created by evan on 2018/1/7.
 */
class MdEditFragment : Fragment(),MdEditContract.View {
    override lateinit var presenter: MdEditContract.Presenter

    override var isActive: Boolean = false
        get() = isAdded

    var preview:TextView? = null
    private lateinit var file:File

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_markdown_edit, null)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        val bundle = this.arguments
        file = File(URI(bundle.getString("data")))
        edit_text.text.append(file.readText())
        edit_text.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                EventBus.getDefault().post(p0)
                file.writeText(p0.toString())
            }
        })
    }

    override fun setTextChangedListener(textView: TextView?){
        preview = textView
    }
}