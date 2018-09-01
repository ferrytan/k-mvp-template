package com.meetferrytan.kotlinmvptemplate.presentation.githubusersearch

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import com.meetferrytan.kotlinmvptemplate.R
import com.meetferrytan.kotlinmvptemplate.base.presentation.BaseMvpActivity
import com.meetferrytan.kotlinmvptemplate.data.entity.User
import com.meetferrytan.kotlinmvptemplate.presentation.userdetail.UserDetailFragment
import dagger.android.AndroidInjection
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_example.*
import kotlinx.android.synthetic.main.content_example.*
import java.text.SimpleDateFormat
import java.util.*

class GithubUserSearchActivity : BaseMvpActivity<GithubUserSearchPresenter, GithubUserSearchContract.View>(), GithubUserSearchContract.View, HasSupportFragmentInjector {
    private var mUserDetailFragment: UserDetailFragment? = null
    private var mLastQuery: String? = null

    private fun currentDateTime(): String{
        val simpleDateFormat = SimpleDateFormat("dd-MM HH:mm:ss.SSS", Locale.getDefault())
        return simpleDateFormat.format(System.currentTimeMillis())
    }

    public override fun setLayoutRes(): Int {
        return R.layout.activity_example
    }

    public override fun processIntentExtras(extras: Bundle?) {

    }

    public override fun startingUpActivity(savedInstanceState: Bundle?) {
        setSupportActionBar(toolbar)
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(CONTENT_LOG)) {
                txvLog.text = savedInstanceState.getString(CONTENT_LOG)
            }
        }
        txvLog.movementMethod = ScrollingMovementMethod()
        btnGetUserDetail.setOnClickListener {
            val lastQuery = edtUsername.text.toString()
            if (!lastQuery.isBlank()) {
                mLastQuery = lastQuery
                presenter.getUserDetail(lastQuery)
            }
        }
        mUserDetailFragment = UserDetailFragment.newInstance()
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, mUserDetailFragment).commit()
    }

    public override fun inject() {
        AndroidInjection.inject(this)
    }

    public override fun getViewImpl(): GithubUserSearchContract.View? = this


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(CONTENT_LOG, txvLog.text.toString())
    }

    override fun showError(processCode: Int, throwable: Throwable) {
        // Switch process by static integers from GithubUserSearchPresenter
        when (processCode) {
            GithubUserSearchPresenter.PROCESS_GET_USER_DETAIL -> appendLog("${currentDateTime()} Error: ${throwable.message}\n")
        }
    }

    override fun showLoading(processCode: Int, show: Boolean) {
        // Switch process by static integers from GithubUserSearchPresenter
        when (processCode) {
            GithubUserSearchPresenter.PROCESS_GET_USER_DETAIL -> if (show) {
                appendLog("${currentDateTime()} Loading Github User \"$mLastQuery\"\n")
            } else {
                appendLog("${currentDateTime()} Done fetching user data\n")
            }
        }
    }

    override fun showUserDetail(user: User) {
        appendLog("${currentDateTime()} User fetched: ${user.id}-${user.name}\n")
        mUserDetailFragment?.updateUserInfo(user)
    }

    private fun appendLog(text: String) {
        txvLog.append(text)
        val textBottom = txvLog.lineHeight * txvLog.lineCount
        val textViewHeight = txvLog.height
        if (textBottom > textViewHeight) {
            txvLog.scrollTo(0, textBottom - textViewHeight)
        }
    }

    companion object {
        const val CONTENT_LOG = "log"
    }
}