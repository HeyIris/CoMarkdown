package com.androidproject.comarkdown.ot

import android.content.Context
import com.androidproject.comarkdown.data.*
import com.androidproject.comarkdown.network.ApiClient
import com.androidproject.comarkdown.network.ApiErrorModel
import com.androidproject.comarkdown.network.ApiResponse
import com.androidproject.comarkdown.network.NetworkScheduler
import kotlin.math.min

/**
 * Created by evan on 2018/1/10.
 */
class OTClient(mRevision:Int, fileName:String, fileContent:String, viewContext:Context) {
    private var revision: Int
    private var state: ClientState
    var name: String
    var file: String
    private var fileID: Int = -1
    private val context: Context

    init {
        revision = mRevision
        state = Synchronized
        name = fileName
        file = fileContent
        context = viewContext
        searchFile()
    }

    fun applyClient(operation: List<String>) {
        state = state.applyClient(this, operation)
    }

    fun applyServer(operation: List<String>) {
        state = state.applyServer(this, operation)
    }

    fun serverAck() {
        revision += 1
        state = state.serverAck(this)
    }

    fun searchFile(){
        ApiClient.instance.service.onlineFileList(AccountInfo.username,AccountInfo.token)
                .compose(NetworkScheduler.compose())
                .subscribe(object : ApiResponse<OnlineFileInfo>(context) {
                    override fun success(data: OnlineFileInfo) {
                        if (data.success == "true"){
                            for (item in data.list){
                                if(item.name == name){
                                    fileID = item.id
                                    createServer()
                                    break
                                }
                            }
                        }
                    }

                    override fun fail(statusCode: Int, apiErrorModel: ApiErrorModel) {
                    }
                })
    }

    fun createServer(){
        ApiClient.instance.service.createServer(AccountInfo.username,fileID,AccountInfo.username,AccountInfo.token)
                .compose(NetworkScheduler.compose())
                .subscribe(object : ApiResponse<CreateServerInfo>(context) {
                    override fun success(data: CreateServerInfo) {
                        if (data.success == "true"){
                        }
                    }

                    override fun fail(statusCode: Int, apiErrorModel: ApiErrorModel) {
                        exitServer()
                    }
                })
    }

    fun exitServer(){
        ApiClient.instance.service.exitServer(AccountInfo.username,fileID,AccountInfo.username,AccountInfo.token)
                .compose(NetworkScheduler.compose())
                .subscribe(object : ApiResponse<ExitServerInfo>(context) {
                    override fun success(data: ExitServerInfo) {
                        if (data.success == "true"){
                        }
                    }

                    override fun fail(statusCode: Int, apiErrorModel: ApiErrorModel) {
                    }
                })
    }

    fun sendOperation(operation: List<String>) {
        ApiClient.instance.service.doOT(AccountInfo.username, fileID, AccountInfo.username, AccountInfo.token, operation, revision)
                .compose(NetworkScheduler.compose())
                .subscribe(object : ApiResponse<DoOTInfo>(context) {
                    override fun success(data: DoOTInfo) {
                        for (item in data.operation) {
                            if (item.name == AccountInfo.username) {
                                serverAck()
                            } else {
                                applyServer(item.ops)
                            }
                        }
                    }

                    override fun fail(statusCode: Int, apiErrorModel: ApiErrorModel) {
                    }
                })
    }

    fun applyOperation(operation: List<String>) {
        var result = ""
        var pos = 0
        var length = 0
        for (item in operation) {
            when (item[0]) {
                'r' -> {
                    length = item.subSequence(1, item.length).toString().toInt()
                    result += file.subSequence(pos, pos + length)
                    pos += length
                }
                'd' -> {
                    length = item.subSequence(1, item.length).toString().toInt()
                    pos += length
                }
                'i' -> {
                    result += item.subSequence(1, item.length)
                }
            }
        }
        file = result
    }

    fun mergeOperation(outstanding: List<String>, operation: List<String>): List<String> {
        var pos1 = -1
        var pos2 = -1
        var a = ""
        var b = ""
        var result = ArrayList<String>()
        var minLength: Int
        var lengthA: Int
        var lengthB: Int
        while (true) {
            if (a == "") {
                pos1++
                if (pos1 < outstanding.size) {
                    a = outstanding[pos1]
                }
            }
            if (b == "") {
                pos2++
                if (pos2 < operation.size) {
                    b = operation[pos2]
                }
            }
            if ((a == "") && (b == "")) {
                break
            }
            if (isDelete(a)) {
                result.add(a)
                a = ""
                continue
            }
            if (isInsert(b)) {
                result.add(b)
                b = ""
                continue
            }
            if ((a == "") || (b == "")) {
                result = ArrayList<String>()
                break
            }
            lengthA = lengthOfOperation(a)
            lengthB = lengthOfOperation(b)
            minLength = min(lengthA, lengthB)
            if (isRetain(a) && isRetain(b)) {
                result.add('r' + minLength.toString())
            } else if (isInsert(a) && isRetain(b)) {
                result.add('i' + a.substring(1, a.length))
            } else if (isRetain(a) && isDelete(b)) {
                result.add('d' + minLength.toString())
            }
            lengthA = lengthOfOperation(a)
            lengthB = lengthOfOperation(b)
            if (lengthA == lengthB) {
                a = ""
                b = ""
            } else if (lengthA > lengthB) {
                a = shortenOperation(a, lengthB)
                b = ""
            } else {
                a = ""
                b = shortenOperation(b, lengthA)
            }
        }
        return result
    }

    fun transformOperation(outstanding: List<String>, operation: List<String>): ArrayList<List<String>> {
        var result = ArrayList<ArrayList<String>>(2)
        result[0] = ArrayList()
        result[1] = ArrayList()
        var pos1 = -1
        var pos2 = -1
        var a = ""
        var b = ""
        var minLength: Int
        var lengthA: Int
        var lengthB: Int
        while (true) {
            if (a == "") {
                pos1++
                a = outstanding[pos1]
            }
            if (b == "") {
                pos2++
                b = operation[pos2]
            }
            if ((a == "") && (b == "")) {
                break
            }
            if (isInsert(a)) {
                result[0].add(a)
                result[1].add("r" + lengthOfOperation(a))
                a = ""
                continue
            }
            if (isInsert(b)) {
                result[0].add("r" + lengthOfOperation(b))
                result[1].add(b)
                b = ""
                continue
            }
            if ((a == "") || (b == "")) {
                result = ArrayList<ArrayList<String>>()
                break
            }
            lengthA = lengthOfOperation(a)
            lengthB = lengthOfOperation(b)
            minLength = min(lengthA, lengthB)
            if (isRetain(a) && isRetain(b)) {
                result[0].add('r' + minLength.toString())
                result[1].add('r' + minLength.toString())
            } else if (isDelete(a) && isRetain(b)) {
                result[0].add('d' + minLength.toString())
            } else if (isRetain(a) && isDelete(b)) {
                result[1].add('d' + minLength.toString())
            }
            lengthA = lengthOfOperation(a)
            lengthB = lengthOfOperation(b)
            if (lengthA == lengthB) {
                a = ""
                b = ""
            } else if (lengthA > lengthB) {
                a = shortenOperation(a, lengthB)
                b = ""
            } else {
                a = ""
                b = shortenOperation(b, lengthA)
            }
        }
        val results = ArrayList<List<String>>()
        results.add(result[0])
        results.add(result[1])
        return results
    }

    private fun lengthOfOperation(op: String): Int {
        var length = 0
        when (op[0]) {
            'r' -> {
                length = op.substring(1, op.length).toInt()
            }
            'd' -> {
                length = op.substring(1, op.length).toInt()
            }
            'i' -> {
                length = op.length - 1
            }
        }
        return length
    }

    private fun isRetain(op: String): Boolean = (op.length > 1 && op[0] == 'r')

    private fun isDelete(op: String): Boolean = (op.length > 1 && op[0] == 'd')

    private fun isInsert(op: String): Boolean = (op.length > 1 && op[0] == 'i')

    private fun shortenOperation(op: String, by: Int): String {
        if (isInsert(op)) {
            return op.substring(0, by)
        } else if (isDelete(op)) {
            return ('d' + (lengthOfOperation(op) - by).toString())
        } else if (isRetain(op)) {
            return ('r' + (lengthOfOperation(op) - by).toString())
        }
        return "r0"
    }
}