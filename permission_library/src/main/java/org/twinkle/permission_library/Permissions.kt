package org.twinkle.permission_library

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.Switch
import org.twinkle.permission_library.PermissionsActivity.Companion.permissionHandler
import java.io.Serializable


object Permissions {
    var loggingEnabled: Boolean = true

    fun disableLogging() {
        Permissions.loggingEnabled = false
    }

    fun log(message: String?) {
        if (Permissions.loggingEnabled) Log.d("Permissions", message!!)
    }

    fun check(
        context: Context?, permission: String?, rationale: String?,
        handler: PermissionHandler?
    ) {
        check(
            context!!, true,  arrayOf(permission), rationale, null,
            handler!!
        )
    }

    fun check(
        context: Context, permission: String?, rationaleId: Int,
        handler: PermissionHandler?
    ) {
        var rationale: String? = null
        try {
            rationale = context.getString(rationaleId)
        } catch (ignored: Exception) {
        }
        check(
            context, arrayOf(permission), rationale, null,
            handler!!
        )
    }

    fun check(
        context: Context, permissions: Array<String?>, rationale: String?,
        options: Options?, handler: PermissionHandler
    ) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            handler.onGranted()
            log("Android version < 23")
        } else {

            val permissionsSet: MutableSet<String> = LinkedHashSet()
            for (perm in permissions) {
                if (perm != null) {
                    permissionsSet.add(perm)
                }
            }

            var allPermissionProvided = true
            for (aPermission in permissionsSet) {
                if (context.checkSelfPermission(aPermission) != PackageManager.PERMISSION_GRANTED) {
                    allPermissionProvided = false
                    break
                }
            }

            if (allPermissionProvided) {
                handler.onGranted()
                log("Permission(s) " + (if (permissionHandler == null) "already granted." else "just granted from settings."))
                permissionHandler = null
            } else {
                permissionHandler = handler
                val permissionsList = ArrayList(permissionsSet)

                val intent: Intent = Intent(
                    context,
                    PermissionsActivity::class.java
                )
                    .putExtra(PermissionsActivity.EXTRA_PERMISSIONS, permissionsList)
                    .putExtra(PermissionsActivity.EXTRA_RATIONALE, rationale)
                    .putExtra(PermissionsActivity.EXTRA_OPTIONS, options)
                if (options != null && options.createNewTask) {
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(intent)
            }
        }
    }

    fun check(
        context: Context, custom: Boolean, permissions: Array<String?>, rationale: String?,
        options: Options?, handler: PermissionHandler
    ) {

        val permissionsSet: MutableSet<String> = LinkedHashSet()

        if (custom) {
            for (perm in permissions) {
                if (perm != null) {
                    permissionsSet.add(perm)
                }
            }
        } else {
            for (per in permissions) {
                when(per) {

                    "CAMERA" -> {
                        permissionsSet.add(android.Manifest.permission.CAMERA)
                    }

                    "LOCATION" -> {
                        permissionsSet.add(android.Manifest.permission.ACCESS_FINE_LOCATION)
                        permissionsSet.add(android.Manifest.permission.ACCESS_COARSE_LOCATION)
                    }

                    "STORAGE" -> {
                        permissionsSet.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        permissionsSet.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    }

                    "CONTACTS" -> {
                        permissionsSet.add(android.Manifest.permission.READ_CONTACTS)
                        permissionsSet.add(android.Manifest.permission.WRITE_CONTACTS)
                    }

                    "PHONE" -> {
                        permissionsSet.add(android.Manifest.permission.READ_PHONE_STATE)
                        permissionsSet.add(android.Manifest.permission.CALL_PHONE)
                        permissionsSet.add(android.Manifest.permission.READ_CALL_LOG)
                        permissionsSet.add(android.Manifest.permission.WRITE_CALL_LOG)
                        permissionsSet.add(android.Manifest.permission.ADD_VOICEMAIL)
                    }

                    "SMS" -> {
                        permissionsSet.add(android.Manifest.permission.SEND_SMS)
                        permissionsSet.add(android.Manifest.permission.READ_SMS)
                        permissionsSet.add(android.Manifest.permission.RECEIVE_SMS)
                        permissionsSet.add(android.Manifest.permission.RECEIVE_MMS)
                        permissionsSet.add(android.Manifest.permission.RECEIVE_BOOT_COMPLETED)
                    }

                    "MIC" -> {
                        permissionsSet.add(android.Manifest.permission.RECORD_AUDIO)
                    }

                    "CALENDER" -> {
                        permissionsSet.add(android.Manifest.permission.READ_CALENDAR)
                        permissionsSet.add(android.Manifest.permission.WRITE_CALENDAR)
                    }

                    "SENSOR" -> {
                        permissionsSet.add(android.Manifest.permission.READ_CONTACTS)
                    }

                    "NOTIFICATION" -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            permissionsSet.add(android.Manifest.permission.POST_NOTIFICATIONS)
                        }
                    }
                }

            }
        }



        var allPermissionProvided = true
        for (aPermission in permissionsSet) {
            if (context.checkSelfPermission(aPermission) != PackageManager.PERMISSION_GRANTED) {
                allPermissionProvided = false
                break
            }
        }

        if (allPermissionProvided) {
            handler.onGranted()
            log("Permission(s) " + (if (permissionHandler == null) "already granted." else "just granted from settings."))
            permissionHandler = null
        } else {
            permissionHandler = handler
            val permissionsList = ArrayList(permissionsSet)

            val intent: Intent = Intent(
                context,
                PermissionsActivity::class.java
            )
                .putExtra(PermissionsActivity.EXTRA_PERMISSIONS, permissionsList)
                .putExtra(PermissionsActivity.EXTRA_RATIONALE, rationale)
                .putExtra(PermissionsActivity.EXTRA_OPTIONS, options)
            if (options != null && options.createNewTask) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        }
    }

    fun check(
        context: Context, permissions: Array<String?>?, rationaleId: Int,
        options: Options?, handler: PermissionHandler?
    ) {
        var rationale: String? = null
        try {
            rationale = context.getString(rationaleId)
        } catch (ignored: Exception) {
        }
        check(context, permissions!!, rationale, options, handler!!)
    }


    class Options : Serializable {
        var settingsText: String = "Settings"
        var rationaleDialogTitle: String = "Permissions Required"
        var settingsDialogTitle: String = "Permissions Required"
        var settingsDialogMessage: String = "Required permission(s) have been set not to ask again! Please provide them from settings."
        var sendBlockedToSettings: Boolean = true
        var createNewTask: Boolean = false

        fun setSettingsText(settingsText: String): Options {
            this.settingsText = settingsText
            return this
        }

        fun setCreateNewTask(createNewTask: Boolean): Options {
            this.createNewTask = createNewTask
            return this
        }

        fun setRationaleDialogTitle(rationaleDialogTitle: String): Options {
            this.rationaleDialogTitle = rationaleDialogTitle
            return this
        }

        fun setSettingsDialogTitle(settingsDialogTitle: String): Options {
            this.settingsDialogTitle = settingsDialogTitle
            return this
        }

        fun setSettingsDialogMessage(settingsDialogMessage: String): Options {
            this.settingsDialogMessage = settingsDialogMessage
            return this
        }

        fun sendDontAskAgainToSettings(send: Boolean): Options {
            sendBlockedToSettings = send
            return this
        }
    }
}
