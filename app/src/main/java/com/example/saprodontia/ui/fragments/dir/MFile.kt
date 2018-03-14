package com.xt.directoryfragment

import android.util.Log
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by steve on 18-1-28.
 */

data class MFile(@SerializedName("p") var path: String = "/",
                 @SerializedName("d") val directories: MutableList<MFile> = mutableListOf(),
                 @SerializedName("t") val type: String, @SerializedName("s") var size : Long = 0) : Serializable,Comparable<MFile>{
    override fun compareTo(other: MFile): Int {
        return this.path.compareTo( other.path)

    }

    companion object {
        val FILE = "FILE"
        val DIR = "DIR"
    }


    fun getDirectory(path: String, root: MFile = this): MFile? {
        if (root.path == path) return root
        else {
            root.directories.forEach {
                return getDirectory(path, it)
            }
        }
        return null
    }

    /**
     * 创建文件
     */
    fun create(dir: MFile, root: MFile = this) {
        val parentPath = dir.path.substring(0, dir.path.substring(0, dir.path.length - 1).lastIndexOf("/") + 1)
        if (root.path == parentPath) {
            root.directories.forEach {
                if (it.path == dir.path) {
                    Log.e("MFile", "已创建过 不再再创建")
                    return
                }
            }
            root.size++
            root.directories.add(dir)
        } else {
            root.directories.forEach {
                create(dir, it)
            }
        }
    }


    fun toJson(): String {
        return Gson().toJson(this)
    }
}
