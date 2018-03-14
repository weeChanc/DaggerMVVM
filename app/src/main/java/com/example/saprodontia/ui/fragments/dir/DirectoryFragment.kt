package com.xt.directoryfragment

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager

import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.saprodontia.R
import com.xt.directoryfragment.MFile.Companion.DIR
import kotlinx.android.synthetic.main.dialog_dir.*


/**
 * Created by steve on 18-1-28.
 */
class DirectoryFragment() : android.support.v4.app.DialogFragment() {

    lateinit var manager: DirectoryManager
    private lateinit var adapter: DirectoryAdapter
    private var ensureListener: ((path: String) -> Unit)? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.dialog_dir, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
        recyclerView.emptyView = empty

    }

    override fun onResume() {
        val params = dialog.window!!.attributes
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = ViewGroup.LayoutParams.MATCH_PARENT
        dialog.window!!.attributes = params as android.view.WindowManager.LayoutParams
        super.onResume()
    }

    fun setup() {

        manager = DirectoryManager
        adapter = DirectoryAdapter(context!!, manager.root, true)

        this.dialog.setOnKeyListener { dialog, keyCode, event ->

            if (event.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                val consume = adapter.consumeBackPressed()
                recyclerView.scheduleLayoutAnimation()
                consume
            } else {
                false
            }

        }

        adapter.setOnPathChangListenner {
            currentDir.animate().setDuration(200).translationY(-50f).alpha(0f).setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    currentDir.translationY = 50f
                    var dir = it.substring(0, it.length - 1)
                    if (dir == "") dir = "根目录"
                    currentDir.text = dir
                    currentDir.animate().setDuration(200).translationY(0f).alpha(1f).setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                        }
                    })
                }
            })
        }


        add.setOnClickListener { showDiaglog() }


        ensure.setOnClickListener {
            ensureListener?.invoke(adapter.currentDir.path)
            ensure.postDelayed({dismiss()},300)
        }

        currentDir.text = "根目录"
    }


    fun showDiaglog() {
        InputDialog.InputDialogBuilder(this.context!!) {
            title = "输入文件夹"
            onclick = {
                val dir = MFile(adapter.currentDir.path + "${contentText}/", type = DIR, size = 0)
                manager.mkdir(dir)
                dismiss()
                adapter.notifyDataSetChanged()
            }
        }.build().show()

    }

    fun setEnsureListener(listener: ((path: String) -> Unit)?) {
        ensureListener = listener
    }


}