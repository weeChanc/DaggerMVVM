package com.example.daggermvvm.ui

import javax.inject.Inject

/**
 * Created by 铖哥 on 2017/11/9.
 */
class MainPresenter : MainContract.Presenter{

    lateinit var view : MainContract.View

    constructor(mView : MainContract.View){
        this.view = mView;
    }


    override fun subscribe() {
    }

    override fun unsubscribe() {
    }

}

