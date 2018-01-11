package com.androidproject.comarkdown.markdownedit.invite

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.androidproject.comarkdown.MainActivity
import com.androidproject.comarkdown.R
import com.trello.rxlifecycle2.components.support.RxFragment
import kotlinx.android.synthetic.main.fragment_invite.*
import kotlin.properties.Delegates

/**
 * Created by evan on 2018/1/11.
 */
class InviteFragment : RxFragment(), InviteContract.View{
    override var success: Boolean by Delegates.observable(false){property, oldValue, newValue ->
        if(newValue){
            Toast.makeText(activity,"邀请成功",Toast.LENGTH_SHORT).show()
            (activity as MainActivity).showMainFragment(MainActivity.FragmentType.EDIT)
        }else{
            Toast.makeText(activity,"邀请失败",Toast.LENGTH_SHORT).show()
        }
    }
    override lateinit var presenter: InviteContract.Presenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_invite, null)
        InvitePresenter(this)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        invite_btn.setOnClickListener {
            presenter.invite(invite_username.text.toString())
        }
    }

    override fun getViewContext(): Context {
        return context
    }
}