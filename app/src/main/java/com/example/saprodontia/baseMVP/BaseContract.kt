package com.example.saprodontia.baseMVP

/**
 * Created by steve on 17-11-24.
 */
interface BaseContract{

    interface View{

    }

    interface Presenter<V : BaseContract.View>{
        fun onPresenterCreate()

        fun onPresenterDestroy()

        fun attachView(view : V )

        fun detachView()

    }
}