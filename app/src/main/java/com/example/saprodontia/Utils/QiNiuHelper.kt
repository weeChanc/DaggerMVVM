package com.example.saprodontia.Utils

import android.support.annotation.WorkerThread
import com.example.saprodontia.Constant.Constant
import com.qiniu.android.storage.Configuration
import com.qiniu.android.storage.KeyGenerator
import com.qiniu.android.storage.UpCancellationSignal
import com.qiniu.android.storage.UpProgressHandler
import com.qiniu.android.storage.UploadManager
import com.qiniu.android.storage.UploadOptions
import com.qiniu.android.storage.persistent.FileRecorder
import com.qiniu.common.QiniuException
import com.qiniu.storage.BucketManager
import com.qiniu.util.Auth
import java.util.ArrayList

/**
 * Created by steve on 17-11-28.
 */
public class QiniuHelper{
    companion object {
        private val keyGen = KeyGenerator { key, file -> key + "_._" + StringBuffer(file.absolutePath).reverse() }
        private val recorder = FileRecorder(Constant.tempDir)
        private val config = Configuration.Builder().recorder(recorder, keyGen).build()
        private val uploadManager = UploadManager(config)
        private val upToken = createUpToken(Constant.AccessKey, Constant.secretKey, Constant.PhotoBucket)


        private fun createUpToken(accessKey: String, secretKey: String, bucket: String): String {
            val auth = Auth.create(accessKey, secretKey)
            return auth.uploadToken(bucket)
        }

        fun upload(path : String ,name : String ,
                   onSuccess:(String)->Unit,
                   onFailed : (String) -> Unit,
                   onProgressChanged:(String,Double)->Unit = {_,_->}){

            uploadManager.put(path,name,upToken, { key, response, json ->
                if(response.isOK) onSuccess(key) else onFailed(key)},
                    UploadOptions(null,null,false,
                            UpProgressHandler{ key,present -> onProgressChanged(key,present)},
                            UpCancellationSignal { false }))
        }

        fun listFile(directory : String =""): Array<com.qiniu.storage.model.FileInfo>? {
            val auth = Auth.create(Constant.AccessKey, Constant.secretKey)
            val bucketManager = BucketManager(auth)
            try {
                val fileListing = bucketManager.listFiles("photo", directory, null, 2000,"/")
                return fileListing.items
            } catch (e: QiniuException) {
                e.printStackTrace()
                return null
            }

        }

        @WorkerThread
        fun getImageSourceKey(): ArrayList<String> {
            val keys = ArrayList<String>()
            val qiniuUtils = QiniuUtils()
            val fileInfos = qiniuUtils.listFile()
            for (i in fileInfos!!.indices) {
                if (fileInfos[i].mimeType.contains("image")) {
                    keys.add(fileInfos[i].key)
                    LogUtil.e(fileInfos[i].key)
                }
            }
            return keys
        }

    }
}