//package com.example.saprodontia.data
//
//import android.util.Log
//import com.example.saprodontia.Constant.Constant
//import com.example.saprodontia.Utils.QiniuHelper
//import com.example.saprodontia.data.Directory.Companion.DIR
//import com.google.gson.Gson
//import com.google.gson.annotations.SerializedName
//import com.mobile.utils.Preference
//import com.mobile.utils.downloader.Downloader
//import com.mobile.utils.showToast
//import com.mobile.utils.toggleDir
//import com.mobile.utils.toggleFile
//import java.io.*
//import java.util.concurrent.Executors
//import kotlin.concurrent.thread
//
///**
// * Created by steve on 17-11-28.
// */
//
//object DirectoryManager {
//
//
//    lateinit var root: Directory
//    val fixPool = Executors.newFixedThreadPool(1)
//
////    init {
//        //获取本地
//        var local = Preference.get("directory", "local" to "") as String
//        if (local == "") local = Gson().toJson(Directory("/", type = DIR,size = 0))
//
//        fixPool.execute {
//            //获取网络 -> 成功 -> 覆盖本地 -> 失败使用本地
//            val raw: String = Downloader.simpleDownload(Constant.baseLink + "dir")
//            root = if (raw != "") (Gson().fromJson(raw, Directory::class.java))
//            else Gson().fromJson(local, Directory::class.java)
//        }
//
//    }
////
////    private fun refreshDirectory(root: Directory) {
////
////        fixPool.execute {
////            QiniuHelper.listFile(root.path)?.forEach {
////                Log.e("DirectoryManager",it.key)
////                showToast(it.key)
////                root.directories.add(Directory(root.path + it.key, type = it.mimeType))
////            }
////        }
////
////        root.directories.forEach {
////            if(it.type == DIR) {
////                refreshDirectory(it)
////            }
////        }
////    }
//
//    fun getDirectory(path: String) = root.getDirectory(path)
//
//    fun mkdir(dir: Directory, callback: (success: Boolean) -> Unit) {
//        root.createDirectory(dir)
//        save(callback)
//    }
//
//    //本地加网络save
//    fun save(callback: (success: Boolean) -> Unit) {
//        val json = createJson()
//        thread {
//            File(Constant.STRUCTURE).toggleDir()
//            val file = File(Constant.STRUCTURE + "/dirStructor.txt")
//            file.toggleFile()
//            val os = file.outputStream()
//            os.write(json.toByteArray())
//            os.close()
//
//            QiniuHelper.overwriteUpload(Constant.STRUCTURE + "/dirStructor.txt", "dir",
//                    onSuccess = {
//                        callback.invoke(true);
//                        Preference.save("directory") { "local" - json }
//                    },
//
//                    onFailed = { callback.invoke(false) })
//        }
//    }
//
//
//    fun createJson() = root.toJson()
//
//
//}
//
//
//data class Directory(@SerializedName("p") var path: String = "/",
//                     @SerializedName("d") val directories: MutableList<Directory> = mutableListOf(),
//                     @SerializedName("t") val type: String,@SerializedName("s") val size : Long = 0, @Transient var progress : Double = 0.0) : Serializable ,Comparable<Directory>{
//    override fun compareTo(other: Directory): Int {
//        return this.path.compareTo( other.path)
//    }
//
//    companion object {
//        val FILE = "FILE"
//        val DIR = "DIR"
//    }
//
//
//    fun getDirectory(path: String, root: Directory = this): Directory? {
//        if (root.path == path) return root
//        else {
//            root.directories.forEach {
//                return getDirectory(path, it)
//            }
//        }
//        return null
//    }
//
//    fun createDirectory(dir: Directory, root: Directory = this) {
//        val parentPath = dir.path.substring(0, dir.path.substring(0, dir.path.length - 1).lastIndexOf("/") + 1)
//        if (root.path == parentPath) {
//            root.directories.forEach {
//                if (it.path == dir.path) {
//                    Log.e("Directory", "已创建过 不再再创建")
//                    return
//                }
//            }
//            root.directories.add(dir)
//        } else {
//            root.directories.forEach {
//                createDirectory(dir, it)
//            }
//        }
//    }
//
//    fun toJson(): String {
//        return Gson().toJson(this)
//    }
//}
//
//
