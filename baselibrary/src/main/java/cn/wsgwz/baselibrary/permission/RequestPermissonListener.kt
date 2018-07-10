package cn.wsgwz.baselibrary.permission

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.widget.Toast
import cn.wsgwz.baselibrary.R

interface RequestPermissonListener {
    val activity: Activity?
    val permission: Permission
    val requestCode: Int
    val whyRequestPermission:String
    fun success()
    fun refuse() {
        activity?.let {

            Toast.makeText(activity,String.format(it.resources.getString(R.string.no_permission), permission.description,whyRequestPermission),Toast.LENGTH_SHORT).show()
            /*AlertDialog.Builder(activity)
                    .setTitle(it.resources.getString(R.string.hint))
                    .setMessage(String.format(it.resources.getString(R.string.no_permission), permission.description,whyRequestPermission)       )
                    .setPositiveButton(it.resources.getString(R.string.ok), object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            //activity.finish()
                        }
                    })
                    .create().show()*/
        }

    }
}