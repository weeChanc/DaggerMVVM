package com.example.saprodontia.Activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.saprodontia.Application.App

import com.example.saprodontia.R
import com.example.saprodontia.di.component.ActivityComponent
import com.example.saprodontia.di.modules.ActivityModule
import javax.inject.Inject


/**
 * Created by steve on 17-11-22.
 */
open class BaseActivity : AppCompatActivity() {


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.download -> startActivity(Intent(this@BaseActivity, Cloud::class.java))

//            R.id.upload -> startActivity(Intent(this@BaseActivity, UpLoadActivity::class.java))
        }
        return true
    }

}