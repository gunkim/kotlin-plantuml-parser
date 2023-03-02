package io.github.babiesdev.clazz

import java.io.FileInputStream
import java.nio.file.Path

fun pathToStrings(path:Path){
    FileInputStream(path.toFile()).use { stream ->
        val bytes = ByteArray(stream.available())
        stream.read(bytes)
        val text = String(bytes)
        println(text)
    }

}