//package com.example.githubtrendingrepo.util
//
//import aglibs.loading.skeleton.BuildConfig
//import com.example.githubtrendingrepo.app.Constants
//import android.util.Log
//
//object Logger {
//
//    inline fun <reified T> log(loggerType: LogType, vararg msg: String) {
//        if (BuildConfig.BUILD_TYPE == Constants.DEBUG_TYPE) {
//            val tag = T::class.java.simpleName
//            when (loggerType) {
//                LogType.DEBUG -> Log.d(tag, msg.getString())
//                LogType.INFO -> Log.i(tag, msg.getString())
//                LogType.ERROR -> Log.e(tag, msg.getString())
//            }
//        }
//    }
//
//    enum class LogType {
//        DEBUG,
//        INFO,
//        ERROR
//    }
//}