package com.ahbap.android.app.wikiclient

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.os.Messenger
import android.os.RemoteException
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ahbap.android.app.wikiclient.databinding.ActivityMainBinding
import com.ahbap.android.app.wikiclient.view.ListenerViewMainActivity
import dagger.hilt.android.AndroidEntryPoint

const val ACTION = "android.intent.action.WİKİSERVİCE"
const val PACKGAGE = "com.ahbap.android.app.wikiservice"
const val STR = "Q"
const val MAXROW = "MAXROW"
const val HANDLER_WHAT_MSG = 1
const val GEO = "GEO"
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var mBinding : ActivityMainBinding

    private var mRequestMessenger: Messenger? = null
    private var mReplyMessenger: Messenger? = null
    private var mBound = false
    private var services = object : ServiceConnection {

        override fun onServiceConnected(p0: ComponentName?, service: IBinder?) {

            mRequestMessenger = Messenger(service)
            mReplyMessenger = Messenger(HandlerMain(this@MainActivity))
            mBound = true
        }

        override fun onServiceDisconnected(p0: ComponentName?) {

            mRequestMessenger = null
            mBound = false
           Log.d("MainActivity","onServiceDisconnected")
        }
    }

    private fun OnBound()
    {
        val intent = Intent(ACTION).setPackage(PACKGAGE)

        if (!bindService(intent,services, Context.BIND_AUTO_CREATE))
            throw RemoteException("OnConnectedService Exception")
    }

    private fun UnBoundService()
    {
        if (mBound)
        {
            mRequestMessenger = null
            mBound = false
            unbindService(services)
        }
    }

    override fun onStart() {
        try {
            OnBound()
        }
        catch (ex : RemoteException)
        {
            Toast.makeText(this, ex.message.toString(), Toast.LENGTH_SHORT).show()
            Log.d("OnBound ",ex.message.toString())
        }
        super.onStart()
    }
    override fun onStop() {
       try {
           UnBoundService()
       }
       catch (ex : RemoteException)
       {
           Toast.makeText(this, ex.message.toString(), Toast.LENGTH_SHORT).show()
           Log.d(" UnBoundService() ",ex.message.toString())
       }
        super.onStop()
    }
    private fun initialize()
    {
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        mBinding.str = "batman"
        mBinding.maxRow = 3
        mBinding.view = ListenerViewMainActivity(this)
        mBinding.adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,ArrayList<String>())
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()

    }
    private fun sendData()
    {
        val msg = Message.obtain(null, HANDLER_WHAT_MSG)
        msg.data.putInt(MAXROW,mBinding.maxRow)
        msg.data.putString(STR,mBinding.str)
        msg.replyTo = mReplyMessenger
        mRequestMessenger!!.send(msg)

    }
    fun getButtonClicked()
    {
        if (mBound)
            sendData()
    }

    private class HandlerMain(activity: MainActivity) : android.os.Handler(Looper.myLooper()!!)
    {
        private var mWeek = java.lang.ref.WeakReference(activity)
    private fun adapterCallback(text : String)
    {
        mWeek.get()!!.mBinding.adapter!!.add(text)
    }
        override fun handleMessage(msg: Message) {
            when(msg.what)
            {
                HANDLER_WHAT_MSG -> adapterCallback(msg.data.getString(GEO)!!)
            }
            super.handleMessage(msg)
        }
    }
}