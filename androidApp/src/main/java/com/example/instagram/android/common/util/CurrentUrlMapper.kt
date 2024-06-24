package com.example.instagram.android.common.util

private const val CURRENT_BASE_URL = "http://192.168.61.69:8080/"

fun String.toCurrentUrl():String{
    return "$CURRENT_BASE_URL${this.substring(26)}"
}