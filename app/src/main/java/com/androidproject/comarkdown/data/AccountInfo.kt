package com.androidproject.comarkdown.data

/**
 * Created by evan on 2018/1/9.
 */
object AccountInfo {
    lateinit var username: String
    lateinit var email: String
    lateinit var token: String
    var file: PartakeFileItem = PartakeFileItem("", "", -1)
    var filepath: String = ""
}