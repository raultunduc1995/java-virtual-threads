package com.example.myapp

import com.example.myapp.utils.doSafely
import com.example.myapp.utils.use
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

fun main() {
    startClient()
}

fun startClient() = doSafely {
    doSafely {
        Socket("localhost", 9991).use { socket ->
            handleUserInput(socket)
        }
    }
}

private fun handleUserInput(socket: Socket) = doSafely {
    arrayOf(
        PrintWriter(socket.getOutputStream(), true),
        BufferedReader(
            InputStreamReader(System.`in`)
        )
    ).use { (printWritter, stdIn) ->
        var userInput = (stdIn as BufferedReader).readLine()
        while (userInput != null) {
            (printWritter as PrintWriter).println(userInput)
            if (userInput.equals("bye")) break
            userInput = stdIn.readLine()
        }
    }
}