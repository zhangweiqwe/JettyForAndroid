package cn.wsgwz.baselibrary.permission

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import cn.wsgwz.baselibrary.R

class ActivityWithPermission private constructor() : BaseWithPermisson<Activity>() {
    override lateinit var permissionList: ArrayList<RequestPermissonListener>
    override lateinit var t: Activity


    companion object {
        fun with(t: Activity, permissionList: ArrayList<RequestPermissonListener>?): BaseWithPermisson<Activity>? {
            if (permissionList == null) {
                return null
            }
            return ActivityWithPermission().apply {
                this.t = t
                this.permissionList = permissionList
            }
        }
    }


    private fun hintUserWhyRequestPermission(requestPermissonListener: RequestPermissonListener) {
        requestPermissonListener.activity?.let {

            val dialog = AlertDialog.Builder(it)
                    .setTitle(it.resources.getString(R.string.hint))
                    .setMessage(String.format(it.resources.getString(R.string.no_permission), requestPermissonListener.permission.description, requestPermissonListener.whyRequestPermission))
                    .setPositiveButton(it.resources.getString(R.string.authorization)) { _, _ ->
                        requestPermissonListener.permission.permission?.let {
                            ActivityCompat.requestPermissions(t,
                                    it, requestPermissonListener.requestCode)
                        }
                    }
                    .setNegativeButton(it.resources.getString(R.string.cancel), { _, _ ->
                        for (r in permissionList) {
                            if (requestPermissonListener.requestCode == r.requestCode) {
                                r.refuse()
                                permissionList.remove(r)
                                break
                            }
                        }
                    })
                    .create()
            if (!it.isFinishing) {
                dialog.show()
            }
        }
    }

    override fun doWithPermission(requestPermissonListener: RequestPermissonListener) {
        permissionList?.add(requestPermissonListener)
        requestPermissonListener.permission.permission?.let {
            // Here, thisActivity is the current activity
            if (ContextCompat.checkSelfPermission(t,
                            it[0]) != PackageManager.PERMISSION_GRANTED) {
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(t,
                                it[0])) {
                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                    //toast("为了你好")

                    hintUserWhyRequestPermission(requestPermissonListener)

                } else {
                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(t,
                            it, requestPermissonListener.requestCode)
                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                    //toast("再次申请")
                }
            } else {
                requestPermissonListener.success()
                //toast("拥有权限")
            }
        }


    }

}