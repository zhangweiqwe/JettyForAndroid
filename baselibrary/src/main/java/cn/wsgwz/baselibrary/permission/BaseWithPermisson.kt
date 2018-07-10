package cn.wsgwz.baselibrary.permission

import android.app.AlertDialog
import android.content.DialogInterface
import android.support.v4.app.ActivityCompat
import cn.wsgwz.baselibrary.R

abstract class BaseWithPermisson<T> {

    abstract var t:T
    abstract var permissionList:ArrayList<RequestPermissonListener>

    abstract fun doWithPermission(requestPermissonListener: RequestPermissonListener)

}