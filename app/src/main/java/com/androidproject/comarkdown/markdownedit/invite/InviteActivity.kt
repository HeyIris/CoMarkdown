package com.androidproject.comarkdown.markdownedit.invite

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import com.androidproject.comarkdown.R
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import kotlinx.android.synthetic.main.activity_invite.*
import kotlin.properties.Delegates

/**
 * Created by evan on 2018/1/11.
 */
class InviteActivity: RxAppCompatActivity(), InviteContract.View{
    override var success: Boolean by Delegates.observable(false){property, oldValue, newValue ->
        if(newValue){
            Toast.makeText(this,"邀请成功",Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this,"邀请失败",Toast.LENGTH_SHORT).show()
        }
    }
    override lateinit var presenter: InviteContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invite)

        InvitePresenter(this)

        invite_btn.setOnClickListener {
            presenter.invite(invite_username.text.toString())
        }
    }

    override fun getViewContext(): Context {
        return this
    }
}