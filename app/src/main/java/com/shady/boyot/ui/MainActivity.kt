package com.shady.boyot.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.fawry.pos.retailer.connect.FawryConnect
import com.fawry.pos.retailer.connect.model.connection.ConnectionType
import com.fawry.pos.retailer.connect.model.messages.user.UserData
import com.fawry.pos.retailer.connect.model.messages.user.UserType
import com.fawry.pos.retailer.connect.model.payment.PaymentOptionType
import com.fawry.pos.retailer.ipc.IPCConnectivity
import com.fawry.pos.retailer.modelBuilder.sale.CardSale
import com.shady.boyot.R
import com.shady.boyot.classes.others.CONSTANTS.password
import com.shady.boyot.classes.others.CONSTANTS.userName
import com.shady.boyot.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {



    private val callBack: FawryConnect.OnConnectionCallBack = FawryConnect.OnConnectionCallBack(
        onConnected = { Log.d("TAG", "shady onConnected: ")},
        onFailure = { connection, throwable ->
            Toast.makeText(this, "خدمة الدفع لم يتم تشغيلها بسبب\n"+ throwable?.message, Toast.LENGTH_SHORT).show()
        },
        onDisconnected = { }
    )


    private lateinit var binding: ActivityMainBinding
    var fawryConnect: FawryConnect? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setLanguage()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        fawryConnect = FawryConnect.setup<IPCConnectivity.Builder>(
            ConnectionType.IPC,
            UserData(userName, password, UserType.MCC)
        )
            .setContext(applicationContext)
            .setConnectionCallBack(callBack)
            .connect()


    }

    fun setLanguage() {
        val locale = Locale("ar")
        Locale.setDefault(locale)
        val configuration = resources.configuration
        configuration.setLayoutDirection(locale)
        configuration.locale = locale
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
    }

}