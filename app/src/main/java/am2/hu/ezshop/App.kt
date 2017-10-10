package am2.hu.ezshop

import am2.hu.ezshop.di.AppModule
import am2.hu.ezshop.di.DaggerAppComponent
import android.app.Application


class App : Application() {
    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
                .inject(this)
    }
}