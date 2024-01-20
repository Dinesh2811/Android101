package com.dinesh.android.rv.kotlin.expand

data class RvModel(
    val name: String,
    val profilePic: Int,
    val desc: String,
    var expanded: Boolean = false,
    var isChecked: Boolean = false
)