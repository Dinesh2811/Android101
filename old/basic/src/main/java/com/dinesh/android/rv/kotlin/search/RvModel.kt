package com.dinesh.android.rv.kotlin.search

data class RvModel(
    var name: String,
    var profilePic: Int = 0,
    var expanded: Boolean? = null,
    var isChecked: Boolean = false
)