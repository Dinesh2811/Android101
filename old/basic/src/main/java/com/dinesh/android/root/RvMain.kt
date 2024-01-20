package com.dinesh.android.root

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dinesh.android.R
import com.dinesh.android.app.BaseActivity
import com.dinesh.android.app.user_interface.CollapsingToolbar
import com.dinesh.android.app.RequestPermission
import com.dinesh.android.app.ThemePreference
import com.dinesh.android.app.ToolbarMain
import com.dinesh.android.kotlin.activity.LayoutInflaterClass
import com.dinesh.android.kotlin.activity.PhotoPicker
import com.dinesh.android.root.RvMain.RvData.rvList
import com.dinesh.android.test.Testing
import kotlin.collections.ArrayList

private val TAG = "log_" + RvMain::class.java.name.split(RvMain::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]


private var isKotlinChecked: Boolean = true

class RvMain : ToolbarMain(), RvParentInterface {
    private lateinit var rvParentAdapter: RvParentAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentViewLayout(R.layout.root_rv_main)
        recyclerView = findView(R.id.recyclerView)
        searchView = findView(R.id.searchView)
        isKotlinChecked = getSharedPreferences("sharedPreferences_$packageName", MODE_PRIVATE).getBoolean("SelectLanguage", true)
        if (isKotlinChecked){
            toolbar.title = getString(R.string.app_name) + " (Kotlin)"
        } else {
            toolbar.title = getString(R.string.app_name) + " (Java)"
        }

        rvParentAdapter = RvParentAdapter(emptyList(), this@RvMain)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = rvParentAdapter
        rvParentAdapter.rvParentModelList = rvList()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })

    }

    private fun filterList(query: String?) {
        if (query != null) {
            val filteredList = findMatchingParent(query, rvList())
            if (filteredList.isEmpty()) {
                Log.e(TAG, "filterList: No Data found")
                rvParentAdapter.setFilteredList(emptyList())
            } else {
                rvParentAdapter.setFilteredList(filteredList)
            }
        }
    }

    private fun findMatchingParent(query: String, list: List<RvParentModel>): ArrayList<RvParentModel> {
        val matchingParents = ArrayList<RvParentModel>()
        for (parent in list) {
            for (child in parent.rvChildModel) {
                if (child.title.contains(query, ignoreCase = true) || child.className.simpleName.contains(query, ignoreCase = true)
                    || child.description.contains(query, ignoreCase = true)
                ) {
                    if (!matchingParents.contains(parent)) {
                        matchingParents.add(parent)
                    }
                    break
                }
            }
        }
        return matchingParents
    }

    override fun onParentItemClick(view: View?, position: Int) {
//        if (view != null) {
//            when(view.id){
//                R.id.tvPackageName -> Log.d(TAG, "onItemClick: tvPackageName")
//                R.id.tvClassName -> Log.d(TAG, "onItemClick: tvClassName")
//                R.id.tvVersion -> Log.d(TAG, "onItemClick: tvVersion")
//                R.id.rvCardView -> Log.d(TAG, "onItemClick: rvCardView")
//                else -> Log.e(TAG, "onItemClick: else")
//            }
//        }

        Log.d(TAG, "onParentItemClick: ${rvList()[position].packageName}")
    }


    private lateinit var actionKotlin: MenuItem
    private lateinit var actionJava: MenuItem
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menu.setGroupVisible(R.id.action_select_language, true)

        actionKotlin = menu.findItem(R.id.action_kotlin)
        actionJava = menu.findItem(R.id.action_java)

        if (isKotlinChecked) {
            actionKotlin.isChecked = true
        } else {
            actionJava.isChecked = true
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_kotlin -> {
                isKotlinChecked = true
                RvData.rvParentModelList.clear()
                rvParentAdapter.rvParentModelList = rvList()
                getSharedPreferences("sharedPreferences_$packageName", MODE_PRIVATE).edit().putBoolean("SelectLanguage", true).apply()
                item.isChecked = true
                rvParentAdapter.notifyDataSetChanged()
                toolbar.title = getString(R.string.app_name) + " (Kotlin)"
                true
            }

            R.id.action_java -> {
                isKotlinChecked = false
                RvData.rvParentModelList.clear()
                rvParentAdapter.rvParentModelList = rvList()
                getSharedPreferences("sharedPreferences_$packageName", MODE_PRIVATE).edit().putBoolean("SelectLanguage", false).apply()
                item.isChecked = true
                rvParentAdapter.notifyDataSetChanged()
                toolbar.title = getString(R.string.app_name) + " (Java)"
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


    object RvData {
        internal var rvParentModelList = ArrayList<RvParentModel>()

        private fun initializeData() {
            setRvParentModelList("App", app)
            setRvParentModelList("Activity", activity)
            setRvParentModelList("RecyclerView", rv)
            setRvParentModelList("Retrofit", retrofit)
            setRvParentModelList("Test", test)
        }

        fun rvList(): ArrayList<RvParentModel> {
//            rvParentModelList.clear()
            setData()
            if (rvParentModelList.isEmpty()) {
                initializeData()
            }
            return rvParentModelList
        }

        private fun setRvParentModelList(parentPackageName: String, childDataList: List<ChildData>): ArrayList<RvParentModel> {
            val rvChildModelList = setRvChildModelList(
                className = childDataList.map { it.className } as ArrayList<Class<*>>,
                stringList = childDataList.map { it.title } as ArrayList<String>,
                descriptionList = childDataList.map { it.description } as ArrayList<String>
            )

            if (parentPackageName.isNotEmpty() && rvChildModelList.isNotEmpty()) {
                rvParentModelList.add(RvParentModel(parentPackageName, rvChildModelList))
            }
            return rvParentModelList
        }

        private fun setRvChildModelList(className: ArrayList<Class<*>>, stringList: ArrayList<String>, descriptionList: ArrayList<String> = ArrayList()): ArrayList<RvChildModel> {
            val rvChildModelList = ArrayList<RvChildModel>()

            for (i in className.indices) {
                val title = stringList.getOrNull(i)?.takeIf { it.isNotBlank() } ?: className[i].simpleName
                val description = descriptionList.getOrNull(i)?.takeIf { it.isNotBlank() } ?: "Yet to add description"
                val existingChild = rvChildModelList.firstOrNull { it.title == title }
                if (existingChild == null) {
                    rvChildModelList.add(RvChildModel(title = title, className = className[i], description = description))
                }
            }

            return rvChildModelList
        }

        private var app: List<ChildData> = emptyList()
        private var activity: List<ChildData> = emptyList()
        private var rv: List<ChildData> = emptyList()
        private var retrofit: List<ChildData> = emptyList()
        private var test: List<ChildData> = emptyList()

        init {
//            setData()
        }

        private fun setData() {
            app = listOf(
                ChildData(ThemePreference::class.java, "Theme Preference", "Yet to add description"),
                ChildData(ToolbarMain::class.java, ""),
                ChildData(BaseActivity::class.java, ""),
                ChildData(RequestPermission::class.java, ""),
                ChildData(CollapsingToolbar::class.java, "Collapsing Toolbar"),
                ChildData(com.dinesh.android.app.user_interface.notification.MainActivity::class.java, "Notification")
            )

            retrofit = listOf(
                ChildData(com.dinesh.android.kotlin.retrofit.raw.ReadJSON::class.java,"ReadJSON"),
                ChildData(com.dinesh.android.kotlin.retrofit.v0.ApiMain::class.java,"v0"),
                ChildData(com.dinesh.android.kotlin.retrofit.v1.ApiMain::class.java,"v1"),
                ChildData(com.dinesh.android.kotlin.retrofit.v2.ApiMain::class.java,"v2"),
                ChildData(com.dinesh.android.kotlin.retrofit.livedata.ApiMain::class.java,"livedata"),
                ChildData(com.dinesh.android.kotlin.retrofit.v3.ApiMain::class.java,"v3")
            )

            test = listOf(ChildData(Testing::class.java))

            if (isKotlinChecked) {
                activity = listOf(
                    ChildData(PhotoPicker::class.java, ""),
                    ChildData(LayoutInflaterClass::class.java, "LayoutInflater")
                )

                rv = listOf(
                    ChildData(com.dinesh.android.rv.kotlin.basic.RvMain::class.java, "Basic"),
                    ChildData(com.dinesh.android.rv.kotlin.search.RvMain::class.java, "Search filter", "Search filter"),
                    ChildData(com.dinesh.android.rv.kotlin.refresh.RvMain::class.java, "Refresh", "Refresh to force update the view"),
                    ChildData(com.dinesh.android.rv.kotlin.reorder.RvMain::class.java, "Reorder", "Reorder"),
                    ChildData(com.dinesh.android.rv.kotlin.multi_select.RvMain::class.java, "Multi Select", "Multiple selection"),
                    ChildData(com.dinesh.android.rv.kotlin.expand.RvMain::class.java, "Expand", "Expand"),
                    ChildData(com.dinesh.android.rv.kotlin.diffutil.RvMain::class.java, "DiffUtil", "DiffUtil is used to optimize the efficiency"),
                    ChildData(com.dinesh.android.rv.kotlin.adv.swipe_drag_gesture.RvMain::class.java, "Swipe and Drag Gesture", "Swipe left and right along with UP & DOWN drag and reorder"),
                    ChildData(com.dinesh.android.rv.kotlin.nested.v0.RvMain::class.java, "Nested v0", "Nested vertical parent and horizontal child"),
                    ChildData(com.dinesh.android.rv.kotlin.nested.v1.RvMain::class.java, "Nested v1", "Nested vertical parent and vertical child which Intercept touch events in the child RecyclerView and pass them to the parent RecyclerView"),
                    ChildData(com.dinesh.android.rv.kotlin.nested.v2.RvMain::class.java, "Nested v2", "Nested RecyclerView similar to playStore & netflix"),
                    ChildData(com.dinesh.android.rv.kotlin.multi_view.RvMain::class.java, "Multi View", "Multi View"),
                    ChildData(com.dinesh.android.rv.kotlin.listadapter.RvMain::class.java, "List Adapter with DiffUtil", "List Adapter with DiffUtil")
                )

            } else {
                activity = emptyList()

                rv = listOf(
                    ChildData(com.dinesh.android.rv.java.basic.RvMain::class.java, "Basic"),
                    ChildData(com.dinesh.android.rv.java.search.RvMain::class.java, "Search filter", "Search filter"),
                    ChildData(com.dinesh.android.rv.java.refresh.RvMain::class.java, "Refresh", "Refresh to force update the view"),
                    ChildData(com.dinesh.android.rv.java.reorder.RvMain::class.java, "Reorder", "Reorder"),
                    ChildData(com.dinesh.android.rv.java.multi_select.RvMain::class.java, "Multi Select", "Multiple selection"),
                    ChildData(com.dinesh.android.rv.java.expand.RvMain::class.java, "Expand", "Expand")
                )
            }
        }
    }

}


data class ChildData(val className: Class<*>, val title: String = "", val description: String = "")
