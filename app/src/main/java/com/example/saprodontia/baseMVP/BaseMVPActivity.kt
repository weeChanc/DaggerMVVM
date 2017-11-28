package com.example.saprodontia.baseMVP

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.saprodontia.Application.App
import com.example.saprodontia.di.component.ActivityComponent
import com.example.saprodontia.di.modules.ActivityModule
import com.mobile.utils.AlbumPickerActivity
import com.mobile.utils.permission.PermissionCompatActivity

/**
 * Created by steve on 17-11-25.
 */
abstract class BaseMVPActivity<P : BaseContract.Presenter<V>, V : BaseContract.View> : AlbumPickerActivity(), BaseContract.View {

    abstract fun injectPresenter(): P

    abstract fun injectDependencies()

    var mPresenter: P? = null

    lateinit var model: BaseViewModel<V, P>


    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies()

        var model = ViewModelProviders.of(this).get(BaseViewModel::class.java) as BaseViewModel<V,P>


        if (model.mPresenter == null) {
            mPresenter = injectPresenter()
            model.mPresenter = mPresenter
            model.mPresenter?.attachView( this as V )
            mPresenter?.onPresenterCreate()
        } else {
            mPresenter = model.mPresenter
        }

        model.mPresenter?.attachView( this as V )

    }


    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.onPresenterDestroy()
    }

    val activityComponent: ActivityComponent by lazy {
        App.app.applicationComponent + ActivityModule(this)
    }
}