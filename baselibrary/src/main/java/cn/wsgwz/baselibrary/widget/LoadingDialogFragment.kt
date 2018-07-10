package cn.wsgwz.baselibrary.widget

import android.app.DialogFragment
import android.app.FragmentManager
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.TextView
import cn.wsgwz.baselibrary.R
import kotlinx.android.synthetic.main.view_loading_diaog.*


class LoadingDialogFragment : DialogFragment() {
    companion object {
        val TAG = LoadingDialogFragment::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onStart() {
        super.onStart()
        val window = getDialog().window
        val windowParams = window.attributes
        windowParams.dimAmount = 0.0f
        window.attributes = windowParams
        window.setBackgroundDrawable(ColorDrawable())
        //window.setWindowAnimations(R.style.LoadingDialogAnime)

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return inflater?.inflate(R.layout.view_loading_diaog, container)
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    fun showExecutePendingTransactions(manager: FragmentManager) {
        if(isAdded||isVisible){

        }else{
            super.show(manager, TAG)
            fragmentManager.executePendingTransactions()
        }
    }

}