package com.example.githubtrendingrepo.util

import android.content.Context
import android.widget.Toast


fun Context.showToastMsg(msg: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, msg, duration).show()
}

