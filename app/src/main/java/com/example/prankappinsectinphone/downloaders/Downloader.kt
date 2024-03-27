package com.example.prankappinsectinphone.downloaders

interface Downloader {
    fun downloadFile(url: String): Long
}