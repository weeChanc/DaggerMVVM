package com.example.saprodontia.data

import com.example.saprodontia.modules.Directory

/**
 * Created by steve on 17-11-28.
 */
interface DirectoryContract {



    interface Local{
        fun getDirectory() : Directory
        fun updateDirectory(directory : Directory)
    }

    interface Remote{



    }

}

