package com.andy.andyyohanes

import android.app.Application
import com.andy.andyyohanes.data.network.MyApi
import com.andy.andyyohanes.data.network.NetworkConnectionInterceptor
import com.andy.andyyohanes.data.repositories.UserRepository
import com.andy.andyyohanes.ui.MainViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MainApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@MainApplication))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { MyApi(instance()) }
        bind() from singleton { UserRepository(instance()) }
        bind() from provider { MainViewModelFactory(instance()) }
    }
}