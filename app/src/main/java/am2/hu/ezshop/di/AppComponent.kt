package am2.hu.ezshop.di

import am2.hu.ezshop.App
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = [(AndroidInjectionModule::class), (AppModule::class), (ActivityModule::class)])

interface AppComponent {
    fun inject(app: App)
}