package net.foursure.hogwarts.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.util.Log


class NetworkManager {

    //TODO: Check for network connection.
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (connectivityManager != null) {
                val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                        return true
                    }
                }
            }
        }else{
            val activeNetwork: NetworkInfo = connectivityManager.getActiveNetworkInfo()
            if (activeNetwork != null) {
                // connected to the internet
                if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) {
                    return true
                } else if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE) {
                    return true
                }
            } else {
                return false
            }
        }
        return false
    }
    //TODO: Add data to the database on app launch and refresh the data every 24 hours. data will be used when the user is offline (wish list)
}