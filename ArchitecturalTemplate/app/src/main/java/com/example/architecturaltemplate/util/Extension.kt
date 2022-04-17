package com.example.architecturaltemplate.util

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import com.example.architecturaltemplate.RegisterFragment
import com.example.architecturaltemplate.network.Resource
import com.example.architecturaltemplate.ui.fragment.LoginFragment
import com.google.android.material.snackbar.Snackbar


fun <A : Activity> Activity.startNewActivity(activity: Class<A>) {
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.enable(enabled: Boolean) {
    isEnabled = enabled
    alpha = if (enabled) 1f else 0.5f
}

fun View.snackbar(message: String, action: (() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let {
        snackbar.setAction("Retry") {
            it()
        }
    }
    snackbar.show()
}

fun Fragment.handleApiError(
    failure: Resource.Failure,
    retry: (() -> Unit)? = null
) {
    when {

        failure.isNetworkError -> requireView().snackbar(
            "Connection problem",
            retry
        )

        failure.errorCode == 400 -> {
            when (this) {
                is LoginFragment -> requireView().snackbar("success")
                is RegisterFragment -> requireView().snackbar("success")
            }
        }
        failure.errorCode == 502 -> {
            when (this) {
                is LoginFragment -> requireView().snackbar("Internal server err")
                is RegisterFragment -> requireView().snackbar("Internal server err")
            }
        }
        failure.errorCode == 302 -> {
            when (this) {
                is LoginFragment -> requireView().snackbar("Unauthorized access")
                is RegisterFragment -> requireView().snackbar("Unauthorized access")
                // (this as BaseFragment<*, *, *>).logout()
            }
        }

        failure.errorCode == 401 -> {
            when (this) {
                is LoginFragment -> requireView().snackbar("authentication error")
                is RegisterFragment -> requireView().snackbar("authentication error")
                // (this as BaseFragment<*, *, *>).logout()
            }
        }else -> {
            val error = failure.errorBody?.string().toString()
            requireView().snackbar(error)
        }
    }
}

