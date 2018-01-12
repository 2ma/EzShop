package am2.hu.ezshop

import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class AppExecutors(val diskIO: Executor) {

    @Inject constructor() : this(Executors.newSingleThreadExecutor())
}