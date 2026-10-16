package com.yavuz_can.movieexplorer.util

fun String.toPosterUrl(): String = "https://image.tmdb.org/t/p/w500$this"
fun String.toBackdropUrl(): String = "https://image.tmdb.org/t/p/w780$this"