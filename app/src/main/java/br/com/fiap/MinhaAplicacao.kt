package br.com.fiap

import android.app.Application
import com.facebook.stetho.Stetho
import dbModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import repositoryModule
import viewModelModule

class MinhaAplicacao : Application() {

    override fun onCreate() {
        super.onCreate()

        if(BuildConfig.DEBUG){
            Stetho.initializeWithDefaults(this);
        }

        startKoin {
            androidLogger()
            androidContext(this@MinhaAplicacao)
            modules(
                listOf(
                    viewModelModule,
                    //uiModule,
                    dbModule,
                    repositoryModule
                )
            )
        }
    }
}