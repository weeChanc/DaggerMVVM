//package com.example.saprodontia.modules
//
//import com.google.gson.Gson
//import java.io.Serializable
//
///**
// * Created by steve on 17-11-28.
// */
//data class Directory ( var path: String ="/" , val directories : MutableList<Directory?> = mutableListOf()): Serializable{
//
//
//    fun getDirectory(root : Directory = getRoot() , path : String):Directory?{
//        if(root.path == path) return root
//        else {
//            root.directories.forEachIndexed{
//                index, directory ->
//                if(index > 0){
//                    return getDirectory(directory!!,path)
//                }
//            }
//        }
//        return null
//    }
//
//    fun createDirectory(root : Directory = getRoot() , dir : Directory){
//        val parentPath = dir.path.substring(0,dir.path.substring(0,dir.path.length-1).lastIndexOf("/"))
//        if( root.path == parentPath){
//            root.directories.add(dir)
//        }else{
//            root.directories.forEachIndexed {
//                index , rd ->
//                if(index > 0 ) {
//                    createDirectory(rd!!, dir)
//                }
//            }
//        }
//    }
//
//    fun getRoot(): Directory {
//        var root: Directory = this.directories[0] ?: return this;
//
//        while( root.directories[0] != null){
//            root = root.directories[0]!!
//        }
//
//        return root;
//    }
//
//    override fun toString(): String {
//        return Gson().toJson(this)
//    }
//}