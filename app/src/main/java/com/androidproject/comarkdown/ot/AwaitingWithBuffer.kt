package com.androidproject.comarkdown.ot

/**
 * Created by evan on 2018/1/10.
 */
class AwaitingWithBuffer(mOutstanding: List<String>,mBuffer: List<String>) : ClientState {
    private var outstanding:List<String>
    private var buffer:List<String>

    init {
        outstanding = mOutstanding
        buffer = mBuffer
    }
    override fun applyClient(client: OTClient, operation: List<String>): ClientState {
        return AwaitingWithBuffer(outstanding, client.mergeOperation(buffer, operation))
    }

    override fun applyServer(client: OTClient, operation: List<String>): ClientState {
        val temp1 = client.transformOperation(outstanding, operation)
        val temp2 = client.transformOperation(buffer, temp1[1])
        client.applyOperation(temp2[1])
        return AwaitingWithBuffer(temp1[0], temp2[0])
    }

    override fun serverAck(client: OTClient): ClientState {
        client.sendOperation(buffer)
        return AwaitingConfirm(buffer)
    }
}