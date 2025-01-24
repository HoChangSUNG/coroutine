package coroutine.Lec5

import coroutine.Lec1.printWithThread
import kotlinx.coroutines.*

fun main(): Unit  = runBlocking() {
    val exceptionHandler = CoroutineExceptionHandler{_, _ ->
        printWithThread("예외")
        //예외 먹어버림
    }

    val  job = CoroutineScope(Dispatchers.Default).launch(exceptionHandler) {
        throw IllegalArgumentException()
    }

    delay(1_000L)
}
// 자식 코루틴의 예외는 부모 코루틴에 전파됨
// 자식 코루틴의 예외를 부모 코루틴에 전파하지 않으려면 SupervisorJob을 사용해야함
fun notThrow():Unit = runBlocking {
    val job = async(SupervisorJob()) {
        throw IllegalArgumentException()
    }
}

fun asyncException():Unit = runBlocking {
    // async 예외 발생 -> 예외 출력 x, 그냥 종료
    val job2 = async {
        throw IllegalArgumentException()
    }

    delay(1_000L)
    // await을 해야 main 쓰레드에서 예외를 확인 가능
}

fun asyncNewThreadException():Unit = runBlocking {
    // async 예외 발생 -> 예외 출력 x, 그냥 종료
    val job2 = CoroutineScope(Dispatchers.Default).async {
        throw IllegalArgumentException()
    }

    delay(1_000L)
    // await을 해야 main 쓰레드에서 예외를 확인 가능
    job2.await()
}

fun launchException():Unit = runBlocking {
    // launch -> 예외 발생 시 예외 출력 후 코루틴 종료
    val job1 = launch {
        throw IllegalArgumentException()
    }

    delay(1_000L)
}

fun launchNewThreadException():Unit = runBlocking {
    // launch -> 예외 발생 시 예외 출력 후 코루틴 종료
    val job1 = CoroutineScope(Dispatchers.Default).launch {
        throw IllegalArgumentException()
    }

    delay(1_000L)
}

fun example1():Unit = runBlocking {
    // 새로운 영역에서 새로운 스레드에서 코루틴을 실행시킬거임
    val job1 = CoroutineScope(Dispatchers.Default).launch {
        delay(1_000L)
        printWithThread("job1")
    }

    val job2 = CoroutineScope(Dispatchers.Default).launch {
        delay(1_000L)
        printWithThread("job2")
    }
}
