package coroutine.Lec3

import coroutine.Lec1.printWithThread
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main():Unit = runBlocking {
    val time = measureTimeMillis {
        val job1 =async {  apiCall1() }
        val job2 =async {  apiCall2(job1.await()) }
        printWithThread(job2.await())
    }
    printWithThread("소요시간: $time")
}

suspend fun apiCall1():Int {
    delay(1_000L)
    return 1
}

suspend fun apiCall2(num: Int): Int {
    delay(1_000L)
    return num + 2
}

fun example5():Unit = runBlocking{
    val job = async { 3 + 5 }
    val eight = job.await() // async의 결과를 가져오는 함수
    printWithThread(eight)
}

fun example4():Unit = runBlocking {
    val job1 = launch {
        delay(1_000L)
        printWithThread("job1")
    }
    // job1이 끝날 때까지 기다림
    job1.join()
    val job2 = launch {
        delay(1_000L)
        printWithThread("job2")
    }
}


fun example3():Unit = runBlocking {
    val job = launch {
        (1..5).forEach {
            printWithThread(it)
            delay(500)
        }
    }

    delay(1_000L)
    job.cancel()
}

fun example2():Unit = runBlocking {
    val job = launch(start = CoroutineStart.LAZY) {
        printWithThread("Hello launch")
    }

    delay(1_000L)
    // start 호출해야 실행됨(CoroutineStart.LAZY)
    job.start()
}

fun example1() {
    runBlocking {
        printWithThread("START")
        launch {
            delay(2_000L)
            printWithThread("LAUNCH END")
        }
    }
    printWithThread("END")
}
