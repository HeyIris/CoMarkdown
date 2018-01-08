package com.androidproject.comarkdown.account.register

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.androidproject.comarkdown.R
import kotlinx.android.synthetic.main.fragment_register.*

/**
 * Created by evan on 2018/1/8.
 */
class RegisterFragment: Fragment(),RegisterContract.View {
    override lateinit var presenter: RegisterContract.Presenter

    override var isActive: Boolean = false
        get() = isAdded

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_register, null)

        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        register_confirm.setOnClickListener { presenter.register(register_username.text.toString(), register_password.text.toString(), register_repeat_password.text.toString()) }
    }

    override fun getViewContext(): Context {
        return context
    }
}