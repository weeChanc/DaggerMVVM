package com.example.saprodontia.data

import com.xt.directoryfragment.MFile


/**
 * Created by steve on 17-11-28.
 */
interface DirectoryContract {



    interface Local{
        fun getDirectory() : MFile
        fun updateDirectory(directory : MFile)
    }

    interface Remote{



    }

}

