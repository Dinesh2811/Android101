package com.dinesh.android.root

import android.view.View

interface RvChildInterface {
    fun onChildItemClick(parentPosition: Int, view: View?, position: Int)
}