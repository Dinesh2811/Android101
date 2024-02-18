package com.dinesh.android.testing.basic

import com.dinesh.android.R


data class RvModel(
    var name: String? = null,
    var profilePic: Int =  R.drawable.ic_launcher_foreground,
    var expanded: Boolean = false,
    var isChecked: Boolean = false
)