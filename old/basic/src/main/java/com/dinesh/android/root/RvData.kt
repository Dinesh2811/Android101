//package com.dinesh.android.root
//
//import android.content.Context.MODE_PRIVATE
//import android.util.Log
//import androidx.appcompat.app.AppCompatActivity
//import com.dinesh.android.app.BaseActivity
//import com.dinesh.android.app.RequestPermission
//import com.dinesh.android.app.ThemePreference
//import com.dinesh.android.app.ToolbarMain
//import com.dinesh.android.kotlin.activity.LayoutInflaterClass
//import com.dinesh.android.kotlin.activity.PhotoPicker
//import com.dinesh.android.test.Testing
//
////private val TAG = "log_" + RvData::class.java.name.split(RvData::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]
//
////data class ChildData(val className: Class<*>, val title: String = "", val description: String = "")
//
//object RvData{
//    private val rvParentModelList = ArrayList<RvParentModel>()
//
//    private fun initializeData() {
//        setChildData("App", app)
//        setChildData("Activity", activity)
//        setChildData("RecyclerView", recyclerView)
//        setChildData("Retrofit", retrofit)
//        setChildData("Test", test)
//    }
//
//    private fun setChildData(parentPackageName: String, childDataList: List<ChildData>) {
//        setRvParentModelList(
//            parentPackageName = parentPackageName,
//            rvChildModelList = setRvChildModelList(
//                className = childDataList.map { it.className } as ArrayList<Class<*>>,
//                stringList = childDataList.map { it.title } as ArrayList<String>,
//                descriptionList = childDataList.map { it.description } as ArrayList<String>
//            )
//        )
//    }
//
//    private fun setRvChildModelList(className: ArrayList<Class<*>>, stringList: ArrayList<String>, descriptionList: ArrayList<String> = ArrayList()): ArrayList<RvChildModel> {
//        val rvChildModelList = ArrayList<RvChildModel>()
//
//        for (i in className.indices) {
//            val title = stringList.getOrNull(i)?.takeIf { it.isNotBlank() } ?: className[i].simpleName
//            val description = descriptionList.getOrNull(i)?.takeIf { it.isNotBlank() } ?: "Yet to add description"
//            val existingChild = rvChildModelList.firstOrNull { it.title == title }
//            if (existingChild == null) {
//                rvChildModelList.add(RvChildModel(title = title, className = className[i], description = description))
//            }
//        }
//
//        return rvChildModelList
//    }
//
//    private fun setRvParentModelList(parentPackageName: String , rvChildModelList: ArrayList<RvChildModel> = ArrayList()): ArrayList<RvParentModel> {
//        if (parentPackageName.isNotEmpty() && rvChildModelList.isNotEmpty()) {
//            rvParentModelList.add(RvParentModel(parentPackageName, rvChildModelList))
//        }
//        return rvParentModelList
//    }
//
//    fun rvList(): ArrayList<RvParentModel> {
//        if (rvParentModelList.isEmpty()) {
//            initializeData()
//        }
//        return rvParentModelList
//    }
//
//    private var app: List<ChildData> = emptyList()
//    private var activity: List<ChildData> = emptyList()
//    private var recyclerView: List<ChildData> = emptyList()
//    private var retrofit: List<ChildData> = emptyList()
//    private var test: List<ChildData> = emptyList()
//
//    init {
//
//        app = listOf(
//            ChildData(ThemePreference::class.java,"Theme Preference", "Yet to add description"),
//            ChildData(ToolbarMain::class.java,""),
//            ChildData(BaseActivity::class.java,""),
//            ChildData(RequestPermission::class.java,"")
//        )
//
//        activity = listOf(
//            ChildData(PhotoPicker::class.java,""),
//            ChildData(LayoutInflaterClass::class.java,"LayoutInflater")
//        )
//
////        retrofit = listOf(
////            ChildData(com.dinesh.android.kotlin.retrofit.raw.ReadJSON::class.java,"ReadJSON"),
////            ChildData(com.dinesh.android.kotlin.retrofit.v0.ApiMain::class.java,"v0"),
////            ChildData(com.dinesh.android.kotlin.retrofit.v1.ApiMain::class.java,"v1"),
////            ChildData(com.dinesh.android.kotlin.retrofit.v2.ApiMain::class.java,"v2"),
////            ChildData(com.dinesh.android.kotlin.retrofit.livedata.ApiMain::class.java,"livedata")
////        )
//
//        test = listOf(
//            ChildData(Testing::class.java)
//        )
//
////        Log.e(TAG, "getSharedPreferences: ${getSharedPreferences("SharedPreferences_RvHome_SelectLanguage", MODE_PRIVATE).getBoolean("SelectLanguage", true)}")
//
//        if(true){
//            recyclerView = listOf(
//                ChildData(com.dinesh.android.rv.kotlin.basic.RvMain::class.java,"Basic"),
//                ChildData(com.dinesh.android.rv.kotlin.search.RvMain::class.java,"Search filter", "Search filter"),
//                ChildData(com.dinesh.android.rv.kotlin.refresh.RvMain::class.java,"Refresh", "Refresh to force update the view"),
//                ChildData(com.dinesh.android.rv.kotlin.reorder.RvMain::class.java,"Reorder","Reorder"),
//                ChildData(com.dinesh.android.rv.kotlin.multi_select.RvMain::class.java,"Multi Select","Multiple selection"),
//                ChildData(com.dinesh.android.rv.kotlin.expand.RvMain::class.java,"Expand","Expand"),
//                ChildData(com.dinesh.android.rv.kotlin.diffutil.RvMain::class.java,"DiffUtil","DiffUtil is used to optimize the efficiency"),
//                ChildData(com.dinesh.android.rv.kotlin.adv.swipe_drag_gesture.RvMain::class.java,"Swipe and Drag Gesture","Swipe left and right along with UP & DOWN drag and reorder"),
//                ChildData(com.dinesh.android.rv.kotlin.nested.v0.RvMain::class.java,"Nested v0","Nested vertical parent and horizontal child"),
//                ChildData(com.dinesh.android.rv.kotlin.nested.v1.RvMain::class.java,"Nested v1","Nested vertical parent and vertical child which Intercept touch events in the child RecyclerView and pass them to the parent RecyclerView"),
//                ChildData(com.dinesh.android.rv.kotlin.nested.v2.RvMain::class.java,"Nested v2","Nested RecyclerView similar to playStore & netflix"),
//                ChildData(com.dinesh.android.rv.kotlin.multi_view.RvMain::class.java,"Multi View", "Multi View"),
//                ChildData(com.dinesh.android.rv.kotlin.listadapter.RvMain::class.java,"List Adapter with DiffUtil", "List Adapter with DiffUtil")
//            )
//        } else{
//            recyclerView = listOf(
//                ChildData(com.dinesh.android.rv.java.basic.RvMain::class.java,"Basic"),
//                ChildData(com.dinesh.android.rv.java.search.RvMain::class.java,"Search filter", "Search filter"),
//                ChildData(com.dinesh.android.rv.java.refresh.RvMain::class.java,"Refresh", "Refresh to force update the view"),
//                ChildData(com.dinesh.android.rv.java.reorder.RvMain::class.java,"Reorder","Reorder"),
//                ChildData(com.dinesh.android.rv.java.multi_select.RvMain::class.java,"Multi Select","Multiple selection"),
//                ChildData(com.dinesh.android.rv.java.expand.RvMain::class.java,"Expand","Expand"),
//            )
//        }
//
//    }
//
//}
