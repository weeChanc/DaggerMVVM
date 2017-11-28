package com.example.saprodontia.modules

import android.content.ContentResolver
import android.content.pm.ApplicationInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.provider.MediaStore
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.saprodontia.Application.App
import com.example.saprodontia.R
import com.example.saprodontia.Utils.MathUtil
import com.mobile.utils.toDrawable
import io.reactivex.Observable
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import java.io.File
import kotlin.properties.Delegates

/**
 * Created by steve on 17-11-22.
 */

class ContentModules {

    companion object {

        val excel = App.app.getDrawable(R.drawable.excel)
        val word = App.app.getDrawable(R.drawable.word)
        val pdf = App.app.getDrawable(R.drawable.pdf)
        val ppt = App.app.getDrawable(R.drawable.powerpoint)
        val unknow = App.app.getDrawable(R.drawable.unknow)
        val music = App.app.getDrawable(R.drawable.music)

        
        private val mContentResolver: ContentResolver = App.app.contentResolver

        fun getMusic(): Observable<FileInfo> {

            val projection = arrayOf(MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.SIZE)
            val cursor = mContentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    projection, null, null, MediaStore.Audio.Media.DATE_MODIFIED + " desc")

            return Observable.create<FileInfo> { emitter ->

                cursor.moveToFirst()
                do {

                    val initSize = (cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE))).toLong()
                    val size = MathUtil.bytoKbOrMb(initSize)
                    val name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                    val icon = music
                    val location = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))

                    val info = FileInfo(name,location,size,initSize,icon=icon)

                    emitter.onNext(info)

                } while (cursor.moveToNext())

                emitter.onComplete()
                cursor.close()

            }
        }

        fun getDocument(): Observable<FileInfo> {

            val projection = arrayOf(MediaStore.Files.FileColumns.DATA, MediaStore.Files.FileColumns.MIME_TYPE, MediaStore.Files.FileColumns.SIZE, MediaStore.Files.FileColumns.TITLE)

            val selection = (MediaStore.Files.FileColumns.MIME_TYPE + "= ? "
                    + " or " + MediaStore.Files.FileColumns.MIME_TYPE + " = ? "
                    + " or " + MediaStore.Files.FileColumns.MIME_TYPE + " = ? "
                    + " or " + MediaStore.Files.FileColumns.MIME_TYPE + " = ? ")

            val selectionArgs = arrayOf("application/msword", "application/pdf", "application/vnd.ms-powerpoint", "application/vnd.ms-excel")

            val cursor = mContentResolver.query(MediaStore.Files.getContentUri("external"), projection, selection, selectionArgs, MediaStore.Files.FileColumns.DATE_MODIFIED + " desc")

            return Observable.create<FileInfo> { emitter ->

                if (cursor != null && cursor.moveToFirst()) {
                    do {


                        val location = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA))
                        val format = location.substring(location.indexOf('.'), location.length)
                        val initSize = java.lang.Long.parseLong(cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.SIZE)))
                        val size = MathUtil.bytoKbOrMb(initSize)
                        val name = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.TITLE))
                        val info = FileInfo(name,location,size,initSize)

                        when (format) {
                            ".xls" -> info.icon = excel
                            ".doc" -> info.icon = word
                            ".pdf" -> info.icon = pdf
                            ".ppt" -> info.icon = ppt
                            else -> info.icon = unknow
                        }

                        emitter.onNext(info)

                    } while (cursor.moveToNext())

                    emitter.onComplete()
                    cursor.close()

                }
            }


        }

        fun getVideo(): Observable<FileInfo> {

            val ops = BitmapFactory.Options()
            ops.inSampleSize = 4
            ops.inScaled = true


            val cursor = mContentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    arrayOf(MediaStore.Video.VideoColumns.DATA, MediaStore.Video.VideoColumns.TITLE,
                            MediaStore.Video.VideoColumns.SIZE, MediaStore.Video.VideoColumns._ID),
                    null, null, MediaStore.Video.VideoColumns.DATE_MODIFIED + "  desc")

            return Observable.create<FileInfo> { emitter ->

                if (cursor != null && cursor.moveToFirst()) {
                    do {


                        val location = cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.DATA))
                        val id = java.lang.Long.valueOf(cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns._ID)))

                        val name = cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.TITLE))
                        val initSize = java.lang.Long.parseLong(cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.SIZE)))
                        val size = MathUtil.bytoKbOrMb(initSize)
                        val icon = MediaStore.Video.Thumbnails.getThumbnail(mContentResolver, id, MediaStore.Video.Thumbnails.MINI_KIND, ops).toDrawable()

                        val info = FileInfo(name,location,size,initSize,id,icon)

                        emitter.onNext(info)

                    } while (cursor.moveToNext())
                }

                emitter.onComplete()
                cursor?.close()

            }

        }

        fun getPhotos(): Observable<FileInfo> {

            val addPath = HashSet<String>()

            val cursor = mContentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    arrayOf(MediaStore.Images.ImageColumns.DATA), null, null, MediaStore.Images.ImageColumns.DATA + "  desc")

            return Observable.create<FileInfo> { emitter ->

                if (cursor != null && cursor.moveToFirst()) {
                    do {

                        val location = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA))
                        val parentPath = File(location).parent
                        addPath.add(parentPath)
                    } while (cursor.moveToNext())
                }
                cursor.close()

                addPath.forEach{

                    val name = it.substring(it.lastIndexOf('/') + 1, it.length)
                    val location = it
                    val folderInfo = FileInfo(name,location)
                    emitter.onNext(folderInfo)
                }

                emitter.onComplete()

            }

        }

        fun getApplications(): Observable<FileInfo> {

            val pm = App.app.packageManager
            var sourceDir: String

            var infos = pm.getInstalledApplications(0)

            val info = infos.filter {
                it.flags and ApplicationInfo.FLAG_SYSTEM > 0
            }

            return Observable.create<FileInfo> { emitter ->
                
                for (i in info) {


                    sourceDir = i.sourceDir
                    val initSize = File(sourceDir).length()
                    val icon = i.loadIcon(pm)
                    val location = sourceDir
                    val name = i.loadLabel(pm).toString()
                    val size = MathUtil.bytoKbOrMb(initSize)
                    val app = FileInfo(name,location,size,initSize,icon=icon)
                    emitter.onNext(app)
                }

                emitter.onComplete()

            }

        }
    }


}