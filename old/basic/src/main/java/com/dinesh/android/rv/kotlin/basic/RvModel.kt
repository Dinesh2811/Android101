package com.dinesh.android.rv.kotlin.basic


data class RvModel(
    var name: String? = null,
    var profilePic: Int = 0,
    var expanded: Boolean? = null,
    var isChecked: Boolean = false
)