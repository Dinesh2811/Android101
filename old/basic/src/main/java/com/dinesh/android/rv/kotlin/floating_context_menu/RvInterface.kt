package com.dinesh.android.rv.kotlin.floating_context_menu

import android.view.View


interface RvInterface {
    fun onItemClick(view: View?, position: Int)
    fun onItemLongClick(view: View?, position: Int)
}