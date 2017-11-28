package com.example.saprodontia.ui.fragments.media

import android.os.Bundle

/**
 * Created by steve on 17-11-26.
 */
enum class MediaFragmentType {

    APPS, DOCUMENT, MUSIC, PHOTO, VIDEO;

    fun createFrament(): MediaFragment {
        val fragment = MediaFragment()
        val bundle = Bundle()
        bundle.putSerializable("type",this)
        fragment.arguments = bundle
        return fragment

    }
}