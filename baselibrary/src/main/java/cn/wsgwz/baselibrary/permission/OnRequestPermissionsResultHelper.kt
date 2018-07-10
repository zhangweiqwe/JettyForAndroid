package cn.wsgwz.baselibrary.permission

import android.content.pm.PackageManager
import android.widget.Toast

class OnRequestPermissionsResultHelper private constructor() {
   lateinit var permissionList:ArrayList<RequestPermissonListener>


    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {


        for (r in permissionList) {
            if (requestCode == r.requestCode) {

                var ok = true
                for (i in 0 until permissions.size) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                       /* val str = "" + "权限" + permissions[i] + "申请成功"
                        Toast.makeText(r.activity,str,Toast.LENGTH_SHORT).show()*/
                    } else {
                        /*val str = "" + "权限" + permissions[i] + "申请失败"
                        Toast.makeText(r.activity,str,Toast.LENGTH_SHORT).show()*/
                        ok = false
                        break
                    }
                }

                if (ok) {
                    r.success()
                } else {
                    r.refuse()
                }
                permissionList.remove(r)
                break
            }
        }


    }

    companion object {
        fun with(permissionList:ArrayList<RequestPermissonListener>?):OnRequestPermissionsResultHelper?{
            if(permissionList==null){
                return null
            }
            return OnRequestPermissionsResultHelper().apply {
                this.permissionList = permissionList
            }
        }
    }

}