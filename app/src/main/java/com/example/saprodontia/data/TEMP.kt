
import android.os.Environment
import com.example.saprodontia.Constant.Constant
import com.google.gson.JsonObject
import com.google.gson.JsonArray
import com.google.gson.JsonPrimitive
import java.io.File

//package com.example.saprodontia.data
//
//
//import com.google.gson.Gson
//import com.google.gson.JsonObject
//import com.google.gson.JsonArray
//import com.google.gson.JsonPrimitive
//import java.io.File
//
//
///**
// * Created by steve on 17-11-28.
// */
//
//fun createJson(root: String): JsonObject {
//    val element = JsonPrimitive(root)
//    val filearray = JsonArray()
//    val dirarray = JsonArray()
//    val json = JsonObject()
//    val file = File(root)
//    json.add("path", element)
//    val files = file.listFiles()
//    for (f in files) {
//        if (f.isFile()) {
//            filearray.add(f.getPath())
//        } else {
//            dirarray.add(createJson(f.getPath()))
//        }
//    }
//    json.add("files", filearray)
//    json.add("directorys", dirarray)
//    return json
//}
//
//
//
//fun createFile(path : String){
//
//}
//



//
//data class MFile( val path : String ,
//                  val fileName : String )
//
//
//
//
//class Directory(val type  ){
//
//
//
//}


