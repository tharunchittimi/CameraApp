package com.example.camerawithpicture.data

import android.content.Context
import android.content.SharedPreferences
import com.example.camerawithpicture.application.MyApplication

object  MyData {
    private var sharedPreferences: SharedPreferences? = null
    private const val KEY_NAME = "NAME"
    private const val KEY_PASSWORD = "PASSWORD"
    private const val KEY_IMAGE = "IMAGE"

    private fun getSharedPreference():SharedPreferences{
       return sharedPreferences?: MyApplication.getApplicationContext().getSharedPreferences("myPref", Context.MODE_PRIVATE)
    }

    private fun setString(key:String,value:String){
        getSharedPreference().edit().putString(key, value).apply()
    }

   private fun getString(key:String):String{
       return getSharedPreference().getString(key, "")?:""
    }

    private fun setImage(key:String,value:String){
        getSharedPreference().edit().putString(key, value).apply()
    }

    private fun getImage(key:String):String{
        return getSharedPreference().getString(key, "")?:""
    }

    fun setUserName(value: String){
        setString(KEY_NAME,value)
    }

    fun getUserName():String{
       return getString(KEY_NAME)
    }

    fun setPassword(value: String){
        setString(KEY_PASSWORD,value)
    }

    fun getPassword():String{
       return getString(KEY_PASSWORD)
    }

    fun setimage(value: String){
        setImage(KEY_IMAGE,value)
    }

    fun getimage():String{
        return getImage(KEY_IMAGE)
    }
}