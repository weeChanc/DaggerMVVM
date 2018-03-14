package com.example.saprodontia.ui.activities.cloud

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.example.saprodontia.Activities.Cloud

//import com.example.saprodontia.Adapter.FileAdapter
//import com.example.saprodontia.Adapter.CloudAdapter
import com.example.saprodontia.R
import com.example.saprodontia.baseMVP.BaseMVPActivity

import com.example.saprodontia.ui.activities.down.DownloadActivity
import com.xt.directoryfragment.DirectoryAdapter
import com.xt.directoryfragment.MFile
import kotlinx.android.synthetic.main.fragment_exhibition.*
import org.greenrobot.eventbus.EventBus
import java.util.*
import javax.inject.Inject

/**
 * Created by steve on 17-11-28.
 */

class  CloudActivity : BaseMVPActivity<CloudContract.Presenter,CloudContract.View>() , CloudContract.View{


    var adapter: DirectoryAdapter? = null

    override fun onFinish(root: MFile) {
        adapter = DirectoryAdapter(this,root,true)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    @Inject
    lateinit var presenter :  CloudPresenter

    override fun injectPresenter(): CloudContract.Presenter = presenter

    override fun injectDependencies() = activityComponent.inject(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_exhibition)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "CloudDisk"
        mPresenter?.getCloudDirectory()

        adapter?.setItemLongClickListener { view, i ->
            EventBus.getDefault().postSticky(adapter?.currentDir?.directories!![i])
            startActivity(Intent(this, DownloadActivity::class.java))
            return@setItemLongClickListener false
        }


    }

    override fun onBackPressed() {

        if(adapter != null) {
            if ( !adapter!!.consumeBackPressed()) {
                super.onBackPressed()
            }else{
                recyclerView.scheduleLayoutAnimation()
            }
        }else{
            super.onBackPressed()
        }

    }
}