package am2.hu.ezshop.di

import dagger.Subcomponent


@Subcomponent
interface ViewModelSubComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): ViewModelSubComponent
    }
}