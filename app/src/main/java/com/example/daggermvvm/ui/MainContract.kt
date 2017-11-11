package com.example.daggermvvm.ui

import com.example.daggermvvm.base.BasePresenter
import com.example.daggermvvm.base.BaseView

/**
 * Created by 铖哥 on 2017/11/9.
 */
interface MainContract {
    interface View : BaseView<Presenter>{

    }

    interface Presenter : BasePresenter{

    }
}