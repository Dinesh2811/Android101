package com.dinesh.android.root

data class RvParentModel(
    var packageName: String = "Root",
    var rvChildModel: List<RvChildModel>,
    var isExpandable: Boolean = false
)