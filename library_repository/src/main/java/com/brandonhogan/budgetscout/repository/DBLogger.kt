package com.brandonhogan.budgetscout.repository

import android.content.Context
import android.widget.Toast
import java.lang.reflect.Method


fun showDBLoggerToast(context: Context?) {
    if (BuildConfig.DEBUG) {
        try {
            val debugDB = Class.forName("com.amitshekhar.DebugDB")
            val getAddressLog: Method = debugDB.getMethod("getAddressLog")
            val value: Any = getAddressLog.invoke(null)
            Toast.makeText(context, value as String, Toast.LENGTH_LONG).show()
        } catch (ignore: Exception) {
        }
    }
}