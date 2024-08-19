package org.twinkle.allpermission

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.twinkle.permission_library.PermissionHandler
import org.twinkle.permission_library.Permissions


class AllPermissionActicity : AppCompatActivity() {

    private val permissionHelperClass by lazy { PermissionHelperClass(this) }
    private val REQUEST_CODE_PERMISSIONS = 1001
    private val REQUIRED_PERMISSIONS = arrayOf(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_permission_acticity)

        /*if (permissionHelperClass.hasPermissionList(REQUIRED_PERMISSIONS)) {
            Toast.makeText(this, "All permission Granted.", Toast.LENGTH_SHORT).show()
        } else {
            permissionHelperClass.requestPermissionForActivity(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }*/

        val camera: Button = findViewById(R.id.button)
        camera.setOnClickListener {
            /*if (permissionHelperClass.hasPermission(android.Manifest.permission.CAMERA)) {
                Toast.makeText(this, "camera permission Granted.", Toast.LENGTH_SHORT).show()
                if (permissionHelperClass.hasPermissionList(REQUIRED_PERMISSIONS)) {
                    sharedMethod()
                }
            } else {
                permissionHelperClass.permissionAlertDialog(
                    this,
                    "Permissions Required",
                    "You have denied camera permission permanently. Please allow the app settings.",
                    "Go to Settings",
                    {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            data = Uri.fromParts("package", packageName, null)
                        }
                        startActivity(intent)
                    },
                    "Cancel",
                    {
                        permissionHelperClass.shouldShowRationaleForActivity(
                            this,
                            android.Manifest.permission.CAMERA
                        )
                    },
                    false
                )
            }*/

            val rationale = "Please provide CAMERA permission so that you can ..."
            val options: Permissions.Options = Permissions.Options()
                .setRationaleDialogTitle("Info")
                .setSettingsDialogTitle("Warning")

            Permissions.check(this, false, arrayOf("CAMERA"), rationale, options, object : PermissionHandler() {
                override fun onGranted() {
                    Toast.makeText(this@AllPermissionActicity, "CAMERA granted.", Toast.LENGTH_SHORT).show()
                }

                override fun onDenied(context: Context?, deniedPermissions: ArrayList<String?>) {
                    Toast.makeText(this@AllPermissionActicity, "CAMERA permission Denied.", Toast.LENGTH_SHORT).show()
                }
            })

        }

        val location: Button = findViewById(R.id.button2)
        location.setOnClickListener {
            /*if (permissionHelperClass.hasPermissionList(arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ))) {
                Toast.makeText(this, "location permission Granted.", Toast.LENGTH_SHORT).show()
                if (permissionHelperClass.hasPermissionList(REQUIRED_PERMISSIONS)) {
                    sharedMethod()
                }
            } else {
                permissionHelperClass.permissionAlertDialog(
                    this,
                    "Permissions Required",
                    "You have denied location permission permanently. Please allow the app settings.",
                    "Go to Settings",
                    {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            data = Uri.fromParts("package", packageName, null)
                        }
                        startActivity(intent)
                    },
                    "Cancel",
                    {
                        permissionHelperClass.shouldShowRationaleForActivity(
                            this,
                            arrayOf(
                                android.Manifest.permission.ACCESS_FINE_LOCATION,
                                android.Manifest.permission.ACCESS_COARSE_LOCATION
                            ).toString()
                        )
                    },
                    false
                )
            }*/

//            val permissions = arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION)
            val rationale = "Please provide location permission so that you can ..."
            val options: Permissions.Options = Permissions.Options()
                .setRationaleDialogTitle("Info")
                .setSettingsDialogTitle("Warning")

            Permissions.check(this, false, arrayOf("LOCATION"), rationale, options, object : PermissionHandler() {
                    override fun onGranted() {
                        Toast.makeText(this@AllPermissionActicity, "Location granted.", Toast.LENGTH_SHORT).show()
                    }

                    override fun onDenied(context: Context?, deniedPermissions: ArrayList<String?>) {
                        Toast.makeText(this@AllPermissionActicity, "Location permission Denied.", Toast.LENGTH_SHORT).show()
                    }
                })
        }

        val storage: Button = findViewById(R.id.button3)
        storage.setOnClickListener {
            /*if (permissionHelperClass.hasPermissionList(arrayOf(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ))) {
                Toast.makeText(this, "storage permission Granted.", Toast.LENGTH_SHORT).show()
                if (permissionHelperClass.hasPermissionList(REQUIRED_PERMISSIONS)) {
                    sharedMethod()
                }
            } else {
                permissionHelperClass.permissionAlertDialog(
                    this,
                    "Permissions Required",
                    "You have denied storage permission permanently. Please allow the app settings.",
                    "Go to Settings",
                    {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            data = Uri.fromParts("package", packageName, null)
                        }
                        startActivity(intent)
                    },
                    "Cancel",
                    {
                        permissionHelperClass.shouldShowRationaleForActivity(
                            this,
                            arrayOf(
                                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                            ).toString()
                        )
                    },
                    false
                )
            }*/
            val rationale = "Please provide storage permission so that you can ..."
            val options: Permissions.Options = Permissions.Options()
                .setRationaleDialogTitle("Info")
                .setSettingsDialogTitle("Warning")

            Permissions.check(
                this,  false,
                arrayOf(
                    "STORAGE"
                ),
                rationale,
                options,
                object : PermissionHandler() {
                    override fun onGranted() {
                        Toast.makeText(
                            this@AllPermissionActicity,
                            "storage granted.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onDenied(
                        context: Context?,
                        deniedPermissions: ArrayList<String?>
                    ) {
                        Toast.makeText(this@AllPermissionActicity, "storage permission Denied.", Toast.LENGTH_SHORT).show()
                    }
                })
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

                if (permissionHelperClass.hasPermissionList(REQUIRED_PERMISSIONS)) {
                    sharedMethod()
                }


            } else {

                permissionHelperClass.permissionAlertDialog(this,
                    "Permission Required",
                    "All permission required",
                    "Yes",
                    {
                        permissionHelperClass.shouldShowRationaleForActivity(
                            this,
                            permissions.toString()
                        )
                    },
                    "No",
                    {
                        permissionHelperClass.permissionAlertDialog(
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
                            },
                            false
                        )

                    },
                    false
                )

            }
        }
    }*/

    fun sharedMethod() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()
    }
}