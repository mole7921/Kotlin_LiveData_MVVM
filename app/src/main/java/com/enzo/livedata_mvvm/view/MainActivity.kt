package com.enzo.livedata_mvvm.view

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.enzo.livedata_mvvm.R
import com.enzo.livedata_mvvm.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    //This property only valid between onCreateView and onDestoryView
    private val binding: ActivityMainBinding get()= _binding!!
    private val requestCode = 1
    private val permissionStorage = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS
    )

    private fun verifyStoragePermissions(activity: Activity?) {
        // Check if we have write permission
        val permission = ActivityCompat.checkSelfPermission(
                activity!!,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    permissionStorage,
                    requestCode
            )
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        verifyStoragePermissions(this)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }




    /*
        In Android 10, if you press back to finish an activity which is a task root,
    that activity will leak, This leak is happening because IRequestFinishCallback$Stub
    is an IPC callback that ends up being held by the activity manager process.
        We can "fix" this leak by overriding Activity.onBackPressed() and calling
    Activity.finishAfterTransition() instead of super if the activity is task root and
    the fragment stack is empty.
     */
    override fun onBackPressed() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment)!!
        if(isTaskRoot
                && navHostFragment.childFragmentManager.backStackEntryCount == 0
                && supportFragmentManager.backStackEntryCount == 0
                && Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
            finishAfterTransition()
        }else{
            super.onBackPressed()
        }
    }

}