package com.example.saprodontia.modules

/**
 * Created by steve on 17-11-28.
 */
data class Directory( var path: String ="/" , val directories : MutableList<Directory> = mutableListOf())