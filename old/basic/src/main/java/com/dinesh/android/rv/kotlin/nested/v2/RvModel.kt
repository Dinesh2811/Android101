package com.dinesh.android.rv.kotlin.nested.v2


data class RvParentModel(
    var title: String,
    var rvChildModelList: List<RvChildModel> = emptyList()
)

data class RvChildModel(
    var imgUrl: String,
    var childTitle: String = "",
    var childImgDetail: String = ""
)

