package cn.wsgwz.baselibrary


interface LoadingDialogHelper {


    fun showLoadingDialog(isCancellable:Boolean)

    fun dismissLoadingDialog()

    fun setDescriptionStr(descriptionStr: String)
}