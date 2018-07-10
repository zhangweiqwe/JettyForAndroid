package com.eatbeancar.user.myapplication

import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import cn.wsgwz.baselibrary.BaseUserInterface
import cn.wsgwz.baselibrary.LoadingDialogHelper
import cn.wsgwz.baselibrary.permission.*

open class BaseFragment : Fragment(),BaseUserInterface,LoadingDialogHelper {

    var permissionList: ArrayList<RequestPermissonListener>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        OnRequestPermissionsResultHelper.with(permissionList)?.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    fun doWithPermission(requestPermissonListener: RequestPermissonListener) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            requestPermissonListener.success()
        }
        if (permissionList == null) {
            permissionList = ArrayList<RequestPermissonListener>()
        }
        FragmentWithPermission.with(this, permissionList)?.doWithPermission(requestPermissonListener)
    }




    override fun onDestroyView() {
        super.onDestroyView()
    }



    override fun toast(string: String?) {
        if(context is BaseUserInterface){
            (context as BaseUserInterface).apply {
                toast(string)
            }
        }
    }

    override fun login() {
        if(context is BaseUserInterface){
            (context as BaseUserInterface).apply {
                login()
            }
        }
    }

    override fun refresh() {

    }


    override fun showLoadingDialog(isCancellable:Boolean){
       if(activity is BaseActivity){
           (activity as BaseActivity).showLoadingDialog(isCancellable)
       }
    }

    override fun dismissLoadingDialog(){
        if(activity is BaseActivity){
            (activity as BaseActivity).dismissLoadingDialog()
        }
    }

    override fun setDescriptionStr(descriptionStr: String) {
        if(activity is BaseActivity){
            (activity as BaseActivity).setDescriptionStr(descriptionStr)
        }
    }
}