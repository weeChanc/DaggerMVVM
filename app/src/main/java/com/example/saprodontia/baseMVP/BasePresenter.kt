package com.example.saprodontia.baseMVP

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.annotation.CallSuper
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by steve on 17-11-24.
 */
abstract class BasePresenter<V : BaseContract.View>: BaseContract.Presenter<V> {

    @Inject
    lateinit var fecher : Fetcher

    var view : V? = null


    @CallSuper
    override fun attachView(view: V) {
        this.view = view

    }

    @CallSuper
    override fun detachView() {
        view = null
    }
}