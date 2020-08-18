package com.example.hackaton

import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.IOException


class OkHttpService() {
    var client = OkHttpClient()

    val JSON = MediaType.parse("application/json; charset=utf-8")

    @Throws(IOException::class)
    fun Get(url: String?): String? {
        val request: Request = Request.Builder()
            .url(url)
            .build()
        client.newCall(request).execute().use { response -> return response.body()!!.string() }
    }

    @Throws(IOException::class)
    fun Post(url: String, json: String): String {
        val body: RequestBody = RequestBody.create(JSON, json)
        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()
        client.newCall(request).execute().use({ response -> return response.body()!!.string() })
    }
}