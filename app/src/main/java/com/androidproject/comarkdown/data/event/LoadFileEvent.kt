package com.androidproject.comarkdown.data.event

/**
 * Created by evan on 2018/1/11.
 */
data class LoadFileEvent(
        var filePath: String,
        var master: String,
        var fileName: String
)