package com.example.saprodontia.baseMVP

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import kotlin.properties.Delegates

/**
 * Created by steve on 17-11-24.
 */
class BaseViewModel< V : BaseContract.View, P : BaseContract.Presenter<V>> : ViewModel() {

    var mPresenter : P? = null

}