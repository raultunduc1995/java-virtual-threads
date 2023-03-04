package com.example.myapp

import com.example.myapp.utils.doSafely
import com.example.myapp.utils.use
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket

fun main() {
    startServer()
}

private fun startServer() = doSafely {
    ServerSocket(9991).use { serverSocket ->
        while (true) {
            val clientSocket = serverSocket.accept()
            Thread.ofVirtual().start {
                readClientData(clientSocket)
            }
        }
    }
}

private fun readClientData(clientSocket: Socket) = doSafely {
    arrayOf(
        PrintWriter(clientSocket.getOutputStream(), true),
        BufferedReader(
            InputStreamReader(clientSocket.getInputStream())
        )
    ).use { (printWriter, bufferedReader) ->
        var inputLine = (bufferedReader as BufferedReader).readLine()
        while (inputLine != null) {
            println(inputLine)
            inputLine = bufferedReader.readLine()
        }
    }
}

