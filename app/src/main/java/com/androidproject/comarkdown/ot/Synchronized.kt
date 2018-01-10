package com.androidproject.comarkdown.ot

/**
 * Created by evan on 2018/1/10.
 */
object Synchronized:ClientState {
    override fun applyClient(client:OTClient, operation:List<String>): ClientState{
        client.sendOperation(operation)
        return AwaitingConfirm(operation)
    }

    override fun applyServer(client: OTClient, operation: List<String>): ClientState {
        client.applyOperation(operation)
        return this
    }

    override fun serverAck(client: OTClient): ClientState {
        //???
        return Synchronized
    }
}