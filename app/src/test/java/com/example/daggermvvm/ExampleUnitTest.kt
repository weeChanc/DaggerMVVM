package com.example.daggermvvm

import android.util.Log
import com.example.saprodontia.Utils.QiniuHelper
import com.example.saprodontia.modules.Directory
import com.google.gson.Gson
import com.mobile.utils.Preference
import org.junit.Test

import org.junit.Assert.*
import java.io.File
import java.io.Serializable

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {

        val manager = DirectoryManager()
        manager.mkdir(DirectoryManager.Directory("/home/"))
        print(manager.createJson())
    }


    class DirectoryManager(){

        val root  : Directory by lazy {
            val raw = Preference.get("currentDir","value" to "") as String
            if (raw != "")( Gson().fromJson(raw,Directory::class.java)) else Directory()
        }

        fun getDirectory(path : String) = root.getDirectory(path)

        fun mkdir(dir : Directory) {
            root.createDirectory(dir)
            Preference.save("currentDir"){
                "value" - createJson()
            }
        }

        fun createJson() = root.toJson()


        data class Directory ( var path: String ="/" , val directories : MutableList<Directory> = mutableListOf()): Serializable {


            fun getDirectory( path : String,root : Directory = this ): Directory?{
                if(root.path == path) return root
                else {
                    root.directories.forEach{
                        return getDirectory(path , it)
                    }
                }
                return null
            }

            fun createDirectory(dir : Directory, root : Directory  = this){
                val parentPath = dir.path.substring(0,dir.path.substring(0,dir.path.length-1).lastIndexOf("/")+1)
                if( root.path == parentPath){
                    root.directories.forEach {
                        if(it.path == dir.path){
                            Log.e("Directory","已创建过 不再再创建")
                            return
                        }
                        root.directories.add(dir)
                    }
                }else{
                    root.directories.forEach {
                        createDirectory(dir , it)
                    }
                }
            }

            fun toJson(): String {
                return Gson().toJson(this)
            }
        }
    }


}
