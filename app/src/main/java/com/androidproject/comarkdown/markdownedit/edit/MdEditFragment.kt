package com.androidproject.comarkdown.markdownedit.edit

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.androidproject.comarkdown.R
import com.androidproject.comarkdown.data.AccountInfo
import com.androidproject.comarkdown.data.event.LoadFileEvent
import com.androidproject.comarkdown.data.event.OpenFileEvent
import com.androidproject.comarkdown.data.event.TextChangedEvent
import com.androidproject.comarkdown.ot.DiffMatchPatch
import com.androidproject.comarkdown.ot.OTClient
import kotlinx.android.synthetic.main.fragment_markdown_edit.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.io.File
import java.net.URI
import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates

/**
 * Created by evan on 2018/1/7.
 */
class MdEditFragment : Fragment(), MdEditContract.View {
    override lateinit var presenter: MdEditContract.Presenter
    private val timer = Timer(true)
    override lateinit var opList: ArrayList<String>
    override lateinit var otClient: OTClient

    override var filePath: String by Delegates.observable("") { property, oldValue, newValue ->
        if (newValue != "") {
            AccountInfo.filepath = newValue
            file = File(URI(newValue))
            edit_text.setText(file.readText())
            if(oldValue != ""){
                otClient.exitServer()
            }
            otClient = OTClient(0, file.name, edit_text.text.toString(), context)

        }
    }

    override var isActive: Boolean = false
        get() = isAdded

    var preview:TextView? = null
    private lateinit var file:File

    override fun onResume() {
        super.onResume()
        presenter.start()
        EventBus.getDefault().register(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_markdown_edit, null)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        edit_text.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                EventBus.getDefault().post(TextChangedEvent(p0.toString(), p0.toString()))
            }
        })
        val timerTask = object :TimerTask() {
            override fun run() {
                if(filePath != ""){
                    val diff = DiffMatchPatch()
                    val lists = diff.diff_main(otClient.file, edit_text.text.toString())
                    if (lists.size == 1 && lists[0].operation == DiffMatchPatch.Operation.EQUAL) {
                    } else {
                        opList = ArrayList()
                        for (item in lists) {
                            opList.add(item.toString())
                        }
                        otClient.applyOperation(opList)
                        otClient.applyClient(opList)
                        file.writeText(otClient.file)
                    }
                }
            }
        }
        timer.schedule(timerTask,5000,5000)
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
        if (filePath != ""){
            otClient.exitServer()
        }
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun onOpenFileEvent(event:OpenFileEvent) {
        filePath = event.filePath
    }

    @Subscribe
    fun onLoadFileEvent(event: LoadFileEvent){
        AccountInfo.file.name = event.fileName
        AccountInfo.file.master = event.master
        filePath = event.filePath
    }

    override fun setTextChangedListener(textView: TextView?){
        preview = textView
    }

    override fun getViewContext(): Context {
        return context
    }
}