package com.example.instagram

interface Platform {
    val name: String
}
expect fun getPlatform(): Platform