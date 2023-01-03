package com.myt.library

import android.content.pm.PackageManager
import androidx.fragment.app.Fragment

typealias PermissionCallBack = (Boolean, List<String>) -> Unit

class InvisibleFragment: Fragment(){

    private var callBack: PermissionCallBack? = null

    fun requestNow(cb: PermissionCallBack, vararg permissions: String){
        callBack = cb
        requestPermissions(permissions, 1)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            val deniedList = ArrayList<String>()
            for ((index, result) in grantResults.withIndex()) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    deniedList.add(permissions[index])
                }
            }
            val allGranted = deniedList.isEmpty()
            callBack?.let { it(allGranted, deniedList) }
        }
    }

}