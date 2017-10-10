package am2.hu.ezshop

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


fun <T> LiveData<T>.getValueTest(): T {
    val data = arrayOfNulls<Any>(1)
    val latch = CountDownLatch(1)
    val observer: Observer<T> = object : Observer<T> {
        override fun onChanged(t: T?) {
            data[0] = t
            latch.countDown()
            removeObserver(this)
        }
    }

    observeForever(observer)
    latch.await(2, TimeUnit.SECONDS)

    return data[0] as T
}