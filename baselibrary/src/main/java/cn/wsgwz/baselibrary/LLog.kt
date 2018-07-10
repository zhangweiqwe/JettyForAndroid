package com.eatbeancar.user.myapplication

import android.util.Log

class LLog {
    companion object {
        fun d(tag:String?, str:String?){
            if(tag==null){
                return
            }
            if(str!=null){
                Log.d(tag,str)
            }else{
                Log.d(tag,"str=null")
            }
        }
        fun d(str:String?){
            d("LLogOther",str)
        }
    }
}