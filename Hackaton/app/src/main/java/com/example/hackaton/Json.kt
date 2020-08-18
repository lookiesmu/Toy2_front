package com.example.hackaton

import org.json.JSONObject
class Json {
    fun login(id:String?, pw : String?){
        var Jsonlogin = JSONObject()
        Jsonlogin.put("nickname",id)
        Jsonlogin.put("password",pw)
    }
}