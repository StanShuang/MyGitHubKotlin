package com.stan.mygithub.repository.net

import android.content.Context
import com.stan.mygithub.R
import com.stan.mygithub.commen.view.LoadingDialog


/**
 * 带loading显示的结果回调
 * Created by guoshuyu
 * Date: 2018-10-08
 */
abstract class ResultProgressObserver<T>(private val context: Context, private val needLoading: Boolean = true) : ResultTipObserver<T>(context) {

    private var loadingDialog: LoadingDialog? = null

    private var loadingText: String? = null

    constructor(context: Context, loadingText: String?) : this(context) {
        this.loadingText = loadingText
    }

    override fun onRequestStart() {
        super.onRequestStart()
        if (needLoading) {
            showLoading()
        }
    }

    override fun onRequestEnd() {
        super.onRequestEnd()
        dismissLoading()
    }

    private fun getLoadingText(): String {
        return if (loadingText.isNullOrBlank()) context.getString(R.string.loading) else loadingText!!
    }

    private fun showLoading() {
        dismissLoading()
        loadingDialog = LoadingDialog.showDialog(context, getLoadingText(), false, null)
    }

    private fun dismissLoading() {
        loadingDialog?.apply {
            if (this.isShowing) {
                this.dismiss()
            }
        }
    }
}