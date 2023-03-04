package com.example.myapp

import java.util.concurrent.Executors

fun main() {
    executeTasksUsingVirtualThreadsBuilder()
//    executeTasksUsingVirtualThreadsExecutor()
//    executeTasksUsingPlatformThreadsBuilder()
//    executeTasksUsingPlatformThreadsExecutor()
}

// The app can terminate before all tasks are completed
fun executeTasksUsingVirtualThreadsBuilder() =
    (0..1_000_000).map {
        Thread.ofVirtual().start {
            runCatching { println("Hello from thread $it") }
        }
    }

fun executeTasksUsingVirtualThreadsExecutor() =
    Executors.newVirtualThreadPerTaskExecutor().use {
        (0..1_000_000).forEach { taskIndex ->
            runCatching {
                it.submit { println("Hello from task $taskIndex") }
            }
        }
    }

// This method does not let the app terminate before all tasks are completed => blocking the app
fun executeTasksUsingPlatformThreadsBuilder() =
    (0..1_000_000).forEach {
        Thread.ofPlatform().start {
            println("Hello fromt task $it")
        }
    }

fun executeTasksUsingPlatformThreadsExecutor() =
    Executors.newCachedThreadPool().also {
        (0..10_000).forEach { taskIndex ->
            runCatching {
                it.submit { println("Hello from task $taskIndex") }
            }
        }
    }
