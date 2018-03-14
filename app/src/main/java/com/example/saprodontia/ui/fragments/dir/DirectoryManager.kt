package com.xt.directoryfragment

import android.content.Context
import android.util.Log
import com.example.saprodontia.Application.App
import com.example.saprodontia.Constant.Constant
import com.example.saprodontia.Utils.QiniuHelper
import com.google.gson.Gson
import com.mobile.utils.downloader.Downloader
import com.xt.directoryfragment.MFile.Companion.DIR
import kotlin.concurrent.thread

/**
 * Created by steve on 17-11-28.
 */

/**
 * link-> 不设空值则会从此URL中获取远程的json数据用于更新
 */
object DirectoryManager {


    lateinit var root: MFile

    private val preference = Preference(App.ctx)

    val downloadRemoteData: (() -> String)? = null

    val saveToremote: ((json: String) -> Unit) = {

        QiniuHelper.overwriteUpload(Constant.STRUCTURE + "/dirStructor.txt", "dir",
                onSuccess = {
                    preference.save("directory") { "local" - it }
                },

                onFailed = { })
    }

    init {

        Log.e("DirectoryManager", "init")
        //获取本地
        var local = preference.get("directory", "local" to "") as String
        if (local == "") local = Gson().toJson(MFile("/", type = DIR, size = 0))

        //获取网络 -> 成功 -> 覆盖本地 -> 失败使用本地
        thread {
            val raw: String = Downloader.simpleDownload(Constant.baseLink + "dir")
            root = if (raw != "") (Gson().fromJson(raw, MFile::class.java))
            else Gson().fromJson(local, MFile::class.java)
            Log.e("DirectoryManager", raw)
        }


    }


    fun getDirectory(path: String) = root.getDirectory(path)

    fun mkdir(dir: MFile) {
        root.create(dir)
        save()
    }

    //本地加网络save
    fun save() {
        val json = createJson()
        thread {
            preference.save("directory") {
                "local" - json
            }
            saveToremote.invoke(json)
        }
    }


    fun createJson() = root.toJson()


}



