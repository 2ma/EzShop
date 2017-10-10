package am2.hu.ezshop.viewmodel

import am2.hu.ezshop.di.ViewModelSubComponent
import am2.hu.ezshop.ui.main.MainViewModel
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.util.ArrayMap
import javax.inject.Inject


class EzShopViewModelFactory @Inject constructor(viewModelSubComponent: ViewModelSubComponent) : ViewModelProvider.Factory {

    private val models: ArrayMap<Class<*>, () -> (ViewModel)> = ArrayMap()

    init {
        models.put(MainViewModel::class.java, viewModelSubComponent::mainViewModel)
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        var model = models[modelClass]

        if (model == null) {
            for ((key, value) in models) {
                if (modelClass.isAssignableFrom(key)) {
                    model = value
                    break
                }
            }
        }

        if (model == null) {
            throw IllegalArgumentException("Unknown model class $modelClass")
        }

        try {

            return model.invoke() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }
}