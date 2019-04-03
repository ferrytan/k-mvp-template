package com.meetferrytan.kotlinmvptemplate.presentation.userupdate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.meetferrytan.kotlinmvptemplate.R
import com.meetferrytan.kotlinmvptemplate.base.presentation.BaseMvpFragment
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.child_fragment_example.*

class UserUpdateFragment : BaseMvpFragment<UserUpdateContract.Presenter, UserUpdateContract.View>(), UserUpdateContract.View {

    public override fun inject() {
        AndroidSupportInjection.inject(this)
    }

    override fun createLayout(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.child_fragment_example, container, false)

    public override fun processArguments(args: Bundle?) {

    }

    public override fun startingUpFragment(savedInstanceState: Bundle?) {

    }

    override fun showError(processCode: Int, throwable: Throwable) {
        txvUpdate.text = "Error fetching user update: ${throwable.message}"
    }

    override fun showLoading(processCode: Int, show: Boolean) {
        if (show)
            txvUpdate.text = "fetching user update..."
    }

    fun fetchUserUpdate(username: String?) {
        presenter.getUserUpdate(username)
    }

    override fun showUserUpdate(updateText: String) {
        txvUpdate.text = updateText
    }

    companion object {

        fun newInstance(): UserUpdateFragment {
            val fragment = UserUpdateFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}