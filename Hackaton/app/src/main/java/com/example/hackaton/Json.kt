package com.example.hackaton

import org.json.JSONObject

class Json {
    fun login(id : String, pw : String){
        var Jsonlogin = JSONObject()
        Jsonlogin.put("id",id)
        Jsonlogin.put("pw",pw)

    }
}