package com.example.saprodontia.ui.activities.main

import android.content.Intent
import android.support.design.widget.BottomSheetDialog
import android.support.v4.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem

import com.example.saprodontia.Adapter.MyPagerAdapter


import com.example.saprodontia.R
import com.example.saprodontia.Utils.ToastUtil
import com.example.saprodontia.baseMVP.BaseMVPActivity
import com.example.saprodontia.modules.SelectedFileManager
import com.example.saprodontia.ui.activities.cloud.CloudActivity

import com.example.saprodontia.ui.fragments.media.MediaFragmentType
import com.example.saprodontia.ui.fragments.selected.SelectedFragment
import com.mobile.utils.showToast
import com.mobile.utils.toast
import com.xt.directoryfragment.DirectoryFragment
import kotlinx.android.synthetic.main.activity_send.*
import kotlinx.android.synthetic.main.dialog.view.*

import java.util.*
import javax.inject.Inject

class SendActivity : BaseMVPActivity<SendContract.Presenter, SendContract.View>(), SendContract.View {
    override fun uploadFailed(errormsg: String) {
        showToast("上传失败,请检查网络链接")
    }

    override fun uploadStart() {
    }

    override fun uploadFinish(name: String) {
    }

    override fun allTaskFinished() {
    }


    override fun injectPresenter(): SendContract.Presenter = sendPresenter

    override fun injectDependencies() {
        activityComponent.inject(this)
    }

//    private var bsDialog: BottomSheetDialog? = null

    @Inject
    lateinit var sendPresenter: SendPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send)


        setSupportActionBar(mainToolbar)

        initViews()

    }
    


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.download -> {
                startActivity(Intent(this, CloudActivity::class.java))
            }

//            R.id.upload -> startActivity(Intent(this, UpLoadActivity::class.java))
        }
        return true
    }


    private fun initViews() {

        initDialog()
        initAdapter()
        initSurface()
    }

    private fun initSurface() {
        count?.text = getString(R.string.Saprodontia)
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.getTabAt(0)!!.text = "图片"
        tabLayout.getTabAt(1)!!.text = "应用"
        tabLayout.getTabAt(2)!!.text = "文档"
        tabLayout.getTabAt(3)!!.text = "视频"
        tabLayout.getTabAt(4)!!.text = "音乐"

        SelectedFileManager.addOnCountChangedListener { _, new ->
            if (new > 0) count.text = "已选择${new}个文件"
            else count?.text = getString(R.string.Saprodontia)
        }

        count.setOnClickListener {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.con, SelectedFragment())
                    .addToBackStack(null)
                    .commit()
        }
        
        
        
    }


    private fun initAdapter() {
        val fragments = ArrayList<Fragment>()
        fragments.add(MediaFragmentType.PHOTO.createFrament())
        fragments.add(MediaFragmentType.APPS.createFrament())
        fragments.add(MediaFragmentType.DOCUMENT.createFrament())
        fragments.add(MediaFragmentType.VIDEO.createFrament())
        fragments.add(MediaFragmentType.MUSIC.createFrament())
        val mySendPagerAdapter = MyPagerAdapter(supportFragmentManager, fragments)
        viewPager!!.adapter = mySendPagerAdapter

    }


    var chooseFragment : DirectoryFragment? = null
    private fun initDialog() {
        val bsDialog = BottomSheetDialog(this)
        val view = LayoutInflater.from(this).inflate(R.layout.dialog, null, false)

        view.upload.setOnClickListener {


            chooseFragment = DirectoryFragment()
            chooseFragment?.show(supportFragmentManager,"dir")
            chooseFragment?.setEnsureListener {
                mPresenter?.upload(it,SelectedFileManager.getSelectedFile())
                SelectedFileManager.clear()
            }
            bsDialog.dismiss()
        }
        


        view.send.setOnClickListener {
            //            socketModle!!.shareFile((applicationContext as App).senDatas)
            bsDialog.dismiss()
            sendBroadcast(Intent("SEND_DATA_CHANGE"))
        }

        view.receive.setOnClickListener {
            // TODO: 2017/7/24
            ToastUtil.showToast("RECE")
            bsDialog.dismiss()
        }

        showDialog.setOnClickListener {
            bsDialog.show()
        }

        bsDialog.setContentView(view)
    }

}
