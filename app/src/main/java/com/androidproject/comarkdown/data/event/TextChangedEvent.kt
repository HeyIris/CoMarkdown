package com.androidproject.comarkdown.data.event

/**
 * Created by evan on 2018/1/11.
 */
data class TextChangedEvent(
        var oldValue:String,
        var newValue:String
)