package com.ahbap.android.app.wikiclient.view

import com.ahbap.android.app.wikiclient.MainActivity
import java.lang.ref.WeakReference

class ListenerViewMainActivity(activity: MainActivity) {
    private var mWeek = WeakReference(activity)

    fun handlerGetButtonClicked() = mWeek.get()!!.getButtonClicked()
}