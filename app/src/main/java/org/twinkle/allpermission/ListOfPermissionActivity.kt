package org.twinkle.allpermission

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.twinkle.permission_library.PermissionHandler
import org.twinkle.permission_library.Permissions


class ListOfPermissionActivity : AppCompatActivity() {

    private val permissionHelperClass by lazy { PermissionHelperClass(this) }
    private val REQUEST_CODE_PERMISSIONS = 1001
    private val REQUIRED_PERMISSIONS = arrayOf(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_permission)

        sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val value = sharedPreferences.getBoolean("isLoggedIn", false)
        if (value) {
            startActivity(Intent(this, AllPermissionActicity::class.java))
            finish()
        } else {

            val cancel: Button = findViewById(R.id.button4)
            val continueButton: Button = findViewById(R.id.button5)

            cancel.setOnClickListener {
                startActivity(Intent(this, AllPermissionActicity::class.java))
                finish()
            }

            continueButton.setOnClickListener {

                /*if (permissionHelperClass.hasPermissionList(REQUIRED_PERMISSIONS)) {
                    saveBooleanValue("isLoggedIn", true)
                    Toast.makeText(this, "All permission Granted.", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, AllPermissionActicity::class.java))
                    finish()
                } else {
                    permissionHelperClass.requestPermissionForActivity(
                        this,
                        REQUIRED_PERMISSIONS,
                        REQUEST_CODE_PERMISSIONS
                    )



                }*/

                Permissions.check(this, true, arrayOf(
//                    "CAMERA", "STORAGE", "LOCATION"
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ), null, null, object : PermissionHandler() {
                    override fun onGranted() {
                        Toast.makeText(this@ListOfPermissionActivity, "All permission Granted.", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@ListOfPermissionActivity, AllPermissionActicity::class.java))
                        finish()
                    }

                })

            }
        }



    }

    /*override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                Toast.makeText(this, "All Permission Granted.", Toast.LENGTH_SHORT).show()
                saveBooleanValue("isLoggedIn", true)
                startActivity(Intent(this, AllPermissionActicity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Permission denied.", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, AllPermissionActicity::class.java))
                finish()
                *//*permissionHelperClass.permissionAlertDialog(
                    this,
                    "Permissions Required",
                    "You have denied some permissions permanently. Please allow them in the app settings.",
                    "Go to Settings",
                    {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            data = Uri.fromParts("package", packageName, null)
                        }
                        startActivity(intent)
                    },
                    "Cancel",
                    {
                        Toast.makeText(this, "All Permission Denied.", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, AllPermissionActicity::class.java))
                        finish()
                    },
                    false
                )*//*
            }
        }
    }*/


    fun saveBooleanValue(key: String, value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBooleanValue(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false) // default value is false if the key doesn't exist
    }

}