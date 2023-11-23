package com.boyot.collector.base

import android.app.Activity
import android.net.ConnectivityManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.boyot.collector.R
import com.boyot.collector.classes.dialogs.LoadingDialog
import com.boyot.collector.classes.utils.AppToast
import com.boyot.collector.classes.utils.ResponseHandler
import com.boyot.collector.ui.MainViewModel
import com.boyot.collector.domain.entity.responses.BaseResponse
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
open class BaseFragment : Fragment() {
    @Inject
    lateinit var appToast: AppToast
    private val mainViewModel: MainViewModel by activityViewModels()

    @Inject
    lateinit var loadingDialog: LoadingDialog

    open fun onApiError(response: BaseResponse?) {
        if (response != null) {
            if (ResponseHandler.isLogoutCode(response)) {
                logout()
//            } else if (ResponseHandler.isForceUpdate(response)) {
//                view?.findNavController()?.navigate(R.id.nav_force_update)
            } else {
                appToast.showMessage(response.message!!)
            }
        }
    }

    open fun onLoading(isLoading: Boolean) {
        if (isLoading)
            loadingDialog.show()
        else
            loadingDialog.dismiss()

    }

    private fun logout() {
        mainViewModel.clearUserData()
        restartActivity(requireActivity())
    }

    private fun restartActivity(activity: Activity) {
        activity.finish()
        activity.startActivity(activity.intent)
    }

    open fun popBackStack() {
        view?.findNavController()?.popBackStack()
    }

    open fun navigate(directions: NavDirections) {
        appToast.cancel()
        hideKeyboard()
        if (!isInternetAvailable) {
            view?.findNavController()?.navigate(R.id.nav_no_internet_connection)
            return
        }
        view?.findNavController()?.navigate(directions)
    }

    open fun navigate(direction: Int) {
        appToast.cancel()
        hideKeyboard()
        if (!isInternetAvailable) {
            view?.findNavController()?.navigate(R.id.nav_no_internet_connection)
            return
        }
        view?.findNavController()?.navigate(direction)
    }


    val isInternetAvailable: Boolean
        get() {
            var haveConnectedWifi = false
            var haveConnectedMobile = false
            val cm =
                requireContext().getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.allNetworkInfo
            for (ni in netInfo) {
                if (ni.typeName.equals(
                        "WIFI",
                        ignoreCase = true
                    )
                ) if (ni.isConnected) haveConnectedWifi = true
                if (ni.typeName.equals(
                        "MOBILE",
                        ignoreCase = true
                    )
                ) if (ni.isConnected) haveConnectedMobile = true
            }
            return haveConnectedWifi || haveConnectedMobile
        }

    protected fun hideKeyboard() {
        val view = requireActivity().currentFocus
        if (view != null) {
            val imm =
                requireContext().getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}