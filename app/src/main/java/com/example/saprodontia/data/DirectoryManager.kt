package com.example.saprodontia.data

import android.util.Log
import com.example.saprodontia.data.Directory.Companion.DIR
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.mobile.utils.Preference
import java.io.Serializable

/**
 * Created by steve on 17-11-28.
 */

class DirectoryManager(val type: Int) {

    companion object {
        val LOCAL = 1;
        val REMOTE = 2;
    }

    val root: Directory by lazy {
        var raw: String = ""
        when (type) {
            LOCAL -> raw = Preference.get("directory", "local" to "") as String
            REMOTE -> if(isDirty()) raw = Preference.get("directory", "local" to "") as String
            else raw = Preference.get("directory", "remote" to "") as String
        }

        if (raw != "") (Gson().fromJson(raw, Directory::class.java)) else Directory(type = DIR)
    }

    fun getDirectory(path: String) = root.getDirectory(path)

    fun mkdir(dir: Directory) {
        root.createDirectory(dir)
        save()
    }

    fun isDirty() = (Preference.get("directory", "dirty" to "true") as String).equals("true")

    fun setDirty(dirty: Boolean) {
        Preference.save("directory") {
            "dirty" - dirty.toString()
        }
    }

    fun save() {
        when (type) {
            LOCAL -> Preference.save("directory") {
                "local" - createJson()
            }

            REMOTE -> Preference.save("directory") {
                "remote" - createJson()
            }
        }
    }


    fun createJson() = root.toJson()


}


data class Directory(@SerializedName("p") var path: String = "/",
                     @SerializedName("d") val directories: MutableList<Directory> = mutableListOf(),
                     @SerializedName("t") val type: String) : Serializable {

    companion object {
        val FILE = "FILE"
        val DIR = "DIR"
    }


    fun getDirectory(path: String, root: Directory = this): Directory? {
        if (root.path == path) return root
        else {
            root.directories.forEach {
                return getDirectory(path, it)
            }
        }
        return null
    }

    fun createDirectory(dir: Directory, root: Directory = this) {
        val parentPath = dir.path.substring(0, dir.path.substring(0, dir.path.length - 1).lastIndexOf("/") + 1)
        if (root.path == parentPath) {
            root.directories.forEach {
                if (it.path == dir.path) {
                    Log.e("Directory", "已创建过 不再再创建")
                    return
                }
            }
            root.directories.add(dir)
        } else {
            root.directories.forEach {
                createDirectory(dir, it)
            }
        }
    }

    fun toJson(): String {
        return Gson().toJson(this)
    }
}


