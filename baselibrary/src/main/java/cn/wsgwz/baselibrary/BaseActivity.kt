package com.eatbeancar.user.myapplication;

import android.content.pm.ActivityInfo
import android.graphics.Point
import android.os.Build
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.widget.Toast
import cn.wsgwz.baselibrary.BaseApplication
import cn.wsgwz.baselibrary.BaseUserInterface
import cn.wsgwz.baselibrary.LoadingDialogHelper
import cn.wsgwz.baselibrary.permission.ActivityWithPermission
import cn.wsgwz.baselibrary.permission.OnRequestPermissionsResultHelper
import cn.wsgwz.baselibrary.permission.RequestPermissonListener
import cn.wsgwz.baselibrary.widget.LoadingDialogFragment
import kotlinx.android.synthetic.main.view_loading_diaog.*


open class BaseActivity : AppCompatActivity(),  BaseUserInterface, LoadingDialogHelper {


    companion object {
        val tag = BaseActivity::class.java.simpleName
    }

    var permissionList: ArrayList<RequestPermissonListener>? = null


    var portrait: Boolean = true

    override fun onPause() {
        super.onPause()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        BaseApplication.addActivity(this)

        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getRealMetrics(metrics)
        proportionX = metrics.widthPixels/30

        if (portrait)
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT


        /*  window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
          window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
  */
        //window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        /*window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
*/

        super.onCreate(savedInstanceState)
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        /*val content = findViewById<FrameLayout>(android.R.id.content)
        content.getChildAt(0).setPadding(0,0,0,DensityUtils.dp2px(this,50f))*/
/*
        //透明状态栏 @顶部
        val content = findViewById<FrameLayout>(android.R.id.content)
        content.getChildAt(0).setPadding(0,0,0,DensityUtils.dp2px(this,50f))*/
        //13580
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        OnRequestPermissionsResultHelper.with(permissionList)?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun doWithPermission(requestPermissonListener: RequestPermissonListener) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            requestPermissonListener.success()
        }
        if (permissionList == null) {
            permissionList = ArrayList<RequestPermissonListener>()
        }
        ActivityWithPermission.with(this, permissionList)?.doWithPermission(requestPermissonListener)

    }


    override fun onResume() {
        super.onResume()
    }
    override fun onStart() {
        super.onStart()

    }


    /* fun toast(string: String?) {
         if (!TextUtils.isEmpty(string)) {
             Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
         }
     }

     open fun login() {}

    open fun refresh() {

     }*/

    private var toast:Toast? = null
    override fun toast(string: String?) {
        if(toast==null){
            toast = Toast.makeText(this,string,Toast.LENGTH_SHORT)
            toast?.show()
        }else{
            toast?.apply {
                if (!TextUtils.isEmpty(string)) {
                    setText(string)
                    duration = Toast.LENGTH_SHORT
                    show()
                }
            }
        }

    }

    override fun login() {

    }

    override fun refresh() {

    }

    var loadingDialogFragment: LoadingDialogFragment? = null
    private var loadingDialogFragmentIsShow = false
    private var isShow = 0
    override fun showLoadingDialog(isCancellable: Boolean) {
        isShow++
        if (loadingDialogFragmentIsShow) {
            return
        }
        if (loadingDialogFragment == null) {
            loadingDialogFragment = LoadingDialogFragment()
        }
        loadingDialogFragment?.isCancelable = isCancellable
        loadingDialogFragment?.showExecutePendingTransactions(fragmentManager)

        loadingDialogFragmentIsShow = true
    }

    override fun dismissLoadingDialog() {
        if (--isShow > 0) {
            return
        }
        loadingDialogFragment?.dismiss()
        loadingDialogFragmentIsShow = false
        isShow = 0
    }

    override fun setDescriptionStr(descriptionStr: String) {
        loadingDialogFragment?.also {
            it.progressDescriptionTV.text = descriptionStr
        }
    }

    val point = Point()


    private var proportionX = 0
    var canScrollFinish = false
    private var actionDownLastTime = 0L
    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action === MotionEvent.ACTION_DOWN) {
            point.x = event.rawX.toInt()
            point.y = event.rawY.toInt()

            actionDownLastTime = System.currentTimeMillis()

        }else

        if (event?.action == MotionEvent.ACTION_UP) {
            if (canScrollFinish&&event.rawX - point.x > 50&&point.x<proportionX&&(System.currentTimeMillis() - actionDownLastTime)<800) {
                finish()
            }
        }
        return super.dispatchTouchEvent(event)
    }


}

