package org.twinkle.allpermission

import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class PermissionHelperClass(private val context: Context) {

    fun hasPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }

    fun hasPermissionList(permissionList: Array<String>): Boolean {
        return permissionList.all { hasPermission(it) }
    }

    fun requestPermissionForActivity(activity: AppCompatActivity, permissionList: Array<String>, requestCode: Int) {
        ActivityCompat.requestPermissions(activity, permissionList, requestCode)
    }

    fun requestPermissionForFragment(fragment: Fragment, permissionList: Array<String>, requestCode: Int) {
        fragment.requestPermissions(permissionList, requestCode)
    }

    fun shouldShowRationaleForActivity(activity: AppCompatActivity, permission: String): Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
    }

    fun shouldShowRationaleForFragment(fragment: Fragment, permission: String): Boolean {
        return fragment.shouldShowRequestPermissionRationale(permission)
    }

    fun permissionAlertDialog(context: Context, title: String? = null, message: String? = null, positiveButtonText: String = "Ok", positiveButtonAction: (() -> Unit)? = null, negativeText: String? = null, negativeButtonAction: (() -> Unit)? = null, isCancelable:Boolean = true) {
        val builder = AlertDialog.Builder(context)
        title?.let { builder.setTitle(it) }
        message?.let { builder.setMessage(it) }
        builder.setPositiveButton(positiveButtonText) { dialog, _ ->
            positiveButtonAction?.invoke()
            dialog.dismiss()
        }
        negativeText?.let {
            builder.setNegativeButton(it) { dialog, _ ->
                negativeButtonAction?.invoke()
                dialog.dismiss()
            }
        }
        builder.setCancelable(isCancelable)
        val dialog = builder.create()
        dialog.show()
    }
}