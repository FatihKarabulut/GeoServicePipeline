package com.ahbap.android.app.wikiservice

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.os.Messenger
import android.util.Log
import android.widget.Toast
import com.ahbap.android.app.wikiservice.geonames.GeoFullData
import com.ahbap.android.app.wikiservice.geonames.GeoFullInfo
import com.ahbap.android.app.wikiservice.geonames.IGeonames
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.ref.WeakReference
import javax.inject.Inject

const val HANDLER_WHAT_MSG = 1
const val STR = "Q"
const val MAXROW = "MAXROW"
const val GEO = "GEO"

@AndroidEntryPoint
class WikiService : Service() {
    lateinit var mHandler: Handler
    lateinit var mMessenger: Messenger
    @Inject
    lateinit var mGeo : IGeonames

    private class HandlerService(service: WikiService) : Handler(Looper.myLooper()!!) {
        private val mWeak = WeakReference(service)

        private fun replayToClient(replyTo : Messenger, geo : String)
        {
            val msg = Message.obtain(null, HANDLER_WHAT_MSG)
            msg.data.putString(GEO,geo)
            replyTo.send(msg)
        }
        private fun Callback(msg: Message)
        {
            val str = msg.data!!.getString(STR)!!
            val maxRow = msg.data!!.getInt(MAXROW)
            val replyTo = msg.replyTo

           val call = mWeak.get()!!.mGeo.findByGeonames(str,maxRow)
           call.enqueue(object : retrofit2.Callback<GeoFullInfo>{
                override fun onResponse(call: Call<GeoFullInfo>, response: Response<GeoFullInfo>) {
                    if (response.isSuccessful)
                    {
                        val res = response.body()
                        if (res?.geonames!!.isNotEmpty())
                        res?.geonames!!.forEach{replayToClient(replyTo,it.toString())}
                        else
                            Toast.makeText(mWeak.get(), "Not Found", Toast.LENGTH_SHORT).show()

                    }
                    else
                      Toast.makeText(mWeak.get(), "Not Successfull", Toast.LENGTH_SHORT).show()

                }

                override fun onFailure(call: Call<GeoFullInfo>, t: Throwable) {
                    Toast.makeText(mWeak.get(), "On Failer", Toast.LENGTH_SHORT).show()
                    Log.d("On Failler ", t.message ?: "On Failler Exception")
                }
            })

        }
        override fun handleMessage(msg: Message)
        {

            when (msg.what) {
                HANDLER_WHAT_MSG -> Callback(msg)
            }
            super.handleMessage(msg)
        }
    }

    override fun onBind(intent: Intent): IBinder {
        Toast.makeText(this, "Services Connected", Toast.LENGTH_SHORT).show()
        mHandler = HandlerService(this)
        mMessenger = Messenger(mHandler)
        return mMessenger.binder
    }
}