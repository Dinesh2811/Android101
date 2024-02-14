package com.dinesh.basic.app

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.dinesh.basic.R


fun FragmentActivity.replaceFragment(fragment: Fragment, containerId: Int /* = R.id.flFragment */, addToBackStack: Boolean = true) {
    val transaction = supportFragmentManager.beginTransaction().replace(containerId, fragment)
    if (addToBackStack) {
        transaction.addToBackStack(null)
    }
    transaction.commit()
    /*
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.flFragment, AssetsAddFragment())
                .addToBackStack(null)
                .commit()
     */
    /*
            requireActivity().supportFragmentManager.popBackStack()
     */
}

