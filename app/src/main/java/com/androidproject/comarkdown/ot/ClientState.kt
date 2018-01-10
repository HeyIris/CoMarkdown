package com.androidproject.comarkdown.ot

/**
 * Created by evan on 2018/1/10.
 */
interface ClientState {
    fun applyClient(client:OTClient, operation:List<String>): ClientState

    fun applyServer(client:OTClient, operation:List<String>): ClientState

    fun serverAck(client:OTClient): ClientState
}