package com.example.saprodontia.ui.fragments.dir

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager

import com.example.saprodontia.Adapter.DirectoryAdapter
import com.example.saprodontia.R
import com.example.saprodontia.data.DirectoryManager

import android.text.InputType
import android.view.*
import com.afollestad.materialdialogs.MaterialDialog
import com.example.saprodontia.data.Directory
import com.example.saprodontia.data.Directory.Companion.DIR
import com.example.saprodontia.data.DirectoryManager.Companion.REMOTE
import kotlinx.android.synthetic.main.dialog_dir.*


/**
 * Created by steve on 17-11-29.
 */
class DirectoryFragment() : android.support.v4.app.DialogFragment() {

    var manager : DirectoryManager? = null
    private lateinit var adapter: DirectoryAdapter
    private var ensureListener: ((path: String) -> Unit)? = null
    private lateinit var model: DirViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        model = ViewModelProviders.of(this).get(DirViewModel::class.java)
        return inflater.inflate(R.layout.dialog_dir, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
    }

    override fun onResume() {
        val params = dialog.window!!.attributes
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = ViewGroup.LayoutParams.MATCH_PARENT
        dialog.window!!.attributes = params as android.view.WindowManager.LayoutParams
        super.onResume()
    }

    fun setup() {

        manager = model.manager
        adapter = model.adapter

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
            currentDir.text = it
        }



        add.setOnClickListener { showDiaglog() }


        ensure.setOnClickListener {
            ensureListener?.invoke(adapter.currentDir.path)
            dismiss()
        }

        currentDir.text = adapter.currentDir.path
    }


    fun showDiaglog() {
        MaterialDialog.Builder(context!!).title("新建文件夹")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input("文件夹名", "", false, { _, input ->
                    val dir = Directory(adapter.currentDir.path + "${input.toString()}/",type = DIR)
                    manager?.mkdir(dir)
                    DirectoryManager(REMOTE).mkdir(dir)
                    adapter.notifyDataSetChanged()
                }).show()
    }

    fun setEnsureListener(listener: ((path: String) -> Unit)?) {
        //VIEW MODEL DIDN'T INIT HERE
        ensureListener = listener
    }

    class DirViewModel : ViewModel() {
        val manager = DirectoryManager(DirectoryManager.LOCAL)
        var adapter = DirectoryAdapter(manager.root,true)
    }


}
