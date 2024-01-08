package com.agamya.sangeetduniya

import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var bottomnav: BottomNavigationView

    companion object{
        var mediaplayer:MediaPlayer? = null
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomnav = findViewById(R.id.botnav)

        if (mediaplayer==null){
            mediaplayer = MediaPlayer()
        }

        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_MEDIA_AUDIO)== PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
            //Baad me
        }else{
            requestPermission()
        }


        replaceFragment(OnlineMusic())



        bottomnav.setOnItemSelectedListener {
            if (it.itemId==R.id.local){
                replaceFragment(LocalMusic())
            }else if (it.itemId == R.id.online){
                replaceFragment(OnlineMusic())
            }else if (it.itemId == R.id.download){
                replaceFragment(Downloads())
            }

            true
        }
    }

    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.container,fragment).commit()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestPermission(){
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_MEDIA_AUDIO,android.Manifest.permission.READ_EXTERNAL_STORAGE),1)
    }

}