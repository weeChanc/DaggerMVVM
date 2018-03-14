package com.example.saprodontia.baseMVP

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v4.app.Fragment
import android.util.Log
import android.view.View
import com.example.saprodontia.Application.App
import com.example.saprodontia.di.component.FragmentComponent
import com.example.saprodontia.di.modules.FragmentModule

/**
 * Created by steve on 17-11-25.
 */


abstract class BaseMVPFragment<P : BaseContract.Presenter<V>, V : BaseContract.View> : Fragment(), BaseContract.View {

    abstract fun injectPresenter(): P

    abstract fun injectDependencies()

    var mPresenter: P? = null

    @CallSuper
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        injectDependencies()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //如果在他之前获取Viewmodel 的话 ，则会不一样
        var model = ViewModelProviders.of(this).get(BaseViewModel::class.java) as BaseViewModel<V,P>

        if (model.mPresenter == null) {
            mPresenter = injectPresenter()
            model.mPresenter = mPresenter
            model.mPresenter?.attachView( this as V )
        } else {
            mPresenter = model.mPresenter
        }

        Log.e("BaseMVPFragment",mPresenter.toString())
        mPresenter?.attachView( this as V ) //attach first
        mPresenter?.onPresenterCreate() //create presenter later
        onPrepared()  // Presenter is ready to use

    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detachView()
        mPresenter?.onPresenterDestroy()
    }

    var fragmentComponent: FragmentComponent = App.ctx.applicationComponent.plus(FragmentModule(this))

    abstract fun onPrepared()

}