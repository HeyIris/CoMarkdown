package com.androidproject.comarkdown.markdownedit.preview

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.androidproject.comarkdown.R
import com.androidproject.comarkdown.data.event.TextChangedEvent
import com.zzhoujay.richtext.CacheType
import com.zzhoujay.richtext.RichText
import com.zzhoujay.richtext.RichType
import kotlinx.android.synthetic.main.fragment_markdown_preview.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * Created by evan on 2018/1/7.
 */
class MdPreviewFragment : Fragment(), MdPreviewContract.View {
    override lateinit var presenter: MdPreviewContract.Presenter

    override var isActive: Boolean = false
        get() = isAdded

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_markdown_preview, null)
        EventBus.getDefault().register(this)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    }

    @Subscribe
    fun onEvent(textChangedEvent: TextChangedEvent) {
        RichText.from(textChangedEvent.newValue).type(RichType.markdown).showBorder(true)
                .cache(CacheType.all).into(preview_text)
    }
}