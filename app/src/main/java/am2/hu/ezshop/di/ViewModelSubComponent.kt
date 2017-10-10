package am2.hu.ezshop.di

import am2.hu.ezshop.ui.main.MainViewModel
import dagger.Subcomponent


@Subcomponent
interface ViewModelSubComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): ViewModelSubComponent
    }

    fun mainViewModel(): MainViewModel
}