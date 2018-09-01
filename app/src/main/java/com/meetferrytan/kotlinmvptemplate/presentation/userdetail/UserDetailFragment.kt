package com.meetferrytan.kotlinmvptemplate.presentation.userdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.meetferrytan.kotlinmvptemplate.R
import com.meetferrytan.kotlinmvptemplate.base.presentation.BaseMvpFragment
import com.meetferrytan.kotlinmvptemplate.data.entity.User
import com.meetferrytan.kotlinmvptemplate.presentation.userupdate.UserUpdateFragment
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.fragment_example.*

class UserDetailFragment : BaseMvpFragment<UserDetailPresenter, UserDetailContract.View>(), UserDetailContract.View, HasSupportFragmentInjector {

    private var mUserUpdateFragment: UserUpdateFragment? = null

    public override fun inject() {
        AndroidSupportInjection.inject(this)
    }

    override fun createLayout(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_example, container, false)

    public override fun processArguments(args: Bundle?) {

    }

    public override fun startingUpFragment(view: View, savedInstanceState: Bundle?) {
        mUserUpdateFragment = UserUpdateFragment.newInstance()
        childFragmentManager.beginTransaction().replace(R.id.frameLayout, mUserUpdateFragment).commit()
    }

    public override fun getViewImpl(): UserDetailContract.View? = this

    override fun showError(processCode: Int, throwable: Throwable) {
        // not called
    }

    override fun showLoading(processCode: Int, show: Boolean) {
        // not called
    }

    fun updateUserInfo(user: User) {
        presenter.updateUserDetail(user)
    }

    override fun displayUser(avatar: String?, name: String?, username: String?, location: String?, blog: String?, repos: String?) {
        Glide.with(this)
                .load(avatar)
                .into(imgAvatar)

        txvName.text = name
        txvLocation.text = location
        txvBlogUrl.text = blog
        txvRepoCount.text = repos

        mUserUpdateFragment?.fetchUserUpdate(username)
    }

    companion object {

        fun newInstance(): UserDetailFragment {
            val fragment = UserDetailFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}