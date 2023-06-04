package io.github.ilyadreamix.httptools.request

import android.util.Log
import io.github.ilyadreamix.httptools.request.model.Request
import io.github.ilyadreamix.httptools.request.model.RequestList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileReader
import java.io.FileWriter

class RequestFile(private val filesDir: File) {
    suspend fun readRequestList(): List<Request>? {
        val file = File(filesDir, REQUESTS_INFORMATION_FILENAME)
        return withContext(Dispatchers.IO) {
            if (file.exists()) {
                try {
                    val bufferedReader = BufferedReader(FileReader(file))
                    val stringBuilder = StringBuilder()
                    var line: String? = bufferedReader.readLine()
                    while (line != null) {
                        stringBuilder.append(line)
                        line = bufferedReader.readLine()
                    }
                    bufferedReader.close()

                    val jsonInformation = stringBuilder.toString()
                    val information = Json.decodeFromString<RequestList>(jsonInformation).requests

                    Log.d(LOG_TAG, "readRequestList: OK")

                    information
                } catch (exception: Exception) {
                    Log.e(LOG_TAG, "readRequestList: Unhandled exception", exception)
                    null
                }
            } else {
                Log.d(LOG_TAG, "readRequestList: $REQUESTS_INFORMATION_FILENAME does not exist")
                null
            }
        }
    }

    suspend fun saveRequestList(requestList: List<Request>) {
        val file = File(filesDir, REQUESTS_INFORMATION_FILENAME)
        withContext(Dispatchers.IO) {
            val jsonInformation = Json.encodeToString(RequestList(requestList))
            val bufferedWriter = BufferedWriter(FileWriter(file))
            bufferedWriter.write(jsonInformation)
            bufferedWriter.close()

            Log.d(LOG_TAG, "saveRequestList: OK")
        }
    }

    fun deleteRequestList() {
        val file = File(filesDir, REQUESTS_INFORMATION_FILENAME)
        file.delete()

        Log.d(LOG_TAG, "deleteRequestList: OK")
    }
}

private const val REQUESTS_INFORMATION_FILENAME = "requestsInformation.json"
private const val LOG_TAG = "RequestFile"
