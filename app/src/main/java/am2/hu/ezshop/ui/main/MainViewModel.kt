package am2.hu.ezshop.ui.main

import am2.hu.ezshop.AppExecutors
import am2.hu.ezshop.persistance.entity.Shop
import am2.hu.ezshop.persistance.repository.local.LocalRepository
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import javax.inject.Inject


class MainViewModel @Inject constructor(val localRepository: LocalRepository, val appExecutors: AppExecutors) : ViewModel() {
    fun getNavMenuItems(): LiveData<List<Shop>> = localRepository.getAllShops()

}