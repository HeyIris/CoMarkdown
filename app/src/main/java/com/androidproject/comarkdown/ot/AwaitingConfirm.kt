package com.androidproject.comarkdown.ot

/**
 * Created by evan on 2018/1/10.
 */
class AwaitingConfirm(mOutstanding: List<String>) : ClientState {
    private var outstanding: List<String>

    init {
        outstanding = mOutstanding
    }

    override fun applyClient(client: OTClient, operation: List<String>): ClientState {
        return AwaitingWithBuffer(outstanding, operation)
    }

    override fun applyServer(client: OTClient, operation: List<String>): ClientState {
        val temp = client.transformOperation(outstanding, operation)
        client.applyOperation(temp[1])
        return AwaitingConfirm(temp[0])
    }

    override fun serverAck(client: OTClient): ClientState {
        return Synchronized
    }
}