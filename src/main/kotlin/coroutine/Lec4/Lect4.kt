package coroutine.Lec4

import coroutine.Lec1.printWithThread
import kotlinx.coroutines.*

class Lect4 {
}

fun main(): Unit = runBlocking {
    val job = launch {
        try{
            delay(1_000L)
        }catch (e:CancellationException) {
            // 아무 작업도 안함
        }
        printWithThread("delay에 의해 취소되지 않았다")
    }

    delay(100L)
    printWithThread("취소 시작")
    job.cancel()
}

fun example2():Unit = runBlocking {
    val job = launch(Dispatchers.Default) {
        var i = 1
        var nextPrintTime = System.currentTimeMillis()
        while (i <= 5) {
            if(nextPrintTime <= System.currentTimeMillis()) {
                printWithThread("${i++}번째 출력!")
                nextPrintTime += 1_000L
            }

            // 취소 명령 받았는지
            if(isActive) {
                throw CancellationException()
            }
        }
    }
    delay(100L)
    job.cancel()
}

fun example1():Unit = runBlocking {
    val job1 = launch {
        delay(1_000L)
        printWithThread("job1")
    }

    val job2 = launch {
        delay(1_000L)
        printWithThread("job2")
    }

    delay(100)
    job1.cancel()
}
