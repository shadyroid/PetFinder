package com.shady.boyot.base

import android.app.Activity
import android.net.ConnectivityManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.shady.boyot.R
import com.shady.boyot.classes.dialogs.LoadingDialog
import com.shady.boyot.classes.utils.AppToast
import com.shady.boyot.ui.MainViewModel
import com.shady.domain.entity.responses.BaseResponse
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
open class BaseFragment : Fragment() {
    @Inject
    lateinit var appToast: AppToast
    private val mainViewModel: MainViewModel by activityViewModels()

    @Inject
    lateinit var loadingDialog: LoadingDialog

    fun onApiError(response: BaseResponse?) {
        if (response != null)
//            if (ResponseHandler.isLogoutCode(response)) {
//                logout()
//            } else if (ResponseHandler.isForceUpdate(response)) {
//                view?.findNavController()?.navigate(R.id.nav_force_update)
//            } else {
            appToast.showMessage(response.message!!)
//            }
    }

    fun onLoading(isLoading: Boolean) {
        if (isLoading)
            loadingDialog.show()
        else
            loadingDialog.dismiss()

    }

    private fun logout() {
        mainViewModel.clearUserData()
        NotificationManagerCompat.from(requireContext()).cancelAll()
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