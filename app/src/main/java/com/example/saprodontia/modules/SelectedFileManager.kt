package com.example.saprodontia.modules

import kotlin.properties.Delegates

/**
 * Created by steve on 17-11-26.
 */
object SelectedFileManager {

    private val checkedFile = mutableListOf<FileInfo>()

    var selectedCount : Int by Delegates.observable(0){
        property, oldValue, newValue ->
        countChanged.forEach{ it.invoke(oldValue , newValue)}
    }

    fun add( fileInfo : FileInfo){
        if(!checkedFile.contains(fileInfo)) {
            if (checkedFile.add(fileInfo)) {
                selectedCount++
            }
        }

    }

    fun remove ( fileInfo : FileInfo){
        if(checkedFile.remove(fileInfo)){
            selectedCount--
        }
    }

    fun clear (){
        checkedFile.clear()
        selectedCount = 0
    }

    fun getCheckFile()= checkedFile.toMutableList()

    fun contains( fileInfo : FileInfo) = checkedFile.contains(fileInfo)

    var countChanged : MutableList<((Int,Int)->Unit)> = mutableListOf()
    fun addOnCountChangedListener( onCountChanged : ((Int, Int)-> Unit)){
        countChanged.add(onCountChanged)
    }
    fun removeOnCountChangedListener( listener:((Int,Int)-> Unit)){
        countChanged.remove(listener)
    }

}